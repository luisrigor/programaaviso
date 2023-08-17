package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.DelegatorsDTO;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.GetDelegatorsDTO;
import com.gsc.programaavisos.dto.MaintenanceTypeDTO;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.rg.dealer.Dealer;
import java.util.List;
import java.util.Map;

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
    List<ClientType> getClientTypes();
    List<Channel> getChannels();
    List<Source> getSources();
    List<ContactType> getAllContactTypes();
    List<MaintenanceTypeDTO>  getMaintenanceTypesByContactType();
}
