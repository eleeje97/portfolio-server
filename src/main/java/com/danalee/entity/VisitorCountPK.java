package com.danalee.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VisitorCountPK implements Serializable {
    private int userId;
    private String visitDate;
}
