package com.danalee.controller;

import com.danalee.dto.*;
import com.danalee.entity.*;
import com.danalee.repo.*;
import lombok.RequiredArgsConstructor;
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
                userEntity.getTodayFeelings(),
                userEntity.getStateMsg(),
                userEntity.getName(),
                userEntity.getBirth(),
                userEntity.getEmail(),
                userEntity.getBlog(),
                userEntity.getGithub(),
                userEntity.getTitle(),
                userEntity.getMiniRoomName(),
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


    @GetMapping("/project")
    public ProjectResponse getProjects(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        List<ProjectEntity> projectEntities = projectRepository.findAllByUserId(userId);
        List<ProjectDTO> projects = new ArrayList<>();

        for (int i = 0; i < projectEntities.size(); i++) {
            ProjectEntity entity = projectEntities.get(i);
            projects.add(new ProjectDTO(i+1,
                    entity.getProjectLang(),
                    entity.getProjectTitle(),
                    entity.getProjectImgPath(),
                    entity.getProjectStartDate(),
                    entity.getProjectEndDate(),
                    entity.getProjectDescription(),
                    Arrays.asList(entity.getProjectFrontSkill().split(",")),
                    Arrays.asList(entity.getProjectBackSkill().split(",")),
                    entity.getDeploymentUrl(),
                    entity.getGithubUrl()));
        }

        
        ProjectResponse response = new ProjectResponse(user, projects);
        System.out.println("Requested URL: /portfolio/project?user=" + user);
        return response;
    }


    @GetMapping("/visitor")
    public VisitorResponse getVisitors(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);
        int userId = userEntity.getUserId();
        List<VisitorEntity> visitorEntities = visitorRepository.findAllByUserId(userId);
        List<VisitorDTO> visitors = new ArrayList<>();


        for (int i = 0; i < visitorEntities.size(); i++) {
            VisitorEntity entity = visitorEntities.get(visitorEntities.size() - (i+1));
            visitors.add(new VisitorDTO(visitorEntities.size() - i,
                    entity.getVisitorNickname(),
                    entity.getVisitorRegDate(),
                    entity.getVisitorMsg()));
        }


        VisitorResponse response = new VisitorResponse(user, visitors);
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
