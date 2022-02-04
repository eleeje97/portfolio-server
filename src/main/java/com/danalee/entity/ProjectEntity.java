package com.danalee.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PROJECT_TB")
public class ProjectEntity {
    @Id
    @Column(name = "project_id")
    private int projectId;

    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "project_lang")
    private String projectLang;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "project_img_path")
    private String projectImgPath;

    @Column(name = "project_start_date")
    private Date projectStartDate;

    @Column(name = "project_end_date")
    private Date projectEndDate;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_front_skill")
    private String projectFrontSkill;

    @Column(name = "project_back_skill")
    private String projectBackSkill;

    @Column(name = "deployment_url")
    private String deploymentUrl;

    @Column(name = "github_url")
    private String githubUrl;

}
