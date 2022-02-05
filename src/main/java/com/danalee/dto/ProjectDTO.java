package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ProjectDTO {
    int no;
    String lang;
    String title;
    String imgPath;
    String startDate;
    String endDate;
    String description;
    List<String> frontSkills;
    List<String> backSkills;
    String deploymentUrl;
    String githubUrl;
}
