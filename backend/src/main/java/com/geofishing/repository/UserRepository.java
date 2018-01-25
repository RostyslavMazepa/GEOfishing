package com.geofishing.repository;

import com.geofishing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
