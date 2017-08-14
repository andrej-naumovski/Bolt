package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Interest;
import mk.edu.ukim.feit.bolt.api.repositories.InterestRepository;
import mk.edu.ukim.feit.bolt.api.services.InterestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gjorgjim on 8/14/17.
 */
@Service
public class InterestServiceImpl implements InterestService {
    private InterestRepository interestRepository;

    public InterestServiceImpl(InterestRepository interestRepository) {
        if(interestRepository == null) {
            throw new IllegalArgumentException(InterestRepository.class.getName() + " cannot be null");
        }
        this.interestRepository = interestRepository;
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
}
