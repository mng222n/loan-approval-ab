package com.cdd.service.impl;

import com.cdd.constants.OperationType;
import com.cdd.dao.CustomerDAO;
import com.cdd.dao.GroupDAO;
import com.cdd.dao.OAuthClientDetailsDAO;
import com.cdd.dao.PackageDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.Customer;
import com.cdd.model.Group;
import com.cdd.model.OAuthClientDetails;
import com.cdd.model.Package;
import com.cdd.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;

@Service("customerServiceImpl")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private OAuthClientDetailsDAO oAuthClientDetailsDAO;
    @Autowired
    private PackageDAO packageDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public PageResult<Customer> getCustomerPageable(Pageable pageable) {
        Page<Customer> page = customerDAO.findAll(pageable);
        return new PageResult<Customer>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = (List<Customer>) customerDAO.findAll();
        return customerList;
    }

    @Transactional
    public Customer saveCustomer(Customer customer, UUID groupId, UUID packageId) {
        Group group = groupDAO.findById(groupId).orElse(null);
        if (null == group) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid group id!");
        }
        customer.setGroup(group);
        Package aPackage = packageDAO.findById(packageId).orElse(null);
        if (null == aPackage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid package id!");
        }
        customer.setUsedPackage(aPackage);
        customer.setCreditBalance(aPackage.getCredits()); // set credit balance according to chosen package
        Customer res = customerDAO.save(customer);
        OAuthClientDetails oAuthClientDetails = new OAuthClientDetails();
        oAuthClientDetails.setCustomer(res);
        oAuthClientDetails.setClientId(UUID.randomUUID().toString());
        oAuthClientDetails.setClientSecret(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
        oAuthClientDetailsDAO.save(oAuthClientDetails);
        res.setClientDetails(oAuthClientDetails);
        return res;
    }

    @Transactional
    public Customer updateCustomer(UUID id, Customer customer, UUID groupId, UUID packageId) {
        Customer customer1 = customerDAO.findById(id).orElse(null);
        if (null == customer1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id");
        }
        Group group = groupDAO.findById(groupId).orElse(null);
        if (null == group) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid group id");
        }
        Package aPackage = packageDAO.findById(packageId).orElse(null);
        if (null == aPackage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid package id");
        }
        customer1.setGroup(group);
//        customer1.setUsedPackage(aPackage);
        customer1.setActivePackage(customer.isActivePackage());
        customer1.setCompany(customer.getCompany());
        customer1.setCity(customer.getCity());
        customer1.setState(customer.getState());
        customer1.setZipCode(customer.getZipCode());
        customer1.setCountry(customer.getCountry());
        customer1.setPhone(customer.getPhone());
        customer1.setWebsite(customer.getWebsite());
        customer1.setCurrency(customer.getCurrency());
        customer1.setCustomerAdmin(customer.getCustomerAdmin());
        customer1.setApiKey(customer.getApiKey());
        customer1.setAddress(customer.getAddress());
//        customer1.setContactList(customer.getContactList());
//        customer1.setCreditBalance(customer.getCreditBalance());
//        customer1.setClientDetails(customer.getClientDetails());
        if (!aPackage.getId().equals(customer.getUsedPackage().getId())) {
            // if package is changed, then add credit to this customer
            customerDAO.addCredit(customer.getId(), aPackage.getCredits());
        }
        Customer res = customerDAO.save(customer1);
        return res;

    }

    @Transactional
    public void deleteCustomer(UUID id) {
        customerDAO.deleteById(id);
    }

    @Transactional
    public Customer getCustomerById(UUID id) {
        Customer customer = customerDAO.getOne(id);
        return customer;
    }

    @Override
    public boolean deductCustomerCredit(UUID customerId, OperationType type) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null || customer.getCreditBalance() <= 0 || customer.getUsedPackage() == null || customer.isActivePackage() == false) {
            // wrong customer id
            // out of credit
            // not selected a package
            // package is deactivated
            return false;
        }
        Package activePackage = customer.getUsedPackage();
        long cost = type.equals(OperationType.KYT) ? activePackage.getKytCost() : activePackage.getKycCost();
        if (cost > customer.getCreditBalance()) {
            // insufficient credit
            return false;
        }

        em.getTransaction().begin();
        // deduct credit base on active package and operation
        em.createQuery("UPDATE Customer c SET c.creditBalance = c.creditBalance - :cost WHERE c.id = :id")
                .setParameter("cost", cost)
                .setParameter("id", customerId)
                .executeUpdate();
        em.refresh(customer);
        if (customer.getCreditBalance() < 0) {
            // rollback credit deduction and return false when concurrency happens to over deduct credit
            em.getTransaction().rollback();
            em.close();
            return false;
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public Customer getCustomerByClientId(String clientId) {
        return customerDAO.getCustomerByClientId(clientId);
    }

}