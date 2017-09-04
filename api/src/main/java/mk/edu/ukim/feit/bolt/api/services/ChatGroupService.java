package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.ChatGroup;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
public interface ChatGroupService {
    ChatGroup findById(Long id);
    List<ChatGroup> findAll();
    ChatGroup save(ChatGroup group) throws Exception;
    void delete(Long id) throws Exception;
    List<ChatGroup> findByInterestName(String name);
    ChatGroup findByName(String name);
    List<ChatGroup> findByUserUsername(String username);
    List<ChatGroup> findRecommendedGroupsByUser(String username);
}
