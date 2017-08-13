package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findByInterestName(String name);
    Group findByName(String name);
}
