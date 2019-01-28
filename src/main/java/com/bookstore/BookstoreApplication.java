package com.bookstore;

import com.bookstore.model.User;
import com.bookstore.security.Role;
import com.bookstore.security.UserRole;
import com.bookstore.service.UserService;
import com.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication //(exclude = { SecurityAutoConfiguration.class })
public class BookstoreApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("Gaurav");
        user.setLastName("Pathak");
        user.setUsername("g");
        user.setPassword(SecurityUtility.passwordEncoder().encode("g"));
        user.setEmail("abc@abc.com");

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ROLE_USER");

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,role));

        userService.createUser(user,userRoles);
    }
}

