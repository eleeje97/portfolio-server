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
    String todayFeelings;
    String stateMsg;
    String name;
    String birth;
    String email;
    String blog;
    String github;
    String title;
    String miniRoomName;
    ArrayList<String> recentVisitors;

}
