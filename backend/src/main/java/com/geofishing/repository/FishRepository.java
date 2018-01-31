package com.geofishing.repository;

import com.geofishing.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish,Long> {
}
