package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.GenericResponse;
import mk.edu.ukim.feit.bolt.api.models.Interest;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import mk.edu.ukim.feit.bolt.api.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by gjorgjim on 8/14/17.
 */
@RestController
@RequestMapping(value = "/interests", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterestController {
    private InterestService interestService;
    private TokenHelper tokenHelper;

    @Autowired
    InterestController(InterestService interestService, TokenHelper tokenHelper) {
        if (interestService == null) {
            throw new IllegalArgumentException(InterestService.class.getName() + " cannot be null.");
        }
        if (tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        this.interestService = interestService;
        this.tokenHelper = tokenHelper;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getAllInterests() {
        List<Interest> list = interestService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getInterestByName(@PathVariable String name) {
        Interest interest = interestService.findByName(name);
        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getInterestById(@PathVariable Long id) {
        Interest interest = interestService.findById(id);
        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    @RequestMapping(value = "/parent/{parent}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity saveInterest(@RequestBody Interest interest, @PathVariable(required = false) String parent) {
        Interest in;
        if (parent == null) {
            in = interestService.save(interest);
        } else {
            Interest par = interestService.findByName(parent);
            if (par == null) {
                return new ResponseEntity<>(
                        new GenericResponse<>(HttpStatus.NOT_FOUND.value(), "Parent interest not found."),
                        HttpStatus.NOT_FOUND
                );
            }
            if(par.getChildInterests() == null) {
                par.setChildInterests(new HashSet<>());
            }
            interest.setParentInterest(par);
            //par.getChildInterests().add(interest);
            in = interestService.save(interest);
            //List<Interest> interestList = new ArrayList<>(par.getChildInterests());
            //in = interestList.get(interestList.size() - 1);
        }
        return new ResponseEntity<>(in, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity deleteInterest(@PathVariable Long id) {
        interestService.delete(id);
        return new ResponseEntity<>(new GenericResponse<>(HttpStatus.OK.value(), "Interest successfully deleted"), HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getInterestsByUsername(@PathVariable String username) {
        List<Interest> interests = interestService.findInterestsByUserUsername(username);
        return new ResponseEntity<>(interests, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity addInterestToUser(@PathVariable(name = "name") String interestName,
                                            HttpServletResponse response,
                                            HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        Interest interest = interestService.findByName(interestName);
        try {
            interestService.addInterestToUser(username, interestName);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(
                    new GenericResponse<>(HttpStatus.CONFLICT.value(), "User already has interest."),
                    HttpStatus.CONFLICT
            );
        }
        return new ResponseEntity<>(
                interest,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{name}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity deleteInterestFromUser(@PathVariable(name = "name") String interestName,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        interestService.deleteInterestFromUser(username, interestName);
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "Interest successfully deleted from user"),
                HttpStatus.OK);
    }
}
