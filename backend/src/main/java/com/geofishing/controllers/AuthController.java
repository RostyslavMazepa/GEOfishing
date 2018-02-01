package com.geofishing.controllers;

import com.geofishing.dto.SocAuthDTO;
import com.geofishing.dto.UserDTO;
import com.geofishing.model.FacebookAccount;
import com.geofishing.model.User;
import com.geofishing.repository.FacebookAccountRepository;
import com.geofishing.repository.UserRepository;
import com.geofishing.services.FacebookService;
import com.geofishing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    FacebookService facebookService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    FacebookAccountRepository facebookAccountRepository;

    @RequestMapping(value = "oauth/socialAuth",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody SocAuthDTO dto){
        User user = null;
        boolean isTokenValid;
        ResponseEntity responseEntity;
        if(dto.getSocialNetwork().equals("facebook")) {
            if(dto.getUserId()!=null && !dto.getUserId().isEmpty()) {
                 user = userRepository.findByFacebookAccount_FacebookID(dto.getUserId());
            }

            isTokenValid = facebookService.verifyToken(dto.getUserId(), dto.getUserToken());

            if(user == null){
                user = userService.findUserByEmail(dto.getUserEmail());
                if(user != null && isTokenValid){
                    FacebookAccount facebookAccount = facebookAccountRepository.save(new FacebookAccount(dto.getUserId(), dto.getUserImageURL(), dto.getUserToken(), Integer.getInteger(dto.getExpiresIn())));
                            user.setFacebookAccount(facebookAccount);
                    userService.saveRegisteredUser(user);
                }
            };

            if(user != null && isTokenValid){
                OAuth2AccessToken userToken = userService.getUserToken(user);
                responseEntity = new ResponseEntity<>(userToken, HttpStatus.OK);
                return responseEntity;

            }


            //ResponseEntity<User> r = new ResponseEntity<>(new User(),HttpStatus.CREATED)
        }

        if(user == null){

            user = userService.registerNewUserAccount(new UserDTO(dto));
            responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
            return responseEntity;
        }

        return new ResponseEntity("Somthing wrong",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "oauth/snauth",method = RequestMethod.GET)
    public void test(){
        String token = "EAAcsnEr99UsBADRbes2wPStznLPAEPCFZAnscaEymZCCL0bAy6gHC4ZAC3e60EYi6RTL7YEpGFxrXVDAoik4gD0wOdz9KHujKWko894oCuIGCnnkvqKQFsmtQycRDG9kYs6qZBZAQC3daWVgmIjuSqZA6nr7aCuyHcUoikjh1Nt1CSaFuYwqqSl2pB4UYxZBu0ZD";
        //ResponseEntity<User> r = new ResponseEntity<>(new User(),HttpStatus.CREATED)
        facebookService.verifyToken("123",token);

    }

}
