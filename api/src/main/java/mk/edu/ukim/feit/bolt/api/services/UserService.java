package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.User;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/7/17.
 */
public interface UserService {
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAll();
    User saveUser(User user);
    void deleteUser(Long id);
    boolean userSentRequest(String currUserUsername, String userToCheckUsername);
    boolean userReceivedRequest(String currUserUsername, String userToCheckUsername);
}
