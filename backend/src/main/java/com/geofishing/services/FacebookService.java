package com.geofishing.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public boolean verifyToken(String fbId, String fbToken){
            uriBuilder = URIBuilder.fromUri("https://graph.facebook.com/debug_token?");
            uriBuilder.queryParam("input_token",fbToken);
            uriBuilder.queryParam("access_token", clientId+"|"+clientSecret);
        System.out.println("fb_req: "+uriBuilder.build().toString());
        RestTemplate restTemplate =new RestTemplate();
        String forObject = restTemplate.getForObject(uriBuilder.build().toString(), String.class);
        try {
            String user_id = new ObjectMapper().readTree(forObject).findValue("user_id").textValue();
            if(!user_id.equals(fbId)) throw new InvalidTokenException("Facebook token doesn'n match with given user ID");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }
}
