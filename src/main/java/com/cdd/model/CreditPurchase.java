package com.cdd.model;

import com.cdd.constants.CreditPurchaseStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class CreditPurchase extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer buyer;

    @ManyToOne
    @JoinColumn(name = "bundle_id")
    private CreditBundle bundle;

    private Timestamp activateDate;

    @Enumerated(value = EnumType.STRING)
    private CreditPurchaseStatus status;

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public CreditBundle getBundle() {
        return bundle;
    }

    public void setBundle(CreditBundle bundle) {
        this.bundle = bundle;
    }

    public Timestamp getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(Timestamp activateDate) {
        this.activateDate = activateDate;
    }

    public CreditPurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(CreditPurchaseStatus status) {
        this.status = status;
    }
}
