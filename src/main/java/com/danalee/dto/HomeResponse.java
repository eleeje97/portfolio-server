package com.danalee.dto;

import java.util.ArrayList;

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

    public HomeResponse(String user, int today, int total, String todayFeelings, String stateMsg, String name, String birth, String email, String blog, String github, String title, String miniRoomName, ArrayList<String> recentVisitors) {
        this.user = user;
        this.today = today;
        this.total = total;
        this.todayFeelings = todayFeelings;
        this.stateMsg = stateMsg;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.blog = blog;
        this.github = github;
        this.title = title;
        this.miniRoomName = miniRoomName;
        this.recentVisitors = recentVisitors;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTodayFeelings() {
        return todayFeelings;
    }

    public void setTodayFeelings(String todayFeelings) {
        this.todayFeelings = todayFeelings;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMiniRoomName() {
        return miniRoomName;
    }

    public void setMiniRoomName(String miniRoomName) {
        this.miniRoomName = miniRoomName;
    }

    public ArrayList<String> getRecentVisitors() {
        return recentVisitors;
    }

    public void setRecentVisitors(ArrayList<String> recentVisitors) {
        this.recentVisitors = recentVisitors;
    }
}
