package com.cdd.service.impl;

import com.cdd.dao.ContactDAO;
import com.cdd.dao.CustomerDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.Contact;
import com.cdd.model.Customer;

import com.cdd.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CustomerDAO customerDAO;

    @Transactional
    public PageResult<Contact> getContactPageable(Pageable pageable, UUID customerId) {
        Customer customer = customerDAO.findById(customerId).orElse(null);
        if (null == customer) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id");
        }
        Page<Contact> page = contactDAO.findContactPageableByCustomer(customerId, pageable);
        return new PageResult<Contact>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<Contact> getAllContacts(UUID customerId) {
        List<Contact> contactList = (List<Contact>) contactDAO.findContactByCustomer(customerId);
        return contactList;
    }

    @Transactional
    public Contact saveContact(Contact contact, UUID customerId) {
        Customer customer = customerDAO.findById(customerId).orElse(null);
        if (null == customer) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id");
        }
        contact.setCustomer(customer);
        Contact res = contactDAO.save(contact);
        return res;
    }

    @Transactional
    public Contact updateContact(UUID id, Contact contact) {

        Contact contact1 = contactDAO.findById(id).orElse(null);
        if (null == contact1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid contact Id");
        }
        contact1.setName(contact.getName());
        contact1.setPosition(contact.getPosition());
        contact1.setEmail(contact.getEmail());
        contact1.setPhone(contact.getPhone());
        contact1.setIsAdmin(contact.getIsAdmin());
        Contact res = contactDAO.save(contact1);
        return res;

    }

    @Transactional
    public void deleteContact(UUID id) {
        contactDAO.deleteById(id);
    }

    @Transactional
    public Contact getContactById(UUID id) {
        Contact contact = contactDAO.getOne(id);
        return contact;
    }

}
