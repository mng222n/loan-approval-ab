package com.cdd.service.impl;

import com.cdd.dao.GroupDAO;
import com.cdd.dto.PageResult;
import com.cdd.model.Group;
import com.cdd.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("groupServiceImpl")
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDAO groupDAO;

    @Transactional
    public PageResult<Group> getGroupPageable(Pageable pageable) {
        Page<Group> page = groupDAO.findAll(pageable);
        return new PageResult<Group>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public List<Group> getAllGroups() {
        List<Group> groupList = (List<Group>) groupDAO.findAll();
        return groupList;
    }

    @Transactional
    public Group saveGroup(Group group) {
        Group res = groupDAO.save(group);
        return res;
    }

    @Transactional
    public Group updateGroup(UUID id, Group group) {
        Group group1 = groupDAO.findById(id).orElse(null);
        if(null == group1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid group id");
        }
        group1.setName(group.getName());
        Group res = groupDAO.save(group1);
        return res;
    }

    @Transactional
    public void deleteGroup(UUID id) {
        groupDAO.deleteById(id);
    }

    @Transactional
    public Group getGroupById(UUID id) {
        Group group = groupDAO.getOne(id);
        return group;
    }
}