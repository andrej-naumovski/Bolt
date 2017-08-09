package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.Error;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gjorgjim on 8/9/17.
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        if(userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.NOT_FOUND.value(), "A user with that username does not exist."),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/friends", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getFriendsByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);

        if(user == null) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.NOT_FOUND.value(), "A user with that username does not exists"),
                            HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(user.getFriends(), HttpStatus.OK);
    }
}
