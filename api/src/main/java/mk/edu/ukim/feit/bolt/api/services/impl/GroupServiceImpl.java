package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Group;
import mk.edu.ukim.feit.bolt.api.repositories.GroupRepository;
import mk.edu.ukim.feit.bolt.api.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        if(groupRepository == null) {
            throw new IllegalArgumentException(GroupRepository.class.getName() + " cannot be null.");
        }
        this.groupRepository = groupRepository;
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public List<Group> findAll() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Group save(Group group) throws Exception {
        try {
            return groupRepository.save(group);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            groupRepository.delete(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<Group> findByInterestName(String name) {
        return groupRepository.findByInterestName(name);
    }

    @Override
    public Group findByName(String name) {
        return groupRepository.findByName(name);
    }
}
