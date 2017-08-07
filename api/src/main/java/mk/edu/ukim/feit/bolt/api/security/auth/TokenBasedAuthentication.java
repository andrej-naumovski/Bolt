package mk.edu.ukim.feit.bolt.api.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by andrejnaumovski on 8/7/17.
 */

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private String token;
    private final UserDetails principal;

    public TokenBasedAuthentication(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
