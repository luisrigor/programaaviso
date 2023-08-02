package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.PaParameterizationCustomRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PaParameterizationCustomRepositoryImpl implements PaParameterizationCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PaParameterization> getByFilter(ParameterizationFilter parameterizationFilter) {

        StringBuilder SQL = new StringBuilder();


        SQL.append(" SELECT * FROM PA_PARAMETERIZATION ");
        SQL.append(" "+parameterizationFilter.whereClause());
        SQL.append(" ORDER BY DT_START, DT_CREATED DESC");

        Query query = em.createNativeQuery(SQL.toString(), PaParameterization.class);
        parameterizationFilter.prepareStatement(query, 1);

        List<PaParameterization> parameterizations = query.getResultList();

        return parameterizations;
    }
}
