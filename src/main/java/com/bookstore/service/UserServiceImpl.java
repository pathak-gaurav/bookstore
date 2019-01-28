package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.repository.PasswordResetTokenRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.PasswordResetToken;
import com.bookstore.security.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }


    @Override
    public User createUser(User user, Set<UserRole> userRoleSet) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser != null) {
            //throw new Exception("User already Exist!!");
            log.info("***User already Exist!!*** -:" + user.getUsername());
        } else {
            for (UserRole userRole : userRoleSet) {
                roleRepository.save(userRole.getRole());
            }
            user.getUserRole().addAll(userRoleSet);
            localUser = userRepository.save(user);
        }
        return localUser;
    }
}
