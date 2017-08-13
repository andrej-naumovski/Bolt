package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by andrejnaumovski on 8/13/17.
 */
@Repository
public interface ChatGroupRepository extends CrudRepository<ChatGroup, Long> {
    List<ChatGroup> findByInterestName(String name);
    ChatGroup findByName(String name);
}
