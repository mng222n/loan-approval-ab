package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.CreditPurchase;
import com.cdd.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface CreditPurchaseService {
    PageResult<CreditPurchase> getCreditPurchasePageable(Pageable pageable);

    CreditPurchase purchaseBundle(Customer buyer, UUID bundleId);

    CreditPurchase activateBundle(UUID purchaseId);

    void deletePurchase(UUID purchaseId);
}
