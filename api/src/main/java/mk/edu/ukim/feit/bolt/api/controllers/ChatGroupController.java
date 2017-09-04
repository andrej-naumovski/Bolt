package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatGroupController {
    private ChatGroupService chatGroupService;
    private TokenHelper tokenHelper;

    @Autowired
    public ChatGroupController(ChatGroupService chatGroupService, TokenHelper tokenHelper) {
        if(chatGroupService == null) {
            throw new IllegalArgumentException(ChatGroupService.class.getName() + " cannot be null.");
        }
        if(tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        this.tokenHelper = tokenHelper;
        this.chatGroupService = chatGroupService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAllGroups() {
        return new ResponseEntity<>(chatGroupService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity getGroupByName(@PathVariable String name) {
        ChatGroup group = chatGroupService.findByName(name);
        if(group == null) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.NOT_FOUND.value(), String.format("ChatGroup with name %s does not exist.", name)),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody ChatGroup group) {
        ChatGroup savedGroup;
        try {
            savedGroup = chatGroupService.save(group);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The group could not be created at this time. Please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(savedGroup, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        try {
            chatGroupService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The group could not be deleted at this time. Please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "The group has been deleted."),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity findGroupsByInterest(@RequestParam String interestName) {
        return new ResponseEntity<>(chatGroupService.findByInterestName(interestName), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity findByUserUsername(HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        List<ChatGroup> chatGroups = chatGroupService.findByUserUsername(username);
        return new ResponseEntity<>(chatGroups, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    public ResponseEntity findRecommendedGroupsByUser(HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        List<ChatGroup> chatGroups = chatGroupService.findRecommendedGroupsByUser(username);
        return new ResponseEntity<>(chatGroups, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/subscribe/{group}", method = RequestMethod.POST)
    public ResponseEntity subscribe(@PathVariable String group, HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        try {
            chatGroupService.subscribe(group, username);
        } catch (Exception e){
            return new ResponseEntity<>(new GenericResponse<>(HttpStatus.BAD_REQUEST.value(), "Error subscribing to group. Please try again."),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse<>(HttpStatus.OK.value(), "Successfully subscribed."),
                HttpStatus.OK);
    }
}
