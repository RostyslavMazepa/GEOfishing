package com.geofishing.controllers;


import com.geofishing.model.Role;
import com.geofishing.model.User;
import com.geofishing.repository.RoleRepository;
import com.geofishing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> retrieveUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/me")
    @ResponseBody
    public User getUser(Principal principal) {
        return userRepository.getUserByUsername(principal.getName());
    }



//    @RequestMapping(value = "/getAllSimpl")
//    public List<User> retrieveMainUsersData(){
//        return userRepository.findAll();
//    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public User register(User user) {
        System.out.println(user.toString());
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);
        return userRepository.save(user);
    }

}
