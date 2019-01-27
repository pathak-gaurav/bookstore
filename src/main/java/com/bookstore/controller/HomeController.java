package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.security.PasswordResetToken;
import com.bookstore.security.Role;
import com.bookstore.security.UserRole;
import com.bookstore.service.UserSecurityService;
import com.bookstore.service.UserService;
import com.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
    public String forgetPassword(Model model) {

        model.addAttribute("classActiveForgetPassword", true);
        return "myAccount";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String email,
                              @ModelAttribute("username") String username, Model model) throws Exception {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExist", true);
            return "myAccount";
        }
        if (userService.findByEmail(email) == null) {
            model.addAttribute("emailExist", true);
            return "myAccount";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        String randomPassword = SecurityUtility.randomPassword();
        String encodedPassword = SecurityUtility.passwordEncoder().encode(randomPassword);
        user.setPassword(encodedPassword);

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("USER_ROLE");
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(new UserRole(user,role));

        //TO DO FROM HERE
        userService.createUser(user, userRoleSet);

        return null;

    }

    @RequestMapping("/newUser")
    public String newUser(Model model, @RequestParam("token") String token, Locale locale) {
        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        if (passwordResetToken == null) {
            String message = "Invalid Token";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }
        User user = passwordResetToken.getUser();
        String username = user.getUsername();
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }
}
