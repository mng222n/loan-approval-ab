package com.cdd.service.impl;

import com.cdd.constants.CreditPurchaseStatus;
import com.cdd.dao.CreditBundleDAO;
import com.cdd.dao.CreditPurchaseDAO;
import com.cdd.dao.CustomerDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.CreditBundle;
import com.cdd.model.CreditPurchase;
import com.cdd.model.Customer;
import com.cdd.service.CreditPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Transactional
public class CreditPurchaseServiceImpl implements CreditPurchaseService {
    @Autowired
    CreditPurchaseDAO creditPurchaseDAO;

    @Autowired
    CreditBundleDAO creditBundleDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public PageResult<CreditPurchase> getCreditPurchasePageable(Pageable pageable) {
        Page page = creditPurchaseDAO.findAll(pageable);

        return new PageResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public CreditPurchase purchaseBundle(Customer buyer, UUID bundleId) {
        CreditBundle bundle = creditBundleDAO.findById(bundleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bundle id"));

        CreditPurchase creditPurchase = new CreditPurchase();
        creditPurchase.setStatus(CreditPurchaseStatus.PENDING);
        creditPurchase.setBuyer(buyer);
        creditPurchase.setBundle(bundle);
        creditPurchaseDAO.save(creditPurchase);
        return creditPurchase;
    }

    @Override
    public CreditPurchase activateBundle(UUID purchaseId) {
        CreditPurchase creditPurchase = creditPurchaseDAO.findById(purchaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bundle id"));
        if (creditPurchase.getStatus().equals(CreditPurchaseStatus.ACTIVATED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bundle was already activated");
        }

        creditPurchase.setActivateDate(new Timestamp(System.currentTimeMillis()));
        creditPurchase.setStatus(CreditPurchaseStatus.ACTIVATED);
        creditPurchaseDAO.save(creditPurchase);
        // add customer credit
        customerDAO.addCredit(creditPurchase.getBuyer().getId(), creditPurchase.getBundle().getCreditAmount());

        return creditPurchase;
    }

    @Override
    public void deletePurchase(UUID purchaseId) {
        creditPurchaseDAO.deleteById(purchaseId);
    }
}
