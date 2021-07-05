package com.cdd.controller;

import java.util.HashMap;
import java.util.List;

import com.cdd.constants.OperationType;
import com.cdd.dto.PageResult;
import com.cdd.service.CustomerService;
import com.cdd.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdd.model.Customer;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageResult<Customer> getCustomerPageable(Pageable pageable) {
        return customerService.getCustomerPageable(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Customer saveCustomer(@RequestBody Customer customer, @RequestParam("groupId") UUID groupId,
                                 @RequestParam("packageId") UUID packageId) {
        return customerService.saveCustomer(customer, groupId, packageId);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer, @RequestParam("groupId") UUID groupId,
                                   @RequestParam("packageId") UUID packageId) {
        return customerService.updateCustomer(id, customer, groupId, packageId);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    /**
     * This endpoint is called by CP server to check if a {@link Customer} has active package or sufficient credit to perform an KYT/KYC transaction
     * Credit is deducted once this endpoint is successfully called, after that CP can perform requested action
     *
     * @param type
     * @return
     */
    @PostMapping("/cp/deduct-credit")
    @Secured("ROLE_SERVICE")
    public ResponseEntity deductCredit(@RequestParam("type") OperationType type) {
        Customer customer = RequestUtils.getRequestCustomer();
        if (customerService.deductCustomerCredit(customer.getId(), type)) {
            // successfully deduct credit, return 200
            return ResponseEntity.ok().build();
        } // else, return 400
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/cp/api-key")
    @Secured("ROLE_SERVICE")
    public Map getApiKey() {
        Customer customer = RequestUtils.getRequestCustomer();

        Map<String, String> result = new HashMap<>();
        result.put("kytApiKey", customer.getApiKey());

        return result;
    }
}
