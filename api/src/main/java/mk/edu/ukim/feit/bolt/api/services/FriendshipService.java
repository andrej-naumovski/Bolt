package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.User;

import java.util.List;

/**
 * Created by gjorgjim on 8/9/17.
 */
public interface FriendshipService {
    void sendFriendRequest(String senderUsername, String receiverUsername) throws Exception;
    void acceptFriendRequest(String senderUsername, String receiverUsername) throws Exception;
    void declineFriendRequest(String senderUsername, String receiverUsername) throws Exception;
    void deleteFriend(String senderUsername, String receiverUsername) throws Exception;
    List<User> friendRequestsSent(String username);
    List<User> friendRequestsReceived(String username);
}
