package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {

    @Query("SELECT C FROM ContactType C WHERE UPPER(C.status) = :status ORDER BY C.name")
    List<ContactType> getByState(@Param("status") Character status);
}
