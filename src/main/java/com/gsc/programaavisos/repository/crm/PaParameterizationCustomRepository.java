package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;

import java.util.List;

public interface PaParameterizationCustomRepository {
    List<PaParameterization> getByFilter(ParameterizationFilter parameterizationFilter);

}
