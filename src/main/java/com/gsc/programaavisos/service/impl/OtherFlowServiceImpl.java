package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.AppProfile;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.cardb.CombustivelRepository;
import com.gsc.programaavisos.repository.cardb.ModeloRepository;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.OtherFlowService;
import com.gsc.programaavisos.util.TPAInvokerSimulator;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.StringTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.*;

import static com.gsc.programaavisos.constants.AppProfile.*;
import static com.gsc.programaavisos.constants.AppProfile.ROLE_VIEW_CALL_CENTER_DEALERS;

@Service
@Log4j
@RequiredArgsConstructor
public class OtherFlowServiceImpl implements OtherFlowService {

    private final ContactReasonRepository contactReasonRepository;
    private final ModeloRepository modeloRepository;
    private final CombustivelRepository combustivelRepository;
    private final GenreRepository genreRepository;
    private final EntityTypeRepository entityTypeRepository;
    private final AgeRepository ageRepository;
    private final KilometersRepository kilometersRepository;
    private final FidelitysRepository fidelitysRepository;
    private final DocumentUnitRepository documentUnitRepository;
    private final PARepository paRepository;


    private static final String QUOTES = "\"";

    @Override
    public List<ContactReason> getContactReasons() {
        try {
            return contactReasonRepository.findAll();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching contact reasons ", e);
        }
    }

