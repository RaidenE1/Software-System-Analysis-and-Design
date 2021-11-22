package com.example.demo.dao.mapper;

import com.example.demo.domain.entity.Paper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaperMapper {

	List<Paper> searchByTitle(@Param("title") String title);

	List<Paper> searchByAuthor(String author);

	List<Paper> searchByKeyword(String keyword);
}