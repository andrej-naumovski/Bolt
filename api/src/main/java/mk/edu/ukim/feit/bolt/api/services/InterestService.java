package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.Interest;

import javax.persistence.EntityExistsException;
import java.util.List;

/**
 * Created by gjorgjim on 8/14/17.
 */
public interface InterestService {
    Interest findByName(String name);
    Interest findById(Long id);
    List<Interest> findAll();
    Interest save(Interest interest);
    void delete(Long id);
    List<Interest> findInterestsByUserUsername(String username);
    void addInterestToUser(String username, String interestName) throws EntityExistsException;
    void deleteInterestFromUser(String username, String interestName);
}
