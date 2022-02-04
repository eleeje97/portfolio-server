package com.danalee.controller;

import com.danalee.dto.HomeResponse;
import com.danalee.entity.UserEntity;
import com.danalee.entity.VisitorCountEntity;
import com.danalee.entity.VisitorEntity;
import com.danalee.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        int todayCount = visitorCountEntity.getVisitCount();
        int totalCount = 0;
        List<VisitorCountEntity> visitorCountEntityList = visitorCountRepository.findAllByUserId(userId);
        for (VisitorCountEntity entity : visitorCountEntityList) {
            totalCount += entity.getVisitCount();
        }


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
}
