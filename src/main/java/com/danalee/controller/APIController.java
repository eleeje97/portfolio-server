package com.danalee.controller;

import com.danalee.dto.*;
import com.danalee.entity.*;
import com.danalee.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class APIController {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final VisitorRepository visitorRepository;
    private final VisitorCountRepository visitorCountRepository;

    private static final HashMap<String, String> animalEmoji = new HashMap<String, String>() {
        {
            put("호랑이", "🐯");
            put("여우", "🦊");
            put("토끼", "🐰");
            put("판다", "🐼");
            put("펭귄", "🐧");
            put("돼지", "🐷");
            put("곰", "🐻");
        }
    };


    @GetMapping("/home")
    public HomeResponse getUserInfo(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();

        // 방문자 수 갱신
        countVisitor(userId);

        int todayCount = getTodayCount(userId);
        int totalCount = getTotalCount(userId);


        // 최근 방명록
        ArrayList<String> recentVisitors = new ArrayList<>();
        List<VisitorEntity> visitorEntities = visitorRepository.findAllByUserId(userId);
        if (visitorEntities.size() > 3) {
            for (int i = 0; i < 3; i++) {
                VisitorEntity entity = visitorEntities.get(visitorEntities.size() - (i+1));
                recentVisitors.add(entity.getVisitorMsg() + " (" + entity.getVisitorNickname() + ")");
            }
        } else {
            for (int i = 0; i < visitorEntities.size(); i++) {
                VisitorEntity entity = visitorEntities.get(visitorEntities.size() - (i+1));
                recentVisitors.add(entity.getVisitorMsg() + " (" + entity.getVisitorNickname() + ")");
            }
        }

        HomeResponse response = new HomeResponse(user,
                todayCount,
                totalCount,
                userEntity.getUserImgPath(),
                userEntity.getTodayFeelings(),
                userEntity.getFeelingImgPath(),
                userEntity.getStateMsg(),
                userEntity.getName(),
                userEntity.getBirth(),
                userEntity.getEmail(),
                userEntity.getBlog(),
                userEntity.getGithub(),
                userEntity.getTitle(),
                userEntity.getMiniRoomName(),
                userEntity.getMiniroomImgPath(),
                recentVisitors);

        System.out.println("Requested URL: /portfolio/home?user=" + user);
        return response;
    }


    @GetMapping("/profile")
    public ProfileResponse getProfile(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        ProfileEntity profileEntity = profileRepository.findById(userId).get();

        List<String> keywords = Arrays.asList(profileEntity.getKeyword().split(","));

        ProfileResponse response = new ProfileResponse(user,
                profileEntity.getIntroMsg(),
                userEntity.getName(),
                userEntity.getBirth(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                keywords);

        System.out.println("Requested URL: /portfolio/profile?user=" + user);
        return response;
    }


    @GetMapping("/project/list")
    public ProjectResponse getProjects(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        List<ProjectEntity> projectEntities = projectRepository.findAllByUserIdOrderByProjectRegDateDesc(userId);
        List<ProjectListDTO> projects = new ArrayList<>();

        for (int i = 0; i < projectEntities.size(); i++) {
            ProjectEntity entity = projectEntities.get(i);
            projects.add(new ProjectListDTO(i+1,
                    entity.getProjectLang(),
                    entity.getProjectTitle()));
        }

        
        ProjectResponse response = new ProjectResponse(user, projects);
        System.out.println("Requested URL: /portfolio/project/list?user=" + user);
        return response;
    }


    @GetMapping("/project/detail")
    public ProjectDetailDTO getProjectDetail(@RequestParam("user") String user, @RequestParam("projectNo") int projectNo) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        List<ProjectEntity> projectEntities = projectRepository.findAllByUserIdOrderByProjectRegDateDesc(userId);
        ProjectEntity projectEntity = projectEntities.get(projectNo - 1);

        List<String> frontSkills = new ArrayList<>();
        List<String> backSkills = new ArrayList<>();

        if (projectEntity.getProjectFrontSkill() != null) {
            for (String skill : projectEntity.getProjectFrontSkill().split(",")) {
                frontSkills.add(skill);
            }
        }

        if (projectEntity.getProjectBackSkill() != null) {
            for (String skill : projectEntity.getProjectBackSkill().split(",")) {
                backSkills.add(skill);
            }

        }

        ProjectDetailDTO project = new ProjectDetailDTO(projectNo,
                projectEntity.getProjectLang(),
                projectEntity.getProjectTitle(),
                projectEntity.getProjectRegDate(),
                projectEntity.getProjectImgPath(),
                projectEntity.getProjectStartDate(),
                projectEntity.getProjectEndDate(),
                projectEntity.getProjectDescription(),
                frontSkills,
                backSkills,
                projectEntity.getDeploymentUrl(),
                projectEntity.getGithubUrl());

        return project;
    }


    @GetMapping("/visitor")
    public VisitorResponse getVisitors(@RequestParam("user") String user, Pageable pageable) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        Page<VisitorEntity> visitorEntityPage = visitorRepository.findAllByUserIdOrderByVisitorIdDesc(userId, pageable);
        List<VisitorEntity> visitorEntities = visitorEntityPage.getContent();
        int page = pageable.getPageNumber() + 1;
        int size = pageable.getPageSize();
        int totalPage = visitorEntityPage.getTotalPages();

        List<VisitorDTO> visitors = new ArrayList<>();
        for (int i = 0; i < visitorEntities.size(); i++) {
            VisitorEntity entity = visitorEntities.get(i);
            int no = (((int) visitorEntityPage.getTotalElements() - ((page-1) * size)) - i);
            visitors.add(new VisitorDTO(no,
                    entity.getVisitorNickname(),
                    entity.getVisitorRegDate(),
                    animalEmoji.get(entity.getVisitorNickname().split(" ")[1]),
                    entity.getVisitorMsg()));
        }


        VisitorResponse response = new VisitorResponse(user,
                page,
                size,
                totalPage,
                visitors);
        System.out.println("Requested URL: /portfolio/visitor?user=" + user);
        return response;
    }


    @PostMapping("/visitor")
    public String registerVisitor(@RequestBody VisitorRequest request) {
        UserEntity userEntity = userRepository.findByEngName(request.getUser());
        int userId = userEntity.getUserId();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String formattedNow = now.format(formatter);
        visitorRepository.save(new VisitorEntity(userId, request.getNickName(), request.getMsg(), formattedNow));

        System.out.println("Requested URL: /portfolio/visitor (POST) user=" + request.getUser());
        return "Register SUCCESS!\nuser: " + request.getUser() + "\nnickname: " + request.getNickName() + "\nmsg: " + request.getMsg();
    }


    // 오늘 방문자 수 올리기
    public void countVisitor(int userId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedToday = today.format(formatter);
        VisitorCountEntity visitorCountEntity = visitorCountRepository.findByUserIdAndVisitDate(userId, formattedToday);

        if (visitorCountEntity == null) {
            visitorCountRepository.save(new VisitorCountEntity(userId, formattedToday, 1));
        } else {
            visitorCountEntity.setVisitCount(visitorCountEntity.getVisitCount()+1);
            visitorCountRepository.save(visitorCountEntity);
        }
    }

    public int getTodayCount(int userId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedToday = today.format(formatter);
        VisitorCountEntity visitorCountEntity = visitorCountRepository.findByUserIdAndVisitDate(userId, formattedToday);

        return visitorCountEntity.getVisitCount();
    }

    public int getTotalCount(int userId) {
        int totalCount = 0;
        List<VisitorCountEntity> visitorCountEntityList = visitorCountRepository.findAllByUserId(userId);
        for (VisitorCountEntity entity : visitorCountEntityList) {
            totalCount += entity.getVisitCount();
        }

        return totalCount;
    }
}
