package mk.edu.ukim.feit.bolt.api.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by andrejnaumovski on 9/2/17.
 */
@Component
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    }
}
