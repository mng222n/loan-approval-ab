package com.cdd.dao;

import com.cdd.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PackageDAO extends JpaRepository<Package, UUID> {
}
