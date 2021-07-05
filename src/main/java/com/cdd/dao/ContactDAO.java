package com.cdd.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.cdd.model.Contact;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactDAO extends JpaRepository<Contact, UUID> {
    @Query(value = "SELECT * FROM contact c WHERE c.customer_id = :customerId", nativeQuery = true)
    List<Contact> findContactByCustomer(@Param("customerId") UUID customerId);

    @Query(value = "SELECT * FROM contact c WHERE c.customer_id = :customerId", nativeQuery = true)
    Page<Contact> findContactPageableByCustomer(@Param("customerId") UUID customerId, Pageable pageable);
}
