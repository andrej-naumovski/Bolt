package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;

/**
 * Created by gjorgjim on 8/11/17.
 */
public interface AuthenticationService {
    PasswordResetToken generatePasswordResetToken(String username) throws Exception;
    boolean isTokenValid(String token);
}
