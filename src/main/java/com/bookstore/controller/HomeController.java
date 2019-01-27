package com.bookstore.controller;

import com.bookstore.security.PasswordResetToken;
import com.bookstore.service.UserSecurityService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }

   /* @RequestMapping("/account")
    public String myAccount() {
        return "myAccount";
    }*/

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model, @RequestParam("token") String token, Locale locale) {
        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        model.addAttribute("classActiveForgetPassword", true);
        return "myAccount";
    }

    @RequestMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("classActiveNewAccount", true);
        return "myAccount";
    }
}
