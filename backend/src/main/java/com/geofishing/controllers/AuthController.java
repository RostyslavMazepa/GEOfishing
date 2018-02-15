package com.geofishing.controllers;

import com.geofishing.auth.swagger.SerializedToken;
import com.geofishing.dto.SocAuthDTO;
import com.geofishing.services.AuthResult;
import com.geofishing.services.SocialNetwork;
import com.geofishing.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Api(tags = "authentication")
@RestController
public class AuthController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> greeting() {
        return new ResponseEntity("Hi, this is a resource server. Please go to the main page - geosportfishing.top", HttpStatus.OK);
    }

    @ApiOperation(value = "authorize or register user",
            notes = "Authorizes or registers a user using the social network token",
            response = SerializedToken.class
    )
    @RequestMapping(value = "oauth/socialAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody SocAuthDTO snTokenDTO) {
        ResponseEntity responseEntity = null;
        SocialNetwork sn = SocialNetwork.valueOf(snTokenDTO.getSocialNetwork().toUpperCase(Locale.ENGLISH));
        String token = (sn == SocialNetwork.FACEBOOK ? snTokenDTO.getAccessToken() : snTokenDTO.getIdToken());
        String refreshToken = snTokenDTO.getRefreshToken();
        AuthResult authResult = userService.authFlow(token, refreshToken, sn);
        OAuth2AccessToken userToken = userService.getUserToken(authResult.getUser());
        switch (authResult.getResultType()) {
            case REGISTRED:
                responseEntity = new ResponseEntity<>(userToken, HttpStatus.CREATED);
                break;
            case FOUND:
                responseEntity = new ResponseEntity<>(userToken, HttpStatus.OK);
                break;
        }
        return responseEntity;
    }


}
