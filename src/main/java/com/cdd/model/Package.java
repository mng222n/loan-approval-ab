package com.cdd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Package extends BaseEntity {
    private String name;
    @Type(type = "text")
    private String description;
    private int kytCost;
    private int kycCost;
    private long credits;
    @OneToMany(mappedBy = "parentPackage",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CreditBundle> creditBundleList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKytCost() {
        return kytCost;
    }

    public void setKytCost(int kytCost) {
        this.kytCost = kytCost;
    }

    public int getKycCost() {
        return kycCost;
    }

    public void setKycCost(int kycCost) {
        this.kycCost = kycCost;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public List<CreditBundle> getCreditBundleList() {
        return creditBundleList;
    }

    public void setCreditBundleList(List<CreditBundle> creditBundleList) {
        this.creditBundleList = creditBundleList;
    }

}
