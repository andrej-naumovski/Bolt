package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.Interest;

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
}
