package com.example.demo.dao.mapper;

import com.example.demo.domain.entity.Follows;

import java.util.List;

public interface FollowsMapper {
    public List<Follows> getFollowsByUserid(int userid);
    public List<Follows> getFollowsByFollowerUserid(int follower_userid);
}
