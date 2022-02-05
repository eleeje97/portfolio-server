package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class VisitorRequest {
    String user;
    String nickName;
    String msg;
}
