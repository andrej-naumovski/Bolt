package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Interest;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.InterestRepository;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.InterestService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by gjorgjim on 8/14/17.
 */
@Service
public class InterestServiceImpl implements InterestService {
    private InterestRepository interestRepository;
    private UserRepository userRepository;

    public InterestServiceImpl(InterestRepository interestRepository, UserRepository userRepository) {
        if(interestRepository == null) {
            throw new IllegalArgumentException(InterestRepository.class.getName() + " cannot be null");
        }
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null");
        }
        this.interestRepository = interestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Interest findByName(String name) {
        return interestRepository.findByName(name);
    }

    @Override
    public Interest findById(Long id) {
        return interestRepository.findOne(id);
    }

    @Override
    public List<Interest> findAll() {
        return (List<Interest>)interestRepository.findAll();
    }

    @Override
    public Interest save(Interest interest) {
        return interestRepository.save(interest) ;
    }

    @Override
    public void delete(Long id) {
        interestRepository.delete(id);
    }

    @Override
    public List<Interest> findInterestsByUserUsername(String username) {
        User user = userRepository.findByUsername(username);
        return new ArrayList<>(user.getInterests());
    }

    @Override
    public void addInterestToUser(String username, String interestName) throws EntityExistsException {
        Interest interest = interestRepository.findByName(interestName);
        User user = userRepository.findByUsername(username);
        if(user.getInterests().contains(interest)) {
            throw new EntityExistsException();
        }
        user.getInterests().add(interest);
        userRepository.save(user);
    }

    @Override
    public void deleteInterestFromUser(String username, String interestName) {
        User user = userRepository.findByUsername(username);
        Interest interest = interestRepository.findByName(interestName);
        user.getInterests().remove(interest);
        userRepository.save(user);
    }
}
