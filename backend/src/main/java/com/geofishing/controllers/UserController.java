package com.geofishing.controllers;


import com.geofishing.dto.UserDTO;
import com.geofishing.model.auth.User;
import com.geofishing.repository.RoleRepository;
import com.geofishing.repository.UserRepository;
import com.geofishing.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
@Api(tags = "users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> retrieveUsers(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "get current user", response = User.class)
    @GetMapping(value = "/me")
    @ResponseBody
    public User getUser(Principal principal) {
        return userRepository.getUserByUsername(principal.getName());
    }


    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("username") String username) {
        return userRepository.getUserByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User createUser(@RequestBody UserDTO userDTO) {
        //Post post = convertToEntity(postDto);
        User user = userService.registerNewUserAccount(userDTO);
        return user;
    }

    private UserDTO convertToDto(User user) {
        UserDTO postDto = modelMapper.map(user, UserDTO.class);
        return postDto;
    }

}
