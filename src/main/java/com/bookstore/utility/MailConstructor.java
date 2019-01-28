package com.bookstore.utility;

import com.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment environment;

    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user, String password) {
        String url = contextPath + "/newUser?token=" + token;
        String message = "Please click on this link o verify your email and edit your personal information. Your password is \n:" + password;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Gaurav Book Store -  New User");
        simpleMailMessage.setText(url + message);
        simpleMailMessage.setFrom(environment.getProperty("support.email"));
        return simpleMailMessage;
    }
}
