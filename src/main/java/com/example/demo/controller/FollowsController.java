package com.example.demo.controller;

import com.example.demo.domain.entity.Follows;
import com.example.demo.domain.entity.User;
import com.example.demo.service.FollowsService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FollowsController {

    @Resource
    private FollowsService followsService;

    @Resource
    private UserService userService;

    @PostMapping("/getFollowers")
    public Map<String, Object> getFollowers(@RequestBody Map<String, String> remap){
        Map<String, Object> resMap = new HashMap<>();
        int userid = Integer.parseInt(remap.get("userid"));
        List<Follows> follows_list = followsService.getFollowsByUserid(userid);
        List<User> followers_list = new ArrayList<>();
        for (Follows f : follows_list) {
            User follower = userService.getUserById(f.getFollower_userid());
            followers_list.add(follower);
        }
        return followers_list;
    }

    @PostMapping("/getFollowedUser")
    public Object getFollowedUser(@RequestBody Map<String, String> remap){
        Map<String, Object> resMap = new HashMap<>();
        int userid = Integer.parseInt(remap.get("userid"));
        List<Follows> follow_list = followsService.getFollowsByFollowerUserid(userid)
        List<User> follow_users_list = new ArrayList<>();
        for (Follows f : follow_list) {
            User follower = userService.getUserById(f.getFollower_userid());
            follow_users_list.add(follower);
        }
        return follow_users_list;
    }
}
