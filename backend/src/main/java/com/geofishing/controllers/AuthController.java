package com.geofishing.controllers;

import com.geofishing.dto.SocAuthDTO;
import com.geofishing.dto.UserDTO;
import com.geofishing.model.User;
import com.geofishing.services.FacebookService;
import com.geofishing.services.SocialNetwork;
import com.geofishing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class AuthController {

    @Autowired
    FacebookService facebookService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "oauth/socialAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody SocAuthDTO dto) {
        ResponseType resposeType = ResponseType.TOKEN;
        ResponseEntity responseEntity = null;

        SocialNetwork sn = SocialNetwork.valueOf(dto.getSocialNetwork().toUpperCase(Locale.ENGLISH));

        userService.verifyToken(dto.getUserId(), dto.getAccessToken(), sn);

        User user = userService.findBySocialAccount(dto, sn);
        if (user == null) {
            user = userService.registerNewUserAccount(new UserDTO(dto));
            resposeType = ResponseType.USER;
        }
        user = userService.bindSocialAccount(user, dto, sn);
        switch (resposeType) {
            case USER:
                responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
                break;
            case TOKEN:
                responseEntity = new ResponseEntity<>(userService.getUserToken(user), HttpStatus.OK);
                break;
        }
        return responseEntity;
    }

    enum ResponseType {USER, TOKEN}
}
