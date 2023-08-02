package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.security.UserPrincipal;

import java.sql.Date;
import java.util.List;

public interface ProgramaAvisosService {
    List<PaParameterization> searchParametrizations(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal);
    List<DocumentUnit> searchDocumentUnit(Integer type, UserPrincipal userPrincipal);
    List<ContactReason> getContactReasons();
    List<Genre> getGenre();
    List<EntityType> getEntityType();
    List<Age> getAge();
    List<Modelo> getModels(UserPrincipal userPrincipal);
    void savePA(UserPrincipal userPrincipal, PADTO pa);
}
