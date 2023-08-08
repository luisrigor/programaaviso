package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.rg.dealer.Dealer;
import java.util.List;

public interface OtherFlowService {
    List<ContactReason> getContactReasons();
    List<Genre> getGenre();
    List<Kilometers> getKilometers();
    List<EntityType> getEntityType();
    List<Age> getAge();
    List<Fidelitys> getFidelitys();
    List<Modelo> getModels(UserPrincipal userPrincipal);
    List<Fuel> getFuels(UserPrincipal userPrincipal);
    void getDocumentUnits(UserPrincipal userPrincipal, int type);
    List<DocumentUnitDTO> searchDocumentUnit(Integer type, UserPrincipal userPrincipal);
    List<Dealer> getDealers(UserPrincipal userPrincipal);
}
