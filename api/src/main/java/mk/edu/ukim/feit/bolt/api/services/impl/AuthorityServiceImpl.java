package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.Authority;
import mk.edu.ukim.feit.bolt.api.repositories.AuthorityRepository;
import mk.edu.ukim.feit.bolt.api.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/19/17.
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        if(authorityRepository == null) {
            throw new IllegalArgumentException(AuthorityRepository.class.getName() + " cannot be null.");
        }
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findOne(id);
    }

    @Override
    public List<Authority> findAll() {
        return (List<Authority>) authorityRepository.findAll();
    }

    @Override
    public Authority save(Authority a) {
        return authorityRepository.save(a);
    }

    @Override
    public void delete(Long id) {
        authorityRepository.delete(id);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }
}
