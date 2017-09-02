package mk.edu.ukim.feit.bolt.api.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrejnaumovski on 8/7/17.
 */
@Component
public class LogoutSuccess implements LogoutSuccessHandler {
    private ObjectMapper objectMapper;

    @Autowired
    public LogoutSuccess(ObjectMapper objectMapper) {
        if(objectMapper == null) {
            throw new IllegalArgumentException(ObjectMapper.class.getName() + " cannot be null.");
        }
        this.objectMapper = objectMapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    }
}
