package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ContactService {

    public PageResult<Contact> getContactPageable(Pageable pageable, UUID customerId);

    public List<Contact> getAllContacts(UUID customerId);

    public Contact saveContact(Contact contact, UUID customerId);

    public Contact updateContact(UUID id, Contact contact) ;

    public void deleteContact(UUID id) ;

    public Contact getContactById(UUID id) ;

}
