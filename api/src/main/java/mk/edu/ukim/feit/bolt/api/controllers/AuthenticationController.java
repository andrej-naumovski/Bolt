package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.Contact;
import mk.edu.ukim.feit.bolt.api.models.Error;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.models.UserTokenState;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by andrejnaumovski on 8/9/17.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private UserService userService;
    private TokenHelper tokenHelper;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    public AuthenticationController(UserService userService, TokenHelper tokenHelper) {
        if (userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        if(tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        this.userService = userService;
        this.tokenHelper = tokenHelper;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User user) {
        logger.info(String.format("Attempted registration by %s", user.toString()));
        EmailValidator validator = EmailValidator.getInstance();

        if(userService.findByUsername(user.getUsername()) != null) {
            logger.info(String.format("User with username %s already exists.", user.getUsername()));
            return new ResponseEntity<>(
                    new Error(HttpStatus.CONFLICT.value(), "A user with that username already exists!"),
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
                    new Error(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid or incomplete data."),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = this.userService.saveUser(user);

        if(newUser == null) {
            logger.error(String.format("Server error while registering user %s.", user.getUsername()));
            return new ResponseEntity<>(
                    new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing your data. Please try again."),
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

}
