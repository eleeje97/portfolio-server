package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class VisitorDTO {
    int no;
    String nickName;
    String regDate;
    String emoji;
    String msg;
}
