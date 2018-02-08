package com.geofishing.repository;

import com.geofishing.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FishRepository extends JpaRepository<Fish,Long> {
}
