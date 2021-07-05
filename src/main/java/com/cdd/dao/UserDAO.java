package com.cdd.dao;

import com.cdd.model.Customer;
import com.cdd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN Customer c ON u.customer = c LEFT JOIN OAuthClientDetails o ON o.customer = c WHERE (u.email = :email AND o.clientId = :clientId) OR (u.email = :email AND c IS NULL)")
    User findByEmailAndClientIdOrNull(String email, String clientId);

    Page<User> findByCustomer(Customer customer, Pageable pageable);
}
