package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.model.crm.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactTypeRepository extends JpaRepository<ContactType,Integer> {

}
