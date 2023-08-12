package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClientTypeRepository extends JpaRepository<ClientType,Integer> {

    @Query("SELECT C FROM ClientType C WHERE UPPER(C.status) = :status ORDER BY C.name")
    List<ClientType> getByStatus(@Param("status") Character status);
}
