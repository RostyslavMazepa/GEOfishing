package com.geofishing.repository;

import com.geofishing.model.FishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FishTypeRepository extends JpaRepository<FishType, Integer> {
}
