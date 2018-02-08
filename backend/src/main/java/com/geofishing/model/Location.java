package com.geofishing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.geofishing.model.auth.User;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int locationId;

    @Column(columnDefinition = "geometry(Point,4326)",nullable = false)
    private Point point;


    private String name;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;

    @ManyToMany
    @JoinTable(name = "fish_locaction", joinColumns = @JoinColumn(name = "loc_id"),inverseJoinColumns = @JoinColumn(name = "fish_id"))
    @JsonBackReference
    private List<Fish> fishes;

    public Location() {
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Fish> getFishes() {
        return fishes;
    }

    public void setFishes(List<Fish> fishes) {
        this.fishes = fishes;
    }
}
