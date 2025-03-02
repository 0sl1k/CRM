package ua.glek.crm_adv.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.glek.crm_adv.jwt.JwtUtils;
import ua.glek.crm_adv.service.UserDetailsImpl;

import java.io.IOException;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String password = oauth2User.getAttribute("password");
        String username =  oauth2User.getAttribute("name");
        UserDetailsImpl  userDetails = new UserDetailsImpl(username, password, email, oauth2User.getAttribute("authorities"));

        Cookie responseCookie = jwtUtils.generateJwtCookie(userDetails);
        response.addCookie(responseCookie);
        response.sendRedirect("/authenticate");



    }
}
