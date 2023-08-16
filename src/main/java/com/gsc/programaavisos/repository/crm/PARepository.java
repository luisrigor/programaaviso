package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PARepository extends JpaRepository<ProgramaAvisos, Integer>, PACustomRepository {
}
