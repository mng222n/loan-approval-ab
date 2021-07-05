package com.cdd.dao;

import com.cdd.model.CreditBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditBundleDAO extends JpaRepository<CreditBundle, UUID> {
    @Query(value = "SELECT * from credit_bundle c WHERE c.package_id = :packageId", nativeQuery = true)
    List<CreditBundle> findCreditBundleByPackage(@Param("packageId") UUID packageId);
}