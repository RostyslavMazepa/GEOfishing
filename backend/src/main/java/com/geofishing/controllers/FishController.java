package com.geofishing.controllers;


import com.geofishing.model.Fish;
import com.geofishing.repository.FishRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "fishes")
@RestController
@RequestMapping(value = "/fishes")
public class FishController {

    @Autowired
    FishRepository fishRepository;

    @ApiOperation(value = "get fishes list", response = Fish.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Fish> retrieveFishes(){
        return fishRepository.findAll();
    }

}
