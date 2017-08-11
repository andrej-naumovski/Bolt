package mk.edu.ukim.feit.bolt.api.exceptions;

/**
 * Created by gjorgjim on 8/11/17.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String username) {
        super(String.format("User with username %s has not been found.", username));
    }
}
