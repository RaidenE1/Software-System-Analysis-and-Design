package com.example.demo.service.impl;

import com.example.demo.dao.jpa.MsgboxJPA;
import com.example.demo.domain.entity.Msgbox;
import com.example.demo.service.MsgboxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgboxServiceImpl implements MsgboxService {
    @Resource
    private MsgboxJPA msgboxJPA;

    @Override
    public void addmsgbox(Msgbox msgbox){
        msgboxJPA.save(msgbox);
    }

    @Override
    public void delmsgbox(int msgboxid){
        msgboxJPA.deleteById(msgboxid);
    }

    @Override
    public Msgbox selectmsgboxbyid(int msgboxid){
        return msgboxJPA.findById(msgboxid).get();
    }

    @Override
    public Msgbox selectmsgboxbyuserid(int fromid, int toid){
        return msgboxJPA.findByMsgboxFromAndMsgboxTo(fromid, toid);
    }

    @Override
    public void updatemsgbox(Msgbox msgbox){
        msgboxJPA.save(msgbox);
    }

    @Override
    public List<Msgbox> findbytoid(int msgboxto) {
        return msgboxJPA.findByMsgboxTo(msgboxto);
    }
}
