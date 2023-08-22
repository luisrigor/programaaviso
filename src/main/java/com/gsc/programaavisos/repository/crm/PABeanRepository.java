package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PABeanRepository extends JpaRepository<ProgramaAvisosBean,Integer>, PABeanCustomRepository {

}
