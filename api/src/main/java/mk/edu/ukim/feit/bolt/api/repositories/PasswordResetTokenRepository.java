package mk.edu.ukim.feit.bolt.api.repositories;

import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by gjorgjim on 8/11/17.
 */
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
