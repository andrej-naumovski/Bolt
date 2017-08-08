package mk.edu.ukim.feit.bolt.api.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;

/**
 * Created by andrejnaumovski on 8/7/17.
 */

public class AnonAuthentication extends AbstractAuthenticationToken {

    public AnonAuthentication() {
        super( null );
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        return true;
    }


}
