package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class VisitorResponse {
    String user;
    int page;
    int size;
    int totalPage;
    List<VisitorDTO> visitors;
}
