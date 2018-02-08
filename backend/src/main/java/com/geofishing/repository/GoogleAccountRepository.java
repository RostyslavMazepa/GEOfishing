package com.geofishing.repository;

import com.geofishing.model.social.GoogleAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleAccountRepository extends JpaRepository<GoogleAccount, Long> {
}
