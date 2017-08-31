package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by gjorgjim on 8/9/17.
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    private TokenHelper tokenHelper;

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
                    new GenericResponse<>(HttpStatus.NOT_FOUND.value(), "A user with that username does not exist."),
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
                    new GenericResponse<>(HttpStatus.NOT_FOUND.value(), "A user with that username does not exists"),
                            HttpStatus.NOT_FOUND
            );
        }
        Set<User> friends = user.getFriends();
        if(friends == null) {
            return new ResponseEntity<>(Collections.EMPTY_SET, HttpStatus.OK);
        }
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "User successfully deleted"),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public ResponseEntity saveUser(@PathVariable String username, @RequestBody User user){
        if(!username.equals(user.getUsername()))
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.BAD_REQUEST.value(), "Usernames didn't match"),
                    HttpStatus.BAD_REQUEST);
        userService.saveUser(user);
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "User successfully saved"),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/requests/sent/{username}", method = RequestMethod.GET)
    public ResponseEntity userSentRequest(@PathVariable String username,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        String token = tokenHelper.getToken(request);
        String userToCheckUsername = tokenHelper.getUsernameFromToken(token);
        if(userService.userSentRequest(username, userToCheckUsername)) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.OK.value(), true)
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.OK.value(), false)
                    , HttpStatus.OK);
    }

    @RequestMapping(value = "/requests/received/{username}", method = RequestMethod.GET)
    public ResponseEntity userReceivedRequest(@PathVariable String username,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        String token = tokenHelper.getToken(request);
        String userToCheckUsername = tokenHelper.getUsernameFromToken(token);
        if(userService.userReceivedRequest(username, userToCheckUsername)) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.OK.value(), true)
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), false)
                , HttpStatus.OK);
    }
}
