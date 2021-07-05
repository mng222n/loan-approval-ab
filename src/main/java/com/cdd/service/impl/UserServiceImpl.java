package com.cdd.service.impl;

import com.cdd.dao.CustomerDAO;
import com.cdd.dao.UserDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.Customer;
import com.cdd.model.User;
import com.cdd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Transactional
    public PageResult<User> getUserPageable(Pageable pageable) {
        Page<User> page = userDAO.findAll(pageable);
        return new PageResult<User>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Transactional
    public User saveUser(User user, UUID customerId) {
        if (customerId != null) {
            Customer customer = customerDAO.findById(customerId).orElse(null);
            if (null == customer) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id!");
            }
            user.setCustomer(customer);
        }
        User res = userDAO.save(user);
        return res;
    }

    @Transactional
    public User updateUser(UUID id, User user) {
        User user1 = userDAO.findById(id).orElse(null);
        if (null == user1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setAddress(user.getAddress());
        User res = userDAO.save(user1);
        return res;
    }

    @Transactional
    public void deleteUser(UUID id) {
        userDAO.deleteById(id);
    }

    @Transactional
    public User getUserById(UUID id) {
        User user = userDAO.getOne(id);
        return user;
    }

    @Override
    public PageResult<User> getUserByCustomer(Customer customer, Pageable pageable) {
        Page page = userDAO.findByCustomer(customer, pageable);

        return new PageResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

}
