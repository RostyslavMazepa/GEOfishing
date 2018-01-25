package com.geofishing.controllers;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.geofishing.model.Fish;
import com.geofishing.model.Role;
import com.geofishing.model.User;
import com.geofishing.repository.FishRepository;
import com.geofishing.repository.RoleRepository;
import com.geofishing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/fishes")
public class FishController {

    @Autowired
    FishRepository fishRepository;



    @RequestMapping(value = "/getAll")
    public List<Fish> retrieveFishes(){
        return fishRepository.findAll();
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Fish add(Fish fish) throws Exception {
        Fish registered = fishRepository.save(fish);
        if (registered == null) {throw new Exception("Something wrong...");}
        System.out.println(        registered.getFishType().toString()
        );
        return registered;
    }


}
