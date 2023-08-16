package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source,Integer> {

    @Query("SELECT S FROM Source S WHERE UPPER(S.status) = :status ORDER BY S.name")
    List<Source> getByStatus(@Param("status") Character status);
}
