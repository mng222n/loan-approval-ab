package com.cdd.dao;

import com.cdd.model.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OAuthClientDetailsDAO extends JpaRepository<OAuthClientDetails, UUID> {
    OAuthClientDetails findByClientId(String clientId);
}
