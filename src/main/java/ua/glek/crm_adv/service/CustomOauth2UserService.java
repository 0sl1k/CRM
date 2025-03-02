package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.model.AuthProvider;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.repository.UserRepo;


@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private  UserRepo userRepo;
    @Autowired
    public CustomOauth2UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        AuthProvider provider = AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        User user = userRepo.findByEmail(email)
                .orElseGet(()->new User(email,name));
        user.setUsername(name);
        user.setEmail(email);

        userRepo.save(user);
        return oAuth2User;
    }
}
