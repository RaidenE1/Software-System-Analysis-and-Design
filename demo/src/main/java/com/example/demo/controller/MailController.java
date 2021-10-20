package com.example.demo.controller;

import com.example.demo.domain.entity.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin
@RestController
public class MailController {
    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserService userService;

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
            map.put("message", "sent");
        } catch(Exception e){
            e.printStackTrace();
            map.put("res", false);
            map.put("message", "failed");
        }
        return map;
    }


    @PostMapping("/checkMail")
    public Map<String, Object> checkEmailExisted(@RequestBody Map<String, String> remap){
        String email = remap.get("email");
        User u = userService.getUserByEmail(email);
        Map<String, Object> resMap = new HashMap<>();
        if(u != null){
            resMap.put("res", true);
            resMap.put("message", "duplicated");
        } else{
            resMap.put("res", false);
            resMap.put("message", "free");
        }
        return resMap;
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
