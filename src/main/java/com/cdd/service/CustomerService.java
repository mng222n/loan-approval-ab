package com.cdd.service;

import com.cdd.constants.OperationType;
import com.cdd.dto.PageResult;
import com.cdd.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CustomerService {

    public PageResult<Customer> getCustomerPageable(Pageable pageable) ;

    public List<Customer> getAllCustomers() ;

    public Customer saveCustomer(Customer customer, UUID groupId, UUID packageId) ;

    public Customer updateCustomer(UUID id, Customer customer, UUID groupId, UUID packageId) ;

    public void deleteCustomer(UUID id) ;

    public Customer getCustomerById(UUID id) ;

    boolean deductCustomerCredit(UUID customerId, OperationType type);

    Customer getCustomerByClientId(String clientId);
}