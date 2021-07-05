package com.cdd.dao;

import com.cdd.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, UUID> {
    @Modifying
    @Query("UPDATE Customer c SET c.creditBalance = c.creditBalance + :amount WHERE c.id = :customerId")
    void addCredit(UUID customerId, long amount);

    @Query("SELECT c, o FROM Customer c JOIN OAuthClientDetails o ON o.customer = c WHERE o.clientId = :clientId")
    Customer getCustomerByClientId(String clientId);
}