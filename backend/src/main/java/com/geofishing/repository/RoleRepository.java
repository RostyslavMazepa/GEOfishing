package com.geofishing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.geofishing.model.Role;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);


}
