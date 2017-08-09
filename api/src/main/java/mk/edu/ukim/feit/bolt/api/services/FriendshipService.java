package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.User;

/**
 * Created by gjorgjim on 8/9/17.
 */
public interface FriendshipService {
    void sendFriendRequest(Long senderId, Long receiverId);
    void acceptFriendRequest(Long senderId, Long receiverId);
    void declineFriendRequest(Long senderId, Long receiverId);
}
