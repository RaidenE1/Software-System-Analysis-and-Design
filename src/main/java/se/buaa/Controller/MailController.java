package se.buaa.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import se.buaa.Entity.Response.Result;
import se.buaa.Repository.UserRepository;
import se.buaa.Service.MailService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class MailController {
    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user/sendEmail")
    public Result verifyFunc(@RequestBody Map<String, String> remap, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String email = remap.get("email");
        int verification = 1921;
        try {
            verification = (int) Math.round((Math.random()) * 10000);
            mailService.sendSimpleMail(email, "BUAA-TECH ：请查收您的验证码", "注册新用户", String.valueOf(verification));
            map.put("res", true);
            map.put("message", "sent");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.Error("201", "邮件发送异常，请联系管理员或重试。");
        }
        Data data = new Data();
        data.verification = Integer.toString(verification);
        return Result.Success(data);
    }

    public class Data {

        public String verification;

    }
}
