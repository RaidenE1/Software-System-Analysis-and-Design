package com.example.demo.controller;

import com.example.demo.domain.entity.Paper;
import com.example.demo.service.PaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
	@Resource
	private PaperService paperService;

	@GetMapping("/list")
	public Object list(){
		return paperService.list();
	}
}