package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.CcRigorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CcRigorServiceRepository extends JpaRepository<CcRigorService, Long> {

}
