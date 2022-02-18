package com.danalee.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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

    @Column(name = "user_img_path")
    private String userImgPath;

    @Column(name = "feeling_img_path")
    private String feelingImgPath;

    @Column(name = "miniroom_img_path")
    private String miniroomImgPath;

}
