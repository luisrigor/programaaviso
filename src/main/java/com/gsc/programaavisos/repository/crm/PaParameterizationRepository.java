package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaParameterizationRepository extends JpaRepository<PaParameterization, Integer>, PaParameterizationCustomRepository {

}
