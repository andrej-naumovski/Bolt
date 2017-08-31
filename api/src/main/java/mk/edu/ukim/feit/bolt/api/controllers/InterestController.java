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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by gjorgjim on 8/14/17.
 */
@RestController
@RequestMapping(value = "/interest", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterestController {
    private InterestService interestService;
    private TokenHelper tokenHelper;

    @Autowired
    InterestController(InterestService interestService) {
        if(interestService == null) {
            throw new IllegalArgumentException(InterestService.class.getName() + " cannot be null.");
        }
        this.interestService = interestService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAllInterests() {
        List<Interest> list = interestService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity getInterestByName(@PathVariable String name) {
        Interest interest = interestService.findByName(name);
        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getInterestById(@PathVariable Long id) {
        Interest interest = interestService.findById(id);
        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity saveInterest(@RequestBody Interest interest){
        Interest in = interestService.save(interest);
        return new ResponseEntity<>(in, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInterest(@PathVariable Long id){
        interestService.delete(id);
        return new ResponseEntity<>(new GenericResponse<>(HttpStatus.OK.value(), "Interest successfully deleted"), HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity getInterestsByUsername(@PathVariable String username) {
        List<Interest> interests = interestService.findInterestsByUserUsername(username);
        return new ResponseEntity<>(interests, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity addInterestToUser(@PathVariable(name = "name") String interestName,
                                            HttpServletResponse response,
                                            HttpServletRequest request) {
        String token = tokenHelper.getToken(request);
        String username = tokenHelper.getUsernameFromToken(token);
        interestService.addInterestToUser(username, interestName);
        return new ResponseEntity<>(
                new GenericResponse<>(HttpStatus.OK.value(), "Interest successfully added to user"),
        HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
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
