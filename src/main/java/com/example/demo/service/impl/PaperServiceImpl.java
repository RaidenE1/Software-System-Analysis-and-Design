package com.example.demo.service.impl;

import com.example.demo.dao.jpa.PaperJPA;
import com.example.demo.dao.mapper.PaperMapper;
import com.example.demo.domain.entity.Paper;
import com.example.demo.service.PaperService;
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
	public List<Paper> list() {
		return paperJPA.findAll();
	}

	@Override
	public Paper get(String id) {
		return paperJPA.getById(id);
	}

	@Override
	public void delete(String id) {
		paperJPA.deleteById(id);
	}

	@Override
	public void update(Paper paper) {
		paperJPA.save(paper);
	}

	@Override
	public void add(Paper paper) {
		paperJPA.save(paper);
	}

	@Override
	public List<Paper> searchByKeyword(String keyword) {
		return paperMapper.searchByKeyword(keyword);
	}

	@Override
	public List<Paper> searchByAuthor(String author) {
		return paperMapper.searchByAuthor(author);
	}

	@Override
	public List<Paper> searchByTitle(String title) {
		return paperMapper.searchByTitle(title);
	}
}