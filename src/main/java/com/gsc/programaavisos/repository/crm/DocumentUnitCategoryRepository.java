package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.DocumentUnitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentUnitCategoryRepository extends JpaRepository<DocumentUnitCategory, Integer> {

    @Query("SELECT D FROM DocumentUnitCategory D WHERE D.idDocumentUnitType = :type")
    List<DocumentUnitCategory> getByType(@Param("type") Integer type);
}