    @Override
    public List<Genre> getGenre() {
        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching genre ", e);
        }
    }

    @Override
    public List<Kilometers> getKilometers() {
        try {
            return kilometersRepository.getAllKilometers();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching kilometers", e);
        }
    }

    @Override
    public List<EntityType> getEntityType() {
        try {
            return entityTypeRepository.findAll();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching entity type ", e);
        }
    }

    @Override
    public List<Age> getAge() {
        try {
            return ageRepository.getAllAge();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching age", e);
        }
    }

    @Override
    public List<Fidelitys> getFidelitys() {
        try {
            return fidelitysRepository.findAll();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching fidelity", e);
        }
    }

    @Override
    public List<Modelo> getModels(UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            return modeloRepository.getModels(idBrand);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching models ", e);
        }
    }

    @Override
    public List<Fuel> getFuels(UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());

            List<Fuel> fuels = combustivelRepository.getFuelsByIdBrand(idBrand);

            boolean addNoInfo = true;
            for(Fuel fuel: fuels){
                if(fuel.getId() == TPAInvokerSimulator.CAR_DB_COMBUSTIVEL_SEM_INFO){
                    addNoInfo = false;
                    break;
                }
            }

            if(addNoInfo){
                fuels.add(new Fuel(TPAInvokerSimulator.CAR_DB_COMBUSTIVEL_SEM_INFO, "s/info", 0, null, null));
            }

            return fuels;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching fuels ", e);
        }
    }

    @Override
    public void getDocumentUnits(UserPrincipal userPrincipal, int type) {
//        try {
//            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
//
//            ItemFilter oItemFilter = ItemFilter.builder()
//                    .itemType(type)
//                    .dtEnd()
//                    .idBrand()
//                    .build();
//
//
//            oItemFilter.setItemType(type);
//            oItemFilter.setDtEnd(new Date(Calendar.getInstance().getTime().getTime()));
//            oItemFilter.setIdBrand(idBrand);
//
//            List<DocumentUnit> documentUnits = DocumentUnit.getHelper().getByFilter(oItemFilter);
//
//        } catch (SCErrorException e) {
//            throw new ProgramaAvisosException("Error fetching document units ", e);
//        }
    }

    @Override
    public List<DocumentUnitDTO> searchDocumentUnit(Integer type, UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            ItemFilter filter = ItemFilter.builder()
                    .itemType(type)
                    .dtEnd(new Date(Calendar.getInstance().getTime().getTime()))
                    .idBrand(idBrand)
                    .build();
            return  documentUnitRepository.getByFilter(filter);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching documentUnit ", e);
        }
    }

    @Override
    public List<Dealer> getDealers(UserPrincipal userPrincipal) {
        try {
            Set<AppProfile> roles = userPrincipal.getRoles();

            List<Dealer> vecDealers = new ArrayList<>();
            if (userPrincipal.getOidNet().equalsIgnoreCase(Dealer.OID_NET_TOYOTA)) {
                if (roles.contains(ROLE_VIEW_ALL_DEALERS)) {
                    vecDealers = Dealer.getToyotaHelper().GetAllActiveDealers();
                } else if (roles.contains(ROLE_VIEW_CA_DEALERS)) {
                    vecDealers = Dealer.getToyotaHelper().GetCADealers("S");
                } else if (roles.contains(ROLE_VIEW_CALL_CENTER_DEALERS)) {
                    vecDealers = Dealer.getToyotaHelper().GetCADealers("S");

                    Dealer dlr3 = Dealer.getToyotaHelper().getByObjectId("SC00290012");
                    if (dlr3!=null)vecDealers.add(dlr3);

                    Dealer dlr4 = Dealer.getToyotaHelper().getByObjectId("SC00200001");
                    if (dlr4!=null)vecDealers.add(dlr4);

                    Dealer dlr6 = Dealer.getToyotaHelper().getByObjectId("SC00020003");
                    if (dlr6!=null)vecDealers.add(dlr6);

                    Dealer dlr7 = Dealer.getToyotaHelper().getByObjectId("SC03720002");
                    if (dlr7!=null)vecDealers.add(dlr7);

                    Dealer dlr8 = Dealer.getToyotaHelper().getByObjectId("SC03720005");
                    if (dlr8!=null)vecDealers.add(dlr8);

                    Dealer dlr9 = Dealer.getToyotaHelper().getByObjectId("SC04030001");
                    if (dlr9!=null)vecDealers.add(dlr9);

                } else if (roles.contains(AppProfile.ROLE_VIEW_DEALER_ALL_INSTALLATION)) {
                    vecDealers = Dealer.getToyotaHelper().GetActiveDealersForParent(userPrincipal.getOidDealerParent());
                } else if (roles.contains(AppProfile.ROLE_VIEW_DEALER_OWN_INSTALLATION)) {
                    vecDealers.add(Dealer.getToyotaHelper().getByObjectId(userPrincipal.getOidDealer()));
                } else if (roles.contains(AppProfile.ROLE_IMPORT_EXPORT)) {
                    vecDealers = Dealer.getToyotaHelper().GetActiveDealersForParent(userPrincipal.getOidDealerParent());
                }
            } else if (userPrincipal.getOidNet().equalsIgnoreCase(Dealer.OID_NET_LEXUS)) {
                if (roles.contains(ROLE_VIEW_ALL_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetAllActiveDealers();
                } else if (roles.contains(ROLE_VIEW_CA_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetCADealers("S");
                } else if (roles.contains(ROLE_VIEW_CALL_CENTER_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetCADealers("S");
                } else if (roles.contains(AppProfile.ROLE_VIEW_DEALER_ALL_INSTALLATION)) {
                    vecDealers = Dealer.getLexusHelper().GetActiveDealersForParent(userPrincipal.getOidDealerParent());
                } else if (roles.contains(AppProfile.ROLE_VIEW_DEALER_OWN_INSTALLATION)) {
                    vecDealers.add(Dealer.getLexusHelper().getByObjectId(userPrincipal.getOidDealer()));
                } else if (roles.contains(AppProfile.ROLE_IMPORT_EXPORT)) {
                    vecDealers = Dealer.getLexusHelper().GetActiveDealersForParent(userPrincipal.getOidDealerParent());
                }
            }

            List<Dealer> dealers = new ArrayList<>(vecDealers);
            Dealer dealerNotDefined = new Dealer();
            dealerNotDefined.setObjectId("99");
            dealerNotDefined.setDesig("N/D");
            dealerNotDefined.setEnd(StringUtils.EMPTY);
            dealers.add(dealerNotDefined);
            return dealers;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching dealers", e);
        }
    }

    @Override
    public DelegatorsDTO getDelegators(UserPrincipal userPrincipal, GetDelegatorsDTO delegatorsDTO) {
        int fromYear = StringTasks.cleanInteger(delegatorsDTO.getFromYear(), 0);
        int toYear = StringTasks.cleanInteger(delegatorsDTO.getToYear(), 0);
        int fromMonth = StringTasks.cleanInteger(delegatorsDTO.getFromMonth(), 0);
        int toMonth = StringTasks.cleanInteger(delegatorsDTO.getToMonth(), 0);
        String[] arrayOidDealer = delegatorsDTO.getArrayOidDealer();
        String oidsDealer = "'*DUMMY*'";
        for (String currentOidDealer: arrayOidDealer) {
            oidsDealer += ",'" + currentOidDealer + "'";
        }

        List<String> listDelegators;
        Map<String, String> mapLastChangedBy;
        List<DelegatorsValues> delegators = new ArrayList<>();
        List<DelegatorsValues> changedBy = new ArrayList<>();
        try {
            listDelegators = paRepository.getDelegators(fromYear, fromMonth, toYear, toMonth, oidsDealer);

            DelegatorsValues delegatorsValue = DelegatorsValues.builder()
                    .value("*todos*")
                    .text("Todos")
                    .build();

            delegators.add(delegatorsValue);

            for (String delegatedTo: listDelegators) {
                delegatorsValue = DelegatorsValues.builder()
                        .value(delegatedTo)
                        .text(delegatedTo)
                        .build();

                delegators.add(delegatorsValue);
            }

            mapLastChangedBy = paRepository.getLastChangedBy(fromYear, fromMonth, toYear, toMonth, oidsDealer);

            DelegatorsValues changedByValues = DelegatorsValues.builder()
                    .value("*todos*")
                    .text("Todos")
                    .build();

            changedBy.add(changedByValues);

            changedByValues = DelegatorsValues.builder()
                    .value("*ninguem*")
                    .text("Ninguém")
                    .build();

            changedBy.add(changedByValues);

            for (String idChangedBy: mapLastChangedBy.keySet()) {
                String nameChangedBy = mapLastChangedBy.get(idChangedBy);

                changedByValues = DelegatorsValues.builder()
                        .value(idChangedBy)
                        .text(nameChangedBy)
                        .build();

                changedBy.add(changedByValues);
            }

        return DelegatorsDTO.builder()
                .delegators(delegators)
                .changedBy(changedBy)
                .build();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching delegators ", e);
        }
    }
}
