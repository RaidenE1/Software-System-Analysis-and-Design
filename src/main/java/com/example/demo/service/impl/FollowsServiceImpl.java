package com.example.demo.service.impl;

import com.example.demo.dao.mapper.FollowsMapper;
import com.example.demo.domain.entity.Follows;
import com.example.demo.service.FollowsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowsServiceImpl  implements FollowsService {

    @Resource
    private FollowsMapper followsMapper;

    @Override
    public List<Follows> getFollowsByUserid(Follows f) {
        return followsMapper.getFollowsByUserid(f.getUserid());
    }

    @Override
    public List<Follows> getFollowsByFollowerUserid(Follows f) {
        return followsMapper.getFollowsByFollowerUserid(f.getFollower_userid());
    }
}