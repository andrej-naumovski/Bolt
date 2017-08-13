package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.Interest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gjorgjim on 8/13/17.
 */
@Repository
public interface InterestRepository extends CrudRepository<Interest, Long>{
    Interest findByName(String name);
}
