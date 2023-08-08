package com.gsc.programaavisos.service;

import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.security.UserPrincipal;
import java.sql.Date;
import java.util.List;

public interface ParametrizationService {
    List<PaParameterization> searchParametrization(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal);
    void deleteParametrization(UserPrincipal userPrincipal,Integer id);
}
