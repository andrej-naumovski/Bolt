package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.Group;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
public interface GroupService {
    Group findById(Long id);
    List<Group> findAll();
    Group save(Group group) throws Exception;
    void delete(Long id) throws Exception;
    List<Group> findByInterestName(String name);
    Group findByName(String name);
}
