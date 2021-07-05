package com.cdd.controller;

import com.cdd.dao.CustomerDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.CreditPurchase;
import com.cdd.model.Customer;
import com.cdd.service.CreditPurchaseService;
import com.cdd.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/purchase/credit")
public class CreditPurchaseController {
    @Autowired
    CreditPurchaseService creditPurchaseService;

    @Autowired
    CustomerDAO customerDAO;

    @GetMapping("/")
    public PageResult<CreditPurchase> getCreditPurchases(Pageable pageable) {
        return creditPurchaseService.getCreditPurchasePageable(pageable);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePurchase(@PathVariable("id") UUID purchaseId) {
        creditPurchaseService.deletePurchase(purchaseId);
    }

    @PostMapping("/add/{bundleId}")
    @Secured({"ROLE_SERVICE", "ROLE_STAFF"})
    public CreditPurchase purchaseCredit(@PathVariable("bundleId") UUID bundleId, @RequestParam(name = "customerId", required = false) UUID customerId) {
        Customer customer = RequestUtils.getRequestCustomer();
        if (customer == null && customerId != null) {
            customer = customerDAO.findById(customerId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer Id"));
        }
        return creditPurchaseService.purchaseBundle(customer, bundleId);
    }

    @PostMapping("/activate/{purchaseId}")
//    @Secured({"ROLE_STAFF", "ROLE_SUPERADMIN"})
    public CreditPurchase activePackage(@PathVariable("purchaseId") UUID purchaseId) {
        return creditPurchaseService.activateBundle(purchaseId);
    }
}
