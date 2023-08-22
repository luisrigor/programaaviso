package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ParametrizationItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ParametrizationItemsRepository extends JpaRepository<ParametrizationItems,Integer> {

    @Query( "SELECT P FROM ParametrizationItems P WHERE P.idParameterization = :idParameterization AND P.status = 'S' ORDER BY P.idContactReason ASC")
    List<ParametrizationItems> getAllParametrizationItemOnlyActive(@Param("idParameterization") Integer idParameterization);

    @Query( "SELECT P FROM ParametrizationItems P WHERE P.idParameterization = :idParameterization ORDER BY P.idContactReason ASC")
    List<ParametrizationItems> getAllParametrizationItem(@Param("idParameterization") Integer idParameterization);
/*
    @Transactional
    @Query("INSERT INTO ParametrizationItems (idParameterization, idHighlight1, idHighlight2, idHighlight3, " +
            "idService1, idService2, idService3, idHeader1, idHeader2, idHeader3, " +
            "parameterizationItemsType, idContactReason, status, createdBy, dtCreated, " +
            "changedBy, dtChanged) " +
            "VALUES (:idParameterization, :idHighlight1, :idHighlight2, :idHighlight3, " +
            ":idService1, :idService2, :idService3, :idHeader1, :idHeader2, :idHeader3, " +
            ":parameterizationItemsType, :idContactReason, :status, :createdBy, :dtCreated, " +
            ":changedBy, :dtChanged)")
    void insertCloneParametrizationItem(ParametrizationItems parametrizationItems);

 */

}