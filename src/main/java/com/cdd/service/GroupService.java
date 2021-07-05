package com.cdd.service;

import com.cdd.dto.PageResult;
import com.cdd.model.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.UUID;

@Component
public interface GroupService {

    public PageResult<Group> getGroupPageable(Pageable pageable);

    public List<Group> getAllGroups() ;

    public Group saveGroup(Group group) ;

    public Group updateGroup(UUID id, Group group) ;

    public void deleteGroup(UUID id) ;

    public Group getGroupById(UUID id) ;
}