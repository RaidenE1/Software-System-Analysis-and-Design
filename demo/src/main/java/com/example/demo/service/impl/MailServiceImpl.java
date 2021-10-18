package com.example.demo.service.impl;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMail(String tto, String subject, String reason, String verify_code){
        MimeMessage mimeMessage;
        try {
            Map<String, Object> valueMap = new HashMap<>();
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(from);
            // 设置收件人邮箱
            helper.setTo(tto);
            // 设置邮件标题
            helper.setSubject(subject);
            // 添加正文（使用thymeleaf模板）
            valueMap.put("reason", reason);
            valueMap.put("verify_code", verify_code);
            Context context = new Context();
            context.setVariables(valueMap);
            String content = this.templateEngine.process("MailExample", context);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
}
