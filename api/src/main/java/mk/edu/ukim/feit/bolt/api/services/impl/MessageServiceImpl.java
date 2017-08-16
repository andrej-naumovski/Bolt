package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.MessageRepository;
import mk.edu.ukim.feit.bolt.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by andrejnaumovski on 8/12/17.
 */

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        if(messageRepository == null) {
            throw new IllegalArgumentException(MessageRepository.class.getName() + " cannot be null.");
        }
        this.messageRepository = messageRepository;
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findOne(id);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public List<Message> findBySenderAndReceiver(String sender, String receiver) {
        return messageRepository.findBySenderUserUsernameAndReceiverUserUsername(sender, receiver);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        messageRepository.delete(id);
    }

    @Override
    public List<User> findLastUsersFromChat(String username) {
        List<Message> allMessages = messageRepository.findBySenderUserUsername(username);
        allMessages.addAll(messageRepository.findByRecieverUserUsername(username));
        Collections.sort(allMessages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if(o1.getTimestamp().getTime() > o2.getTimestamp().getTime())
                    return -1;
                if(o1.getTimestamp().getTime() < o2.getTimestamp().getTime())
                    return 1;
                return 0;
            }
        });
        List<User> users = new ArrayList<>();
        for(Message message : allMessages) {
            if(!users.contains(message.getSenderUser())) {
                if(!message.getSenderUser().getUsername().equals(username)) {
                    users.add(message.getSenderUser());
                }
            }
            if(!users.contains(message.getReceiverUser())) {
                if(!message.getReceiverUser().getUsername().equals(username)) {
                    users.add(message.getReceiverUser());
                }
            }
        }
        return users;
    }
}
