package com.danalee.controller;

import com.danalee.dto.HomeResponse;
import com.danalee.entity.UserEntity;
import com.danalee.entity.VisitorEntity;
import com.danalee.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class APIController {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final VisitorRepository visitorRepository;


    @GetMapping("/home")
    public HomeResponse getUserInfo(@RequestParam("user") String user) {
        UserEntity userEntity = userRepository.findByEngName(user);

        // 조회수 갱신
        // 매일 0시에 today 조회수 초기화
        userEntity.setVisitorTodayNum(userEntity.getVisitorTotalNum() + 1);
        userEntity.setVisitorTotalNum(userEntity.getVisitorTotalNum() + 1);
        userRepository.save(userEntity);

        // 최근 방명록
        ArrayList<String> recentVisitors = new ArrayList<>();

        HomeResponse response = new HomeResponse(user,
                userEntity.getVisitorTodayNum(),
                userEntity.getVisitorTotalNum(),
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
