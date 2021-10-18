package com.example.demo.service;

import com.example.demo.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
	List<User> listAllUser();

	void removeUser(int id);

	void updateUser(User user);

	int createUser(User user);
}
