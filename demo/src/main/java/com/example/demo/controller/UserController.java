package com.example.demo.controller;

import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
//@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Map<String, Object> userLogin(@RequestBody Map<String, String> remap){
		String email = remap.get("email");
		String password = remap.get("password");
		Map<String, Object> resMap = new HashMap<>();
		User u = userService.getUserByEmail(email);
		if(u == null){
			resMap.put("res", false);
			resMap.put("mes", "not-registered");

		} else{
			if(password.equals(u.getPassword())){
				resMap.put("res", true);
				resMap.put("user", u);
				resMap.put("mes", "success");
			} else{
				resMap.put("res", false);
				resMap.put("mes", "wrong-password");
			}
		}
		return resMap;
	}

	@PostMapping("/register")
	public Map<String, Object> userRegister(@RequestBody Map<String, String> remap, HttpServletRequest request){
		String email = remap.get("email");
		String userName = remap.get("userName");
		String password = remap.get("password");
		String verifyCode = remap.get("verifyCode");
		Map<String, Object> resMap = new HashMap<>();
		User u = userService.getUserByEmail(email);
		if(u != null){
			resMap.put("res", false);
			resMap.put("mes", "email-registered");
			return resMap;
		}
		if(verifyCode.equals(request.getSession().getAttribute("verifyCode"))){
			userService.createUser(new User(email, userName, password));
			resMap.put("res", true);
			resMap.put("mes", "success");
		} else{
			resMap.put("res", false);
			resMap.put("mes", "wrong-verify-code");
		}
		return resMap;
	}

	@PutMapping("create")
	public R create(@RequestBody UserDTO userDTO) {
		User user = User.builder().id(userDTO.getId()).password(userDTO.getPassword()).username(userDTO.getUsername()).build();
		System.out.println(user);
		userService.createUser(user);
		return R.success(user.getId());
	}
}
