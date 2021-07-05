package com.cdd.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.model.Group;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupDAO extends JpaRepository<Group, UUID> {

}