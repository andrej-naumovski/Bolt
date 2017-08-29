package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.exceptions.UserNotFoundException;
import mk.edu.ukim.feit.bolt.api.models.Authority;
import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.AuthorityRepository;
import mk.edu.ukim.feit.bolt.api.repositories.PasswordResetTokenRepository;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.AuthenticationService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by gjorgjim on 8/11/17.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    public AuthenticationServiceImpl(
            PasswordResetTokenRepository passwordResetTokenRepository,
            UserRepository userRepository
    ) {
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
        PasswordResetToken passwordResetToken = new PasswordResetToken(UUID.randomUUID().toString(), user);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public boolean isTokenValid(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        logger.debug("Argument token: " + token);
        logger.debug(passwordResetToken != null ? "Token is not null" : "Token is null");
        return passwordResetToken != null && passwordResetToken.getDate().compareTo(new Date()) > 0;
    }

    @Override
    public void deletePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null) {
            return;
        }
        passwordResetToken.setUser(null);
        passwordResetTokenRepository.save(passwordResetToken);
        passwordResetTokenRepository.delete(passwordResetToken.getId());
    }

    @Override
    public void resetPassword(String token, String password) throws UserNotFoundException {
        User user = passwordResetTokenRepository.findByToken(token).getUser();
        if(user == null) {
            throw new UserNotFoundException();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
