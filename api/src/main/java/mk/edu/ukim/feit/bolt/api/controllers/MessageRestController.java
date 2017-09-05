package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.GroupMessage;
import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.MessageService;

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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {
    private MessageService messageService;
    private TokenHelper tokenHelper;

    @Autowired
    public MessageRestController(MessageService messageService, TokenHelper tokenHelper) {
        if (messageService == null)
            throw new IllegalArgumentException(MessageService.class.getName() + " cannot be null");
        if (tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        this.messageService = messageService;
        this.tokenHelper = tokenHelper;
    }

    @RequestMapping(value = "/chat/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getChat(@PathVariable String username, HttpServletRequest request){
        String token = tokenHelper.getToken(request);
        String currentUser = tokenHelper.getUsernameFromToken(token);
        List<Message> messages = messageService.getChatArchive(currentUser, username);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return new ResponseEntity<>(new GenericResponse<>(HttpStatus.OK.value(), "Message successfully deleted"), HttpStatus.OK);
    }

    @RequestMapping(value = "/last", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getLastUsersFromChat(HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        List<User> users = messageService.findLastUsersFromChat(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getFavoriteUsers(HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token); 
        List<User> users = messageService.findFavoriteUsers(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/chat/group/{group}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getGroupChatMessages(@PathVariable String group) {
        List<GroupMessage> messages = messageService.getGroupChatArchive(group);
        if(messages == null) {
            messages = new ArrayList<>();
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
