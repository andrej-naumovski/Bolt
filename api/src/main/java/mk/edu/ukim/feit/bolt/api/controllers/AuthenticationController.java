package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.exceptions.UserNotFoundException;
import mk.edu.ukim.feit.bolt.api.models.*;
import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.AuthenticationService;
import mk.edu.ukim.feit.bolt.api.services.AuthorityService;
import mk.edu.ukim.feit.bolt.api.services.MailService;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrejnaumovski on 8/9/17.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(value = "*")
public class AuthenticationController {
    private UserService userService;
    private TokenHelper tokenHelper;
    private MailService mailService;
    private AuthenticationService authenticationService;
    private AuthorityService authorityService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    public AuthenticationController(
            UserService userService,
            TokenHelper tokenHelper,
            MailService mailService,
            AuthenticationService authenticationService,
            AuthorityService authorityService
            ) {
        if (userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        if(tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        if(mailService == null) {
            throw new IllegalArgumentException(MailService.class.getName() + " cannot be null.");
        }
        if(authenticationService == null) {
            throw new IllegalArgumentException(AuthenticationService.class.getName() + " cannot be null.");
        }
        if(authorityService == null) {
            throw new IllegalArgumentException(AuthorityService.class.getName() + " cannot be null.");
        }
        this.userService = userService;
        this.tokenHelper = tokenHelper;
        this.mailService = mailService;
        this.authenticationService = authenticationService;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User user) {
        logger.info(String.format("Attempted registration by %s", user.toString()));
        EmailValidator validator = EmailValidator.getInstance();

        if(userService.findByUsername(user.getUsername()) != null) {
            logger.info(String.format("User with username %s already exists.", user.getUsername()));
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.CONFLICT.value(), "A user with that username already exists!"),
                    HttpStatus.CONFLICT
            );
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (user.getContact() == null
                || user.getContact().getEmail() == null
                || !validator.isValid(user.getContact().getEmail())
                || user.getPassword().length() < 6) {
            logger.info(String.format("Attempted register by user %s has incomplete data.", user.getUsername()));
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid or incomplete data."),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityService.findByName("ROLE_USER"));
        user.setAuthorities(authorities);

        User newUser = this.userService.saveUser(user);

        if(newUser == null) {
            logger.error(String.format("Server error while registering user %s.", user.getUsername()));
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "GenericResponse<> processing your data. Please try again."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info(String.format("User %s successfully registered.", newUser.getUsername()));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = this.tokenHelper.getToken(request);

        if(token != null && this.tokenHelper.canTokenBeRefreshed(token)) {
            String newToken = tokenHelper.refreshToken(token);

            Cookie authCookie = new Cookie(TOKEN_COOKIE, newToken);
            authCookie.setPath("/");
            authCookie.setHttpOnly(true);
            authCookie.setMaxAge(EXPIRES_IN);
            response.addCookie(authCookie);

            UserTokenState userTokenState = new UserTokenState(newToken, EXPIRES_IN);
            return new ResponseEntity<>(userTokenState, HttpStatus.OK);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return new ResponseEntity<>(userTokenState, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/reset/{username}", method = RequestMethod.GET)
    public ResponseEntity requestPasswordReset(@PathVariable String username) {
        PasswordResetToken passwordResetToken;
        try {
            passwordResetToken = authenticationService.generatePasswordResetToken(username);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Cannot generate reset token at this time, please try again."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        mailService.sendPasswordResetEmail(mailService.generatePasswordResetEmail(passwordResetToken));
        return new ResponseEntity<>(
                new GenericResponse<>(
                        HttpStatus.OK.value(), "An email containing the password reset URL has been sent to your email. Please check your inbox for further instructions."
                ),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/reset/validate", method = RequestMethod.GET)
    public ResponseEntity validateResetToken(@RequestParam String token) {
        if(authenticationService.isTokenValid(token)) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.OK.value(), true),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), false),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestParam String token, @RequestParam String password) {
        try {
            authenticationService.resetPassword(token, password);
        } catch(UserNotFoundException e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
        authenticationService.deletePasswordResetToken(token);
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "Password successfully reset. Please log in to continue."),
                HttpStatus.OK
        );
    }
}
