package com.gsc.programaavisos.service;

import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.ContactReason;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.security.UserPrincipal;

import java.sql.Date;
import java.util.List;

public interface ProgramaAvisosService {

    List<PaParameterization> searchParametrizations(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal);
    List<ContactReason> getContactReasons();
    List<Modelo> getModels(UserPrincipal userPrincipal);
    List<Fuel> getFuels(UserPrincipal userPrincipal);
    void getDocumentUnits(UserPrincipal userPrincipal, int type);
}
