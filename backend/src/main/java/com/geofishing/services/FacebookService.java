package com.geofishing.services;

import com.geofishing.dto.FacebookUser;
import com.geofishing.model.auth.User;
import com.geofishing.model.social.FacebookAccount;
import com.geofishing.repository.FacebookAccountRepository;
import com.geofishing.repository.UserRepository;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
@Service
public class FacebookService {

    private URIBuilder uriBuilder;

    @Value("${facebook.appid}")
    private String clientId;
    @Value("${facebook.secret}")
    private String clientSecret;
    @Autowired
    FacebookAccountRepository facebookAccountRepository;
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    public User bindFacebookAccount(User user, FacebookUser dto, String accessToken, String refreshToken) {
        if (dto == null) {
            dto = getUserInfo(accessToken);
        }
        FacebookAccount facebookAccount = facebookAccountRepository.save(new FacebookAccount(dto, refreshToken));
        user.setFacebookAccount(facebookAccount);
        userService.saveRegisteredUser(user);
        return user;
    }


    public FacebookUser getUserInfo(String token) {
        FacebookClient fb = new DefaultFacebookClient(token, clientSecret, Version.VERSION_2_11);
        return fb.fetchObject("me", FacebookUser.class, Parameter.with("fields", "id,email,first_name,last_name,birthday,picture.type(normal){url}"));
    }

    public boolean validateToken(String accessToken) {
        FacebookClient.DebugTokenInfo debugTokenInfo = getFacebookClient(accessToken).debugToken(accessToken);
        return debugTokenInfo.isValid();
    }

    public AuthResult authFlow(String accessToken, String refreshToken) {
        AuthResult.AuthResultType resultType = AuthResult.AuthResultType.FOUND;
        if (validateToken(accessToken)) {
            FacebookUser fbUser = getUserInfo(accessToken);
            User user = userRepository.findByFacebookAccount_UserIdOrEmail(fbUser.getId(), fbUser.getEmail());
            if (user == null) {
                user = userService.registerNewUserAccount(fbUser.getEmail(), fbUser.getFirstName(), fbUser.getLastName());
                resultType = AuthResult.AuthResultType.REGISTRED;
            }

            if (user.getFacebookAccount() == null) {
                return new AuthResult(bindFacebookAccount(user, fbUser, accessToken, refreshToken), resultType);
            }
            return new AuthResult(user, resultType);

        } else {
            throw new BadCredentialsException("Access Token is not valid");
        }
    }

    private FacebookClient getFacebookClient(String accessToken) {
        return new DefaultFacebookClient(accessToken, clientSecret, Version.VERSION_2_11);
    }




}
