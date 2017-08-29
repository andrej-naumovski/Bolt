package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.Authority;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by andrejnaumovski on 8/19/17.
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByName(String name);
}
