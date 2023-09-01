package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.security.UserPrincipal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface ParametrizationService {
    List<PaParameterization> searchParametrization(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal);
    void deleteParametrization(UserPrincipal userPrincipal,Integer id);
    List<PaParameterization> getParametrizationsList(UserPrincipal userPrincipal);
    PaParameterization getNewParametrization(UserPrincipal userPrincipal,Integer id);
    void saveParameterization(UserPrincipal userPrincipal, ParameterizationDTO parameterizationDTO);
    void cloneParameterization(UserPrincipal oGSCUser, Integer id);

    HashMap<Integer,List<PaParameterization>> getByIdClient(ParameterizationFilter filter, boolean onlyActives);
}
