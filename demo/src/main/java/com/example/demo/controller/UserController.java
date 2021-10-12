package com.example.demo.controller;

import com.example.demo.domain.common.R;
import com.example.demo.domain.dto.UserDTO;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {
	@Resource
	private UserService userService;

	@GetMapping("listAll")
	public R listAll() {
		List<User> users = userService.listAllUser();
		return R.success(users);
	}

	@DeleteMapping("remove")
	public R remove(@RequestParam int id) {
		userService.removeUser(id);
		return R.success();
	}

	@PostMapping("update")
	public R update(@RequestBody UserDTO userDTO) {
		User user = User.builder().id(userDTO.getId()).password(userDTO.getPassword()).username(userDTO.getUsername()).build();
		userService.updateUser(user);
		return R.success();
	}

	@PutMapping("create")
	public R create(@RequestBody UserDTO userDTO) {
		User user = User.builder().id(userDTO.getId()).password(userDTO.getPassword()).username(userDTO.getUsername()).build();
		System.out.println(user);
		userService.createUser(user);
		return R.success(user.getId());
	}

	@GetMapping("test")
	public R test() {
		userService.test();
		return R.success();
	}
}
