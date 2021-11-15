package com.example.demo.dao.mapper;

import com.example.demo.domain.entity.User;

import java.util.List;

public interface UserMapper {
	List<User> listAllUser();

	int createUser(User user);

	void changeUsername(int id, String newUsername);

	void changeUserPassword(int id, String newPassword);
}
