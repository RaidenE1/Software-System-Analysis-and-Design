package com.example.demo.dao.jpa;

import com.example.demo.domain.entity.Commentshare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentshareJPA extends JpaRepository<Commentshare,Integer> {
}