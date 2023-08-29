package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ParametrizationItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParametrizationItemsRepository extends JpaRepository<ParametrizationItems,Integer> {

    @Query( "SELECT P FROM ParametrizationItems P WHERE P.idParameterization = :idParameterization AND P.status = 'S' ORDER BY P.idContactReason ASC")
    List<ParametrizationItems> getAllParametrizationItemOnlyActive(@Param("idParameterization") Integer idParameterization);

    @Query( "SELECT P FROM ParametrizationItems P WHERE P.idParameterization = :idParameterization ORDER BY P.idContactReason ASC")
    List<ParametrizationItems> getAllParametrizationItem(@Param("idParameterization") Integer idParameterization);

}