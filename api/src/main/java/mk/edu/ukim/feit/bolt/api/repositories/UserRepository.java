package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by andrejnaumovski on 8/7/17.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
