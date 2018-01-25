package com.geofishing.controllers;


import com.geofishing.model.Role;
import com.geofishing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public List<Role> retrieveRoles(){
        return roleRepository.findAll();
    }


    @RequestMapping(value = "/roles",method = RequestMethod.POST)
    public Role addRole(Role role){
        return roleRepository.save(role);
    }



}
