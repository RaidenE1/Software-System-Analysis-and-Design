package com.example.demo.dao.jpa;

import com.example.demo.domain.entity.Msgbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgboxJPA extends JpaRepository<Msgbox,Integer> {
    List<Msgbox> findByMsgboxTo(int msgboxto);
    Msgbox findByMsgboxFromAndMsgboxTo(int msgboxFrom, int MsgboxTo);
}
