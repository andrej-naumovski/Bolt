package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gjorgjim on 8/9/17.
 */
@Service
public class FriendshipServiceImpl implements FriendshipService {

    private UserRepository userRepository;

    @Autowired
    public FriendshipServiceImpl(UserRepository userRepository) {

        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null.");
        }

        this.userRepository = userRepository;
    }

    @Override
    public void sendFriendRequest(String senderUsername, String receiverUsername) throws Exception {
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        Set<User> senderRequests = sender.getFriendRequestsSent();
        Set<User> receiverRequests = receiver.getFriendRequestsReceived();
        if(senderRequests == null) {
            senderRequests = new HashSet<>();
        }
        if(receiverRequests == null) {
            receiverRequests = new HashSet<>();
        }
        if(!receiverRequests.contains(sender)
                && !receiver.getFriends().contains(sender)
                && !senderRequests.contains(receiver)
                ) {
            receiverRequests.add(sender);
            senderRequests.add(receiver);
            sender.setFriendRequestsSent(senderRequests);
            receiver.setFriendRequestsReceived(receiverRequests);
            try {
                userRepository.save(receiver);
                userRepository.save(sender);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public void acceptFriendRequest(String senderUsername, String receiverUsername) throws Exception {
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        Set<User> senderFriends = sender.getFriends();
        Set<User> receiverFriends = receiver.getFriends();
        Set<User> senderRequests = sender.getFriendRequestsSent();
        Set<User> receiverRequests = receiver.getFriendRequestsReceived();

        if(senderFriends == null) {
            senderFriends = new HashSet<>();
        }
        if(receiverFriends == null) {
            receiverFriends = new HashSet<>();
        }
        if(senderRequests == null) {
            return;
        }
        if(receiverRequests == null) {
            return;
        }

        if(!senderFriends.contains(receiver)
                && !receiverFriends.contains(sender)
                ) {
            senderFriends.add(receiver);
            receiverFriends.add(sender);
            senderRequests.remove(receiver);
            receiverRequests.remove(sender);
            sender.setFriendRequestsSent(senderRequests);
            sender.setFriends(senderFriends);
            receiver.setFriendRequestsReceived(receiverRequests);
            receiver.setFriends(receiverFriends);
            try {
                userRepository.save(sender);
                userRepository.save(receiver);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public void declineFriendRequest(String senderUsername, String receiverUsername) throws Exception {
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        Set<User> senderRequests = sender.getFriendRequestsSent();
        Set<User> receiverRequests = receiver.getFriendRequestsReceived();

        if(senderRequests == null) {
            return;
        }
        if(receiverRequests == null) {
            return;
        }

        if(senderRequests.contains(receiver)
                && receiverRequests.contains(sender)
                ) {
            senderRequests.remove(receiver);
            receiverRequests.remove(sender);
            sender.setFriendRequestsSent(senderRequests);
            receiver.setFriendRequestsReceived(receiverRequests);
            try {
                userRepository.save(sender);
                userRepository.save(receiver);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public void deleteFriend(String senderUsername, String receiverUsername) throws Exception {
        User senderUser = userRepository.findByUsername(senderUsername);
        User receiverUser = userRepository.findByUsername(receiverUsername);
        if(senderUser.getFriends().contains(receiverUser) &&
                receiverUser.getFriends().contains(senderUser)) {
            senderUser.getFriends().remove(receiverUser);
            receiverUser.getFriends().remove(senderUser);
            userRepository.save(senderUser);
            userRepository.save(receiverUser);
        }
    }

    @Override
    public List<User> friendRequestsSent(String username) {
        return new ArrayList<>(userRepository.findByUsername(username).getFriendRequestsSent());
    }

    @Override
    public List<User> friendRequestsReceived(String username) {
        return new ArrayList<>(userRepository.findByUsername(username).getFriendRequestsReceived());
    }
}
