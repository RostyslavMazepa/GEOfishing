package com.geofishing.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geofishing.dto.SocAuthDTO;
import com.geofishing.model.FacebookAccount;
import com.geofishing.model.User;
import com.geofishing.repository.FacebookAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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


    public boolean verifyToken(Long fbId, String fbToken) {
        uriBuilder = URIBuilder.fromUri("https://graph.facebook.com/debug_token?");
        uriBuilder.queryParam("input_token", fbToken);
        uriBuilder.queryParam("access_token", clientId + "|" + clientSecret);

        RestTemplate restTemplate = new RestTemplate();

        String forObject = restTemplate.getForObject(uriBuilder.build().toString(), String.class);

        Long user_id = null;
        try {
            user_id = new ObjectMapper().readTree(forObject).findValue("user_id").asLong();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user_id != fbId) throw new InvalidTokenException("Facebook token doesn'n match with given user ID");

        return true;
    }

    public User bindFacebookAccount(User user, SocAuthDTO dto) {
        FacebookAccount facebookAccount = facebookAccountRepository.save(new FacebookAccount(dto));
        user.setFacebookAccount(facebookAccount);
        userService.saveRegisteredUser(user);
        return user;
    }
}
