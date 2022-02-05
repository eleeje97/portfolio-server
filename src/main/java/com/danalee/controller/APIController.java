package com.danalee.controller;

import com.danalee.dto.*;
import com.danalee.entity.*;
import com.danalee.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        int todayCount = getTodayCount(userId);
        int totalCount = getTotalCount(userId);
        List<String> keywords = Arrays.asList(profileEntity.getKeyword().split(","));
        List<String> skills = Arrays.asList(profileEntity.getSkill().split(","));

        ProfileResponse response = new ProfileResponse(user,
                todayCount,
                totalCount,
                userEntity.getTitle(),
                profileEntity.getIntroMsg(),
                userEntity.getName(),
                userEntity.getBirth(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                keywords,
                skills);

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


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        for (int i = 0; i < visitorEntities.size(); i++) {
            VisitorEntity entity = visitorEntities.get(visitorEntities.size() - (i+1));
            visitors.add(new VisitorDTO(i+1,
                    entity.getVisitorNickname(),
                    formatter.format(entity.getVisitorRegDate()),
                    entity.getVisitorMsg()));
        }


        VisitorResponse response = new VisitorResponse(user, visitors);
        System.out.println("Requested URL: /portfolio/visitor?user=" + user);
        return response;
    }


    // 오늘 방문자 수 올리기
    public void countVisitor(int userId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedToday = today.format(formatter);
        VisitorCountEntity visitorCountEntity = visitorCountRepository.findByUserIdAndVisitDate(userId, formattedToday);

        if (visitorCountEntity == null) {
            visitorCountRepository.save(new VisitorCountEntity(userId, formattedToday, 0));
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
