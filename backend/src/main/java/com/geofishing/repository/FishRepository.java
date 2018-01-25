package com.geofishing.repository;

import com.geofishing.model.Fish;
import com.geofishing.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish,Long> {
}
