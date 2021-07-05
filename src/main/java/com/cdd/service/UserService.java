package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.Customer;
import com.cdd.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface UserService {

    public PageResult<User> getUserPageable(Pageable pageable) ;

    public List<User> getAllUsers() ;

    public User saveUser(User user, UUID customerId) ;

    public User updateUser(UUID id, User user) ;

    public void deleteUser(UUID id) ;

    public User getUserById(UUID id) ;

    PageResult<User> getUserByCustomer(Customer customer, Pageable pageable);

}
