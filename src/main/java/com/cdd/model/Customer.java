package com.cdd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer extends BaseEntity {
    private String company;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private String website;
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;
    private String currency;
    private String customerAdmin;
    private String apiKey;
    @Column(columnDefinition = "int8 default 0")
    private long creditBalance;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private OAuthClientDetails clientDetails;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Contact> contactList;
    @ManyToOne
    @JoinColumn(name="package_id")
    private Package usedPackage;
    private Boolean isActivePackage = true; // active by default

    public Boolean isActivePackage() {
        // if isActivePackage is not set, default to true
        return !Boolean.FALSE.equals(isActivePackage);
    }

    public void setActivePackage(Boolean activePackage) {
        isActivePackage = activePackage;
    }

    public Package getUsedPackage() {
        return usedPackage;
    }

    public void setUsedPackage(Package aPackage) {
        this.usedPackage = aPackage;
    }

    public long getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(long creditBalance) {
        this.creditBalance = creditBalance;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public OAuthClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(OAuthClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }   
    
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }   
    
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerAdmin() {
        return customerAdmin;
    }

    public void setCustomerAdmin(String customerAdmin) {
        this.customerAdmin = customerAdmin;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
}