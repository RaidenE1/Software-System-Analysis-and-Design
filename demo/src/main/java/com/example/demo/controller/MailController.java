package com.example.demo.controller;

import com.example.demo.service.MailService;
import io.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MailController {
    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender mailSender;

    @PostMapping("/mail")
    public Map<String, Object> verifyFunc(@RequestBody Map<String, String> remap, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String email = remap.get("email");
        String reason = remap.get("reason");
        String verifyCode = generateVerifyCode();
        try{
            mailService.sendSimpleMail(email, "BUAA_Tech ：请查收您的验证码", reason, verifyCode);
            HttpSession session = request.getSession();
            session.setAttribute("verifyCode", verifyCode);
            map.put("res", true);
            map.put("message", "验证码已发送，请查收");
        } catch(Exception e){
            e.printStackTrace();
            map.put("res", false);
            map.put("message", "验证码发送失败失败，请再次检查邮箱输入，并联系管理员解决。");
        }

        return map;
    }


    public String generateVerifyCode(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random(new Date().getTime());
        for(int i = 0; i < 6; i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}
