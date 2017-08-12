package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by andrejnaumovski on 8/12/17.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findBySenderUserUsernameAndReceiverUserUsername(String senderUserUsername, String receiverUserUsername);
}
