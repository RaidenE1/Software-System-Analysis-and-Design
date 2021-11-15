package com.example.demo.dao.jpa;

import com.example.demo.domain.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperJPA extends JpaRepository<Paper,String> {
}