package com.cdd.controller;

import java.util.List;
import java.util.UUID;

import com.cdd.dto.PageResult;

import com.cdd.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdd.model.Group;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageResult<Group> getGroupPageable(Pageable pageable) {
        return groupService.getGroupPageable(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Group saveGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Group updateGroup(@PathVariable("id") UUID id, @RequestBody Group group) {
        return groupService.updateGroup(id, group);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteGroup(@PathVariable("id") UUID id) {
    	groupService.deleteGroup(id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Group getGroupById(@PathVariable("id") UUID id) {
        return groupService.getGroupById(id);
    }

}
