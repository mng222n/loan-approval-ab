package com.cdd.service.impl;

import com.cdd.dao.CreditBundleDAO;
import com.cdd.dao.PackageDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.Package;
import com.cdd.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("packageServiceImpl")
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDAO packageDAO;
    @Autowired
    private CreditBundleDAO creditBundleDAO;

    @Transactional
    public Package savePackage(Package aPackage) {
        return packageDAO.save(aPackage);
    }

    @Transactional
    public PageResult<Package> getPackagePageable(Pageable pageable) {
        Page<Package> page = packageDAO.findAll(pageable);
        return new PageResult<Package>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<Package> getAllPackages() {
        return packageDAO.findAll();
    }

    @Transactional
    public Package updatePackage(UUID id, Package aPackage) {
        Package package1 = packageDAO.findById(id).orElse(null);
        if (null == package1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid package id");
        }
        package1.setName(aPackage.getName());
        package1.setCredits(aPackage.getCredits());
        package1.setDescription(aPackage.getDescription());
        package1.setCreditBundleList(aPackage.getCreditBundleList());
        package1.setKycCost(aPackage.getKycCost());
        package1.setKytCost(aPackage.getKytCost());
        return packageDAO.save(package1);
    }

    @Transactional
    public void deletePackage(UUID id) {
        packageDAO.deleteById(id);
    }

    @Transactional
    public Package getPackageById(UUID id) {
        return packageDAO.getOne(id);
    }
}