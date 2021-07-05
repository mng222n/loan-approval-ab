package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.CreditBundle;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CreditBundleService {

    public CreditBundle saveCreditBundle(CreditBundle creditBundle, UUID packageId) ;

    public PageResult<CreditBundle> getCreditBundlePageable(Pageable pageable) ;

    public List<CreditBundle> getAllCreditBundles() ;

    public CreditBundle updateCreditBundle(UUID id, CreditBundle creditBundle, UUID packageId) ;

    public void deleteCreditBundle(UUID id) ;

    public CreditBundle getCreditBundleById(UUID id) ;

    public List<CreditBundle> getAllCreditBundlesByPackage();
}
