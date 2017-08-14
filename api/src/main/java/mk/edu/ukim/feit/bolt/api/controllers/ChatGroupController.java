package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import mk.edu.ukim.feit.bolt.api.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by andrejnaumovski on 8/13/17.
 */

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatGroupController {
    private ChatGroupService chatGroupService;

    @Autowired
    public ChatGroupController(ChatGroupService chatGroupService) {
        if(chatGroupService == null) {
            throw new IllegalArgumentException(ChatGroupService.class.getName() + " cannot be null.");
        }
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
                    new GenericResponse(HttpStatus.NOT_FOUND.value(), String.format("ChatGroup with name %s does not exist.", name)),
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
                    new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The group could not be created at this time. Please try again later."),
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
                    new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The group could not be deleted at this time. Please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                new GenericResponse(HttpStatus.OK.value(), "The group has been deleted."),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity findGroupsByInterest(@RequestParam String interestName) {
        return new ResponseEntity<>(chatGroupService.findByInterestName(interestName), HttpStatus.OK);
    }
}