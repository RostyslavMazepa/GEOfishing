package com.geofishing.repository;

import com.geofishing.model.social.FacebookAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacebookAccountRepository extends JpaRepository<FacebookAccount, Long> {
}
