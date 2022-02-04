package com.danalee.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER_TB")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private String birth;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "github")
    private String github;

    @Column(name = "blog")
    private String blog;

    @Column(name = "title")
    private String title;

    @Column(name = "state_msg")
    private String stateMsg;

    @Column(name = "today_feelings")
    private String todayFeelings;

    @Column(name = "mini_room_name")
    private String miniRoomName;

    @Column(name = "visitor_today_num")
    private int visitorTodayNum;

    @Column(name = "visitor_total_num")
    private int visitorTotalNum;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }

    public String getTodayFeelings() {
        return todayFeelings;
    }

    public void setTodayFeelings(String todayFeelings) {
        this.todayFeelings = todayFeelings;
    }

    public String getMiniRoomName() {
        return miniRoomName;
    }

    public void setMiniRoomName(String miniRoomName) {
        this.miniRoomName = miniRoomName;
    }

    public int getVisitorTodayNum() {
        return visitorTodayNum;
    }

    public void setVisitorTodayNum(int visitorTodayNum) {
        this.visitorTodayNum = visitorTodayNum;
    }

    public int getVisitorTotalNum() {
        return visitorTotalNum;
    }

    public void setVisitorTotalNum(int visitorTotalNum) {
        this.visitorTotalNum = visitorTotalNum;
    }
}
