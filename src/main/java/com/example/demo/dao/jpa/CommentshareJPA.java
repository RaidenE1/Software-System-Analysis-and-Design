package com.example.demo.dao.jpa;

import com.example.demo.domain.entity.CommentShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentshareJPA extends JpaRepository<CommentShare,Integer> {
}