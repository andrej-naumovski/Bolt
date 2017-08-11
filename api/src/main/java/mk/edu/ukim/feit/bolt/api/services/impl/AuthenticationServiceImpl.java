package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.exceptions.UserNotFoundException;
import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.PasswordResetTokenRepository;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gjorgjim on 8/11/17.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository,
                                     UserRepository userRepository) {
        if(passwordResetTokenRepository == null) {
            throw new IllegalArgumentException(PasswordResetTokenRepository.class.getName() + " cannot be null.");
        }
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null.");
        }
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PasswordResetToken generatePasswordResetToken(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundException(username);
        }
        return new PasswordResetToken(UUID.randomUUID().toString(), user);
    }

    @Override
    public boolean isTokenValid(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken.getDate().compareTo(new Date()) > 0;
    }
}
