package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.UserRepository;
import mk.edu.ukim.feit.bolt.api.services.ServiceScanMarker;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/7/17.
 */

@Service
public class UserServiceImpl implements ServiceScanMarker, UserService, UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null.");
        }
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("User with username " + s + " not found");
        }
        return user;
    }
}