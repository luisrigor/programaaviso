package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Calls;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallsRepository extends JpaRepository<Calls,Integer> {

    List<Calls> findByIdPaData(Integer id);
}
