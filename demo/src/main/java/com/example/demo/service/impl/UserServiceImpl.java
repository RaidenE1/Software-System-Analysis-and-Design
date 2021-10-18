package com.example.demo.service.impl;

import com.example.demo.dao.jpa.UserJPA;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserJPA userJPA;

	@Resource
	private UserMapper userMapper;

	@Override
	public void updateUser(User user) {
		userJPA.save(user);
	}

	@Override
	public int createUser(User user) {
		return userMapper.createUser(user);
	}

	@Override
	public void removeUser(int id) {
		userJPA.deleteById(id);
	}

	@Override
	public List<User> listAllUser() {
		switch (0) {
			case 1:
				return userJPA.findAll();
			default:
				return userMapper.listAllUser();
		}
	}
}
