package pl.krasnowski.DigitalKitchen.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.config.session.SessionConfiguration;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        User loggedUser = userService.findByUsername(auth.getName());
        request.getSession().setMaxInactiveInterval(SessionConfiguration.SESSION_TIMEOUT);
        BeanUtils.copyProperties(loggedUser, user);

        log.info("User {} logged on.", user.getUsername());
        handle(request, response, auth);
        super.clearAuthenticationAttributes(request);
    }
}