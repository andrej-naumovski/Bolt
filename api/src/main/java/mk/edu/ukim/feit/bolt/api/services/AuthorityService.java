package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.Authority;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/19/17.
 */
public interface AuthorityService {
    Authority findById(Long id);
    List<Authority> findAll();
    Authority save(Authority a);
    void delete(Long id);
    Authority findByName(String name);
}
