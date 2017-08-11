package mk.edu.ukim.feit.bolt.api.services;

/**
 * Created by gjorgjim on 8/9/17.
 */
public interface FriendshipService {
    void sendFriendRequest(String senderUsername, String receiverUsername) throws Exception;
    void acceptFriendRequest(String senderUsername, String receiverUsername) throws Exception;
    void declineFriendRequest(String senderUsername, String receiverUsername) throws Exception;
}
