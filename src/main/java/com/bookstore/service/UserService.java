package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.security.PasswordResetToken;
import com.bookstore.security.UserRole;

import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User createUser(User user, Set<UserRole> userRoleSet) throws Exception;
}
