package com.example.demo.service;

import com.example.demo.domain.entity.Msgbox;

import java.util.List;

public interface MsgboxService {
    public void addmsgbox(Msgbox msgbox);
    public void delmsgbox(int msgboxid);
    public Msgbox selectmsgboxbyid(int msgboxid);
    public Msgbox selectmsgboxbyuserid(int fromid, int toid);
    public void updatemsgbox(Msgbox msgbox);
    public List<Msgbox> findbytoid(int msgboxto);
}
