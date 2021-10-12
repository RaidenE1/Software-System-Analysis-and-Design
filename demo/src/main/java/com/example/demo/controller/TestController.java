package com.example.demo.controller;


import com.example.demo.domain.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("test")
	public R test(){
		return R.success();
	}
}
