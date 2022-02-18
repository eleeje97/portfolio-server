package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class HomeResponse {
    String user;
    int today;
    int total;
    String userImgPath;
    String todayFeelings;
    String feelingImgPath;
    String stateMsg;
    String name;
    String birth;
    String email;
    String blog;
    String github;
    String title;
    String miniRoomName;
    String miniroomImgPath;
    ArrayList<String> recentVisitors;

}
