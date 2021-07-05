package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.Package;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface PackageService {

    public Package savePackage(Package aPackage);

    public PageResult<Package> getPackagePageable(Pageable pageable);

    public List<Package> getAllPackages();

    public Package updatePackage(UUID id, Package aPackage);

    public void deletePackage(UUID id);

    public Package getPackageById(UUID id);

    }