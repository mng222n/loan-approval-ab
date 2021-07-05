package com.cdd.controller;

import java.util.List;
import java.util.UUID;

import com.cdd.dto.PageResult;

import com.cdd.model.Contact;
import com.cdd.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageResult<Contact> getContactPageable(Pageable pageable, @RequestParam("customerId")  UUID customerId) {
        return contactService.getContactPageable(pageable, customerId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Contact> getAllContacts(@RequestParam("customerId") UUID customerId) {
        return contactService.getAllContacts(customerId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Contact saveContact(@RequestBody Contact contact, @RequestParam UUID customerId) {
        return contactService.saveContact(contact,customerId);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Contact updateContact(@PathVariable("id") UUID id, @RequestBody Contact contact) {
        return contactService.updateContact(id, contact);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteContact(@PathVariable("id") UUID id) {
		contactService.deleteContact(id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Contact getContactById(@PathVariable("id") UUID id) {
        return contactService.getContactById(id);
    }

}
