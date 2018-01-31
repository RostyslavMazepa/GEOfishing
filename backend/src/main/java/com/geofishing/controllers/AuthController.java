package com.geofishing.controllers;

import com.geofishing.dto.SocAuthDTO;
import com.geofishing.dto.UserDTO;
import com.geofishing.model.User;
import com.geofishing.repository.UserRepository;
import com.geofishing.services.FacebookService;
import com.geofishing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    FacebookService facebookService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @RequestMapping(value = "oauth/socialAuth",method = RequestMethod.POST)
    public void authenticate(@RequestBody SocAuthDTO dto){
        if(dto.getSocialNetwork()=="facebook") {
            User user = userRepository.findByFacebookAccount_FacebookID(dto.getUserId());
            if(user == null){

                user = userService.registerNewUserAccount(new UserDTO(dto));

            };


            String token = "EAAcsnEr99UsBADRbes2wPStznLPAEPCFZAnscaEymZCCL0bAy6gHC4ZAC3e60EYi6RTL7YEpGFxrXVDAoik4gD0wOdz9KHujKWko894oCuIGCnnkvqKQFsmtQycRDG9kYs6qZBZAQC3daWVgmIjuSqZA6nr7aCuyHcUoikjh1Nt1CSaFuYwqqSl2pB4UYxZBu0ZD";
            //ResponseEntity<User> r = new ResponseEntity<>(new User(),HttpStatus.CREATED)
            facebookService.verifyToken("123", token);
        }
    }

    @RequestMapping(value = "oauth/snauth",method = RequestMethod.GET)
    public void test(){
        String token = "EAAcsnEr99UsBADRbes2wPStznLPAEPCFZAnscaEymZCCL0bAy6gHC4ZAC3e60EYi6RTL7YEpGFxrXVDAoik4gD0wOdz9KHujKWko894oCuIGCnnkvqKQFsmtQycRDG9kYs6qZBZAQC3daWVgmIjuSqZA6nr7aCuyHcUoikjh1Nt1CSaFuYwqqSl2pB4UYxZBu0ZD";
        //ResponseEntity<User> r = new ResponseEntity<>(new User(),HttpStatus.CREATED)
        facebookService.verifyToken("123",token);

    }

}
