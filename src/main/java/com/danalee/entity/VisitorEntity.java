package com.danalee.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "VISITOR_TB")
public class VisitorEntity {
    @Id
    @Column(name = "visitor_id")
    private int visitorId;

    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "visitor_nickname")
    private String visitorNickname;

    @Column(name = "visitor_msg")
    private String visitorMsg;

    @Column(name = "visitor_reg_date")
    private Date visitorRegDate;

}
