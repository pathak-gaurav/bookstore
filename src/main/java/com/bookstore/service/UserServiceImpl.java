package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.repository.PasswordResetTokenRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.PasswordResetToken;
import com.bookstore.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token,user);
        passwordResetTokenRepository.save(myToken);
    }


    //TO DO FROM HERE
    @Override
    public User createUser(User user, Set<UserRole> userRoleSet) {
        return null;
    }
}
