package com.example.demo.service.impl;

import com.example.demo.service.PaperService;
import com.example.demo.dao.jpa.PaperJPA;
import com.example.demo.dao.mapper.PaperMapper;
import com.example.demo.domain.entity.Paper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
	@Resource
	private PaperJPA paperJPA;

	@Resource
	private PaperMapper paperMapper;

	@Override
	public List<Paper> list(){
		return paperJPA.findAll();
	}
}