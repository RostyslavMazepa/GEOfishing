package com.geofishing.repository;

import com.geofishing.model.auth.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u left join fetch u.roles where u.username = :username")
    User findByUsernameWithRoles(@Param("username") String username);

    User findByFacebookAccount_UserId(Long userId);

    User findByFacebookAccount_UserIdOrEmail(String userId, String email);

    User findByGoogleAccount_UserIdOrEmail(String userId, String email);

    User findByEmail(String email);

    @EntityGraph(value = "fullInfo", type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"facebookAccount", "roles"})
    User getUserByUsername(String username);
}