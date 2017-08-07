package mk.edu.ukim.feit.bolt.api.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.models.UserTokenState;
import mk.edu.ukim.feit.bolt.api.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andrejnaumovski on 8/7/17.
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${app.user_cookie}")
    private String USER_COOKIE;

    private TokenHelper tokenHelper;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthenticationSuccessHandler(TokenHelper tokenHelper, ObjectMapper objectMapper) {
        super();
        if (tokenHelper == null) {
            throw new IllegalArgumentException(TokenHelper.class.getName() + " cannot be null.");
        }
        if (objectMapper == null) {
            throw new IllegalArgumentException(ObjectMapper.class.getName() + " cannot be null.");
        }
        this.tokenHelper = tokenHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        User user = (User) authentication.getPrincipal();

        String jws = tokenHelper.generateToken(user.getUsername());

        Cookie authCookie = new Cookie(TOKEN_COOKIE, jws);

        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(EXPIRES_IN);

        Cookie userCookie = new Cookie(USER_COOKIE, (user.getFirstName()));

        userCookie.setPath("/");
        userCookie.setMaxAge(EXPIRES_IN);

        response.addCookie(authCookie);
        response.addCookie(userCookie);

        UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
        String jwtResponse = objectMapper.writeValueAsString(userTokenState);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jwtResponse);
    }
}
