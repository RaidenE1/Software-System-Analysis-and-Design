package com.example.demo.controller;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class MailController {
    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender mailSender;

    @PostMapping("/mail")

    public Map<String, Object> verifyFunc(@RequestBody Map<String, String> remap){
        Map<String, Object> map = new HashMap<>();
        try{
            mailService.sendSimpleMail(remap.get("email"), "ATBS ：请查收您的验证码", remap.get("reason"), remap.get("verify_code"));
            map.put("res", true);
            map.put("message", "验证码已发送，请查收");
        } catch(Exception e){
            e.printStackTrace();
            map.put("res", false);
            map.put("message", "验证码发送失败失败，请再次检查邮箱输入，并联系管理员解决。");
        }
        return map;
    }
}
