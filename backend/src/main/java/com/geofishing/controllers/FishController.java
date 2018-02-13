package com.geofishing.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.geofishing.model.Fish;
import com.geofishing.model.FishType;
import com.geofishing.repository.FishRepository;
import com.geofishing.repository.FishTypeRepository;
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
    @Autowired
    FishTypeRepository fishTypeRepository;

    @ApiOperation(value = "get fishes list", response = Fish.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @JsonView(Views.Dictionary.class)
    public List<Fish> retrieveFishes(){
        return fishRepository.findAll();
    }

    @ApiOperation(tags = {"fishes", "dictionary", "fish type"}, value = "get fish types list", response = FishType.class, responseContainer = "List")
    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FishType> retrieveFishTypes() {
        return fishTypeRepository.findAll();
    }

}
