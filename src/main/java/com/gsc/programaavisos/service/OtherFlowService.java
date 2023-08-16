package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.*;
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
    List<DocumentUnitDTO> searchDocumentUnit(Integer type, UserPrincipal userPrincipal);
    List<Dealer> getDealers(UserPrincipal userPrincipal);
    DelegatorsDTO getDelegators(UserPrincipal userPrincipal, GetDelegatorsDTO delegatorsDTO);
    List<Object[]> getChangedList(GetDelegatorsDTO delegatorsDTO);
    List<ContactType> getContactTypeList(String userLogin);
    ClientContactsDTO getPAClientContacts(String nif, String selPlate, int idPaData, FilterBean oPAFilterBean);
    void mapUpdate(UserPrincipal userPrincipal);
    List<ClientType> getClientTypes();
    List<Channel> getChannels();
    List<Source> getSources();
    List<ContactType> getAllContactTypes();
}
