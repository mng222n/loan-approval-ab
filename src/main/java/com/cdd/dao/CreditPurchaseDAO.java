package com.cdd.dao;

import com.cdd.model.CreditPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditPurchaseDAO extends JpaRepository<CreditPurchase, UUID> {
}
