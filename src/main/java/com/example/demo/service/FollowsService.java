package com.example.demo.service;

import com.example.demo.domain.entity.Follows;

import java.util.List;

public interface FollowsService {
    public List<Follows> getFollowsByUserid(int userid); //谁关注了f.userid
    public List<Follows> getFollowsByFollowerUserid(int userid); //f.userid 关注了谁
}
