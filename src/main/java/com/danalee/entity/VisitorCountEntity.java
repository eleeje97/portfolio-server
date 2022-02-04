package com.danalee.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(VisitorCountPK.class)
@Entity(name = "VISITOR_COUNT_TB")

public class VisitorCountEntity {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "visit_date")
    private String visitDate;

    @Column(name = "visit_count")
    private int visitCount;

}
