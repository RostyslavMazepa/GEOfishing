package com.geofishing.controllers;

import com.geofishing.model.Role;
import com.geofishing.model.User;
import com.geofishing.repository.RoleRepository;
import com.geofishing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public List<User> retrieveUsers(){
        return userRepository.findAll();
    }


//    @RequestMapping(value = "/getAllSimpl")
//    public List<User> retrieveMainUsersData(){
//        return userRepository.findAll();
//    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public User register(User user) throws Exception {
        System.out.println(user.toString());
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);
        User registered = userRepository.save(user);
        registered.getRoles();
        if (registered == null) {throw new Exception("Something wrong...");}
        return registered;
    }

}
