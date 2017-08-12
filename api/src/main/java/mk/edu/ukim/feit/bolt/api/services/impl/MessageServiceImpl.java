package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.repositories.MessageRepository;
import mk.edu.ukim.feit.bolt.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
}
