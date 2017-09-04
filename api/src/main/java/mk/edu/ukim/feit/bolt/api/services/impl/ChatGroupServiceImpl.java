package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import mk.edu.ukim.feit.bolt.api.models.Interest;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.ChatGroupRepository;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
@Service
public class ChatGroupServiceImpl implements ChatGroupService {
    private ChatGroupRepository chatGroupRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatGroupServiceImpl(ChatGroupRepository chatGroupRepository, UserRepository userRepository) {
        if(chatGroupRepository == null) {
            throw new IllegalArgumentException(ChatGroupRepository.class.getName() + " cannot be null.");
        }
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null");
        }
        this.chatGroupRepository = chatGroupRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<ChatGroup> findByUserUsername(String username) {
        List<ChatGroup> all = findAll();
        List<ChatGroup> chatGroups = new ArrayList<>();
        for( ChatGroup curr : all ) {
            for( User user : curr.getUsers()) {
                if(user.getUsername().equals(username)) {
                    chatGroups.add(curr);
                    break;
                }
            }
        }

        return chatGroups;
    }

    @Override
    public List<ChatGroup> findRecommendedGroupsByUser(String username) {
        User user = userRepository.findByUsername(username);
        List<Interest> interestsByUser = new ArrayList<>(user.getInterests());
        List<Interest> allInterests  = new ArrayList<>();
        allInterests.addAll(interestsByUser);

        for(Interest interest : interestsByUser) {
            if(interest.getParentInterest() != null) {
                allInterests.add(interest.getParentInterest());
            }
            if(interest.getChildInterests() != null && interest.getChildInterests().size() > 0) {
                allInterests.addAll(interest.getChildInterests());
            }
        }

        List<ChatGroup> chatGroups = new ArrayList<>();
        for(Interest interest : allInterests) {
            List<ChatGroup> chatGroupList = findByInterestName(interest.getName());
            if(chatGroupList != null && chatGroupList.size() > 0) {
                chatGroups.addAll(chatGroupList);
            }
        }

        return chatGroups;
    }
}
