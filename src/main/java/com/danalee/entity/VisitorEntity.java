package com.danalee.entity;

import lombok.*;

import javax.persistence.*;

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
    private String visitorRegDate;

    public VisitorEntity(int userId, String visitorNickname, String visitorMsg, String visitorRegDate) {
        this.userId = userId;
        this.visitorNickname = visitorNickname;
        this.visitorMsg = visitorMsg;
        this.visitorRegDate = visitorRegDate;
    }
}
