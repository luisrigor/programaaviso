package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PABeanRepository extends JpaRepository<ProgramaAvisosBean,Integer>, PABeanCustomRepository {
}
