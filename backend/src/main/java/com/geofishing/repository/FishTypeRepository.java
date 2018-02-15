package com.geofishing.repository;

import com.geofishing.model.FishType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishTypeRepository extends JpaRepository<FishType, Integer> {
}
