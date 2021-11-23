package com.example.demo.controller;


import com.example.demo.domain.entity.Msgbox;
import com.example.demo.service.MsgboxService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MsgboxController {

    @Resource
    private MsgboxService msgboxService;

    @PostMapping("/addMsgbox")
    public Object addMsgbox(@RequestBody Map<String, String> remap){
        Map<String, Object> map = new HashMap<>();
        int fromid = Integer.parseInt(remap.get("msgboxfrom"));
        int toid = Integer.parseInt(remap.get("msgboxto"));
        int msgboxid = Integer.parseInt(remap.get("msgboxid"));
        Msgbox msgbox = msgboxService.selectmsgboxbyuserid(fromid, toid);
        if(msgbox != null){
            map.put("res", false);
            map.put("message", "failed");
        }
        else{
            msgbox = new Msgbox(msgboxid,false,fromid,toid);
            msgboxService.addmsgbox(msgbox);
            map.put("res", true);
            map.put("message", "success");
        }
        return map;
    }

    @PostMapping("/delMsgbox")
    public Object delMsgbox(@RequestBody Map<String, String> remap){
        Map<String, Object> map = new HashMap<>();
        int msgboxid = Integer.parseInt(remap.get("msgboxid"));
        Msgbox msgbox = msgboxService.selectmsgboxbyid(msgboxid);
        if(msgbox == null){
            map.put("res", false);
            map.put("message", "failed");
        }
        else{
            msgboxService.delmsgbox(msgboxid);
            map.put("res", true);
            map.put("message", "success");
        }
        return map;
    }

    @PostMapping("/updateMsgbox")
    public Object updateMsgbox(@RequestBody Map<String, String> remap){
        Map<String, Object> map = new HashMap<>();
        int msgboxid = Integer.parseInt(remap.get("msgboxid"));
        boolean msgboxread = Boolean.parseBoolean(remap.get("masboxread"));
        Msgbox msgbox = msgboxService.selectmsgboxbyid(msgboxid);
        if(msgbox == null){
            map.put("res", false);
            map.put("message", "failed");
        }
        else{
            msgbox.setMsgboxRead(msgboxread);
            msgboxService.updatemsgbox(msgbox);
            map.put("res", true);
            map.put("message", "success");
        }
        return map;
    }

    @PostMapping("/getMsgbox")
    public Object getMsgboxByMsgboxTo(@RequestBody Map<String, String> remap){
        Map<String, Object> map = new HashMap<>();
        int msgboxid = Integer.parseInt(remap.get("msgboxid"));
        int msgboxto = Integer.parseInt(remap.get("msgboxto"));
        List<Msgbox> msgbox_list = msgboxService.findbytoid(msgboxto);
        if(msgbox_list.size() == 0){
            map.put("res", false);
            map.put("message", "failed");
        }
        else{
            map.put("res", true);
            map.put("message", "success");
        }
        return map;
    }
}
