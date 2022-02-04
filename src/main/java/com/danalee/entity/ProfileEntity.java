package com.danalee.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PROFILE_TB")
public class ProfileEntity {
    @Id
    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "intro_msg")
    private String introMsg;

    @Column(name = "skill")
    private String skill;

    @Column(name = "keyword")
    private String keyword;

}
