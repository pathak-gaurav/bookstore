package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService  {

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user,final String token);
}
