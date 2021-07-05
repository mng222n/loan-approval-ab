package com.cdd.controller;


import com.cdd.constants.RoleEnum;
import com.cdd.dto.PageResult;

import com.cdd.model.Customer;
import com.cdd.model.User;

import java.util.List;
import java.util.UUID;

import com.cdd.service.UserService;

import com.cdd.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageResult<User> getUserPageable(Pageable pageable) {
        return userService.getUserPageable(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user, @RequestParam(name = "customerId", required = false) UUID customerId) {
        return userService.saveUser(user, customerId);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public User getByIdUser(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/cp/")
    @Secured("ROLE_SERVICE")
    public PageResult<User> getClientPortalUsers(Pageable pageable) {
        Customer customer = RequestUtils.getRequestCustomer();
        return userService.getUserByCustomer(customer, pageable);
    }

    @PostMapping("/cp/save")
    @Secured("ROLE_SERVICE")
    public User createClientPortalUser(@RequestBody User user) {
        Customer customer = RequestUtils.getRequestCustomer();
        user.setRole(RoleEnum.USER);
        user.setCustomer(customer);
        return userService.saveUser(user, customer.getId());
    }

    @PutMapping("/cp/update/{id}")
    @Secured("ROLE_SERVICE")
    public User updateClientPortalUser(@RequestBody User user, @PathVariable("id") UUID id) {
        // TODO: limit this endpoint by specific customer only
//        Customer customer = RequestUtils.getRequestCustomer();
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/cp/delete/{id}")
    @Secured("ROLE_SERVICE")
    public void deleteClientPortalUser(@PathVariable("id") UUID id) {
        // TODO: allow delete user from specific customer only
        userService.deleteUser(id);
    }
}