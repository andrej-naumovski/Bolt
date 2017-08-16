package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.models.User;

import java.util.List;
import java.util.Set;

/**
 * Created by andrejnaumovski on 8/12/17.
 */
public interface MessageService {
    Message findById(Long id);
    List<Message> findAll();
    List<Message> findBySenderAndReceiver(String sender, String receiver);
    Message save(Message message);
    void delete(Long id);
    List<User> findLastUsersFromChat(String username);
    List<User> findFavoriteUsers(String username);
}
