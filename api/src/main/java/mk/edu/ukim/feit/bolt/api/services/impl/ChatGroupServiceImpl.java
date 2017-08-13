package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import mk.edu.ukim.feit.bolt.api.repositories.ChatGroupRepository;
import mk.edu.ukim.feit.bolt.api.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
@Service
public class ChatGroupServiceImpl implements ChatGroupService {
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    public ChatGroupServiceImpl(ChatGroupRepository chatGroupRepository) {
        if(chatGroupRepository == null) {
            throw new IllegalArgumentException(ChatGroupRepository.class.getName() + " cannot be null.");
        }
        this.chatGroupRepository = chatGroupRepository;
    }

    @Override
    public ChatGroup findById(Long id) {
        return chatGroupRepository.findOne(id);
    }

    @Override
    public List<ChatGroup> findAll() {
        return (List<ChatGroup>) chatGroupRepository.findAll();
    }

    @Override
    public ChatGroup save(ChatGroup group) throws Exception {
        try {
            return chatGroupRepository.save(group);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            chatGroupRepository.delete(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<ChatGroup> findByInterestName(String name) {
        return chatGroupRepository.findByInterestName(name);
    }

    @Override
    public ChatGroup findByName(String name) {
        return chatGroupRepository.findByName(name);
    }
}
