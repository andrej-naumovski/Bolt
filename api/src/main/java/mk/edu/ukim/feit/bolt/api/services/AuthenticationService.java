package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.exceptions.UserNotFoundException;
import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import mk.edu.ukim.feit.bolt.api.models.User;

/**
 * Created by gjorgjim on 8/11/17.
 */
public interface AuthenticationService {
    PasswordResetToken generatePasswordResetToken(String username) throws Exception;
    boolean isTokenValid(String token);
    void deletePasswordResetToken(String token);
    void resetPassword(String token, String password) throws UserNotFoundException;
}
