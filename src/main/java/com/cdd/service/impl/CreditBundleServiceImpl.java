package com.cdd.service.impl;

import com.cdd.dao.CreditBundleDAO;
import com.cdd.dao.PackageDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.CreditBundle;
import com.cdd.model.Customer;
import com.cdd.model.Package;
import com.cdd.service.CreditBundleService;

import com.cdd.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("creditBundleServiceImpl")
public class CreditBundleServiceImpl implements CreditBundleService {
    @Autowired
    private CreditBundleDAO creditBundleDAO;
    @Autowired
    private PackageDAO packageDAO;

    @Transactional
    public CreditBundle saveCreditBundle(CreditBundle creditBundle, UUID packageId) {
        Package aPackage = packageDAO.findById(packageId).orElse(null);
        if (null == aPackage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid package id");
        }
        creditBundle.setParentPackage(aPackage);
        return creditBundleDAO.save(creditBundle);
    }

    @Transactional
    public PageResult<CreditBundle> getCreditBundlePageable(Pageable pageable) {
        Page<CreditBundle> page = creditBundleDAO.findAll(pageable);
        return new PageResult<CreditBundle>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<CreditBundle> getAllCreditBundles() {
        return creditBundleDAO.findAll();
    }

    @Transactional
    public CreditBundle updateCreditBundle(UUID id, CreditBundle creditBundle, UUID packageId) {
        CreditBundle creditBundle1 = creditBundleDAO.findById(id).orElse(null);
        if (null == creditBundle1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid creditbundle id");
        }
        Package aPackage = packageDAO.findById(packageId).orElse(null);
        if (null == aPackage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid package id");
        }
        creditBundle1.setCreditAmount(creditBundle.getCreditAmount());
        creditBundle1.setCurrency(creditBundle.getCurrency());
        creditBundle1.setParentPackage(aPackage);
        creditBundle1.setPrice(creditBundle.getPrice());

        return creditBundleDAO.save(creditBundle1);
    }

    @Transactional
    public void deleteCreditBundle(UUID id) {
        creditBundleDAO.deleteById(id);
    }

    @Transactional
    public CreditBundle getCreditBundleById(UUID id) {
        return creditBundleDAO.getOne(id);
    }

    @Transactional
    public List<CreditBundle> getAllCreditBundlesByPackage() {
        Customer customer = RequestUtils.getRequestCustomer();
        if (null == customer)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized request !");
        Package aPackage = customer.getUsedPackage();
        if (null == aPackage)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot get package !");
        return creditBundleDAO.findCreditBundleByPackage(aPackage.getId());
    }
}