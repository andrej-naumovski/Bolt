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
    public void sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findOne(senderId);
        User receiver = userRepository.findOne(receiverId);
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
            userRepository.save(receiver);
            userRepository.save(sender);
        }
    }

    @Override
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findOne(senderId);
        User receiver = userRepository.findOne(receiverId);
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
            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }

    @Override
    public void declineFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findOne(senderId);
        User receiver = userRepository.findOne(receiverId);
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
            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }
}
