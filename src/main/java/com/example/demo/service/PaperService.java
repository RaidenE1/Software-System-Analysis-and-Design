package com.example.demo.service;

import com.example.demo.domain.common.R;
import com.example.demo.domain.entity.Paper;

import java.util.List;

public interface PaperService {
	List<Paper> list();

	Paper get(String id);

	void delete(String id);

	void update(Paper paper);

	void add(Paper paper);

	List<Paper> searchByKeyword(String keyword);

	List<Paper> searchByAuthor(String author);

	List<Paper> searchByTitle(String title);
}