package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaParameterizationRepository extends JpaRepository<PaParameterization, Integer>, PaParameterizationCustomRepository {


}
