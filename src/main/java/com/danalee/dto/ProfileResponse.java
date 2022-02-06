package com.danalee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ProfileResponse {
    String user;
    String introMsg;
    String name;
    String birth;
    String email;
    String phone;
    List<String> keywords;
}
