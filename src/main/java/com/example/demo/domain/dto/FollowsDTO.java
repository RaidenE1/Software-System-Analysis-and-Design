package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class FollowsDTO {
    private int follow_id;
    private int userid;
    private int follower_userid;
}