package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.FriendshipService;
import mk.edu.ukim.feit.bolt.api.models.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gjorgjim on 8/9/17.
 */
@RestController
@RequestMapping(value = "/friends", produces = MediaType.APPLICATION_JSON_VALUE)
public class FriendshipController {
    private FriendshipService friendshipService;
    private TokenHelper tokenHelper;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, TokenHelper tokenHelper) {
        if(friendshipService == null) {
            throw new IllegalArgumentException(FriendshipService.class.getName() + " cannot be null.");
        }
        if(tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        this.friendshipService = friendshipService;
        this.tokenHelper = tokenHelper;
    }

    @RequestMapping(value = "/add/{username}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity sendFriendRequest(
            @PathVariable String username,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = tokenHelper.getToken(request);
        String senderUsername = tokenHelper.getUsernameFromToken(token);
        try {
            friendshipService.sendFriendRequest(senderUsername, username);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error sending request, please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/accept/{username}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity acceptFriendRequest(
            @PathVariable String username,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = tokenHelper.getToken(request);
        String receiverUsername = tokenHelper.getUsernameFromToken(token);

        try {
            friendshipService.acceptFriendRequest(username, receiverUsername);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error accepting request, please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/decline/{username}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity declineFriendRequest(
            @PathVariable String username,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = tokenHelper.getToken(request);
        String receiverUsername = tokenHelper.getUsernameFromToken(token);

        try {
            friendshipService.declineFriendRequest(username, receiverUsername);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error declining request, please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
