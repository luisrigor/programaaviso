package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.AppProfile;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.dto.ProgramaAvisosBean;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.cardb.CombustivelRepository;
import com.gsc.programaavisos.repository.cardb.ModeloRepository;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.OtherFlowService;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import com.gsc.programaavisos.service.impl.pa.TPALexusUtil;
import com.gsc.programaavisos.service.impl.pa.TPAToyotaUtil;
import com.gsc.programaavisos.util.TPAInvokerSimulator;
import com.gsc.ws.newsletter.core.WsResponse;
import com.gsc.ws.newsletter.invoke.WsInvokeNewsletter;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.*;
import com.sc.commons.utils.CarTasks;
import com.sc.commons.utils.HttpTasks;
import com.sc.commons.utils.StringTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Date;
import java.util.*;
import static com.gsc.programaavisos.config.environment.MapProfileVariables.*;
import static com.gsc.programaavisos.constants.ApiConstants.PRODUCTION_SERVER_STR;
import static com.gsc.programaavisos.constants.AppProfile.*;

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
    private final ContactTypeMaintenanceTypeRepository maintenanceTypeRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final PaDataInfoRepository dataInfoRepository;
    private final CcRigorServiceRepository ccRepository;


    private static final String QUOTES = "\"";
    private final ClientTypeRepository clientTypeRepository;
    private final SourceRepository sourceRepository;
    private final ChannelRepository channelRepository;

    private final ContactTypeRepositoryCRM contactTypeRepositoryCRM;

    private final TPAToyotaUtil tpaToyotaUtil;
    private final TPALexusUtil tpaLexusUtil;
    private final Environment env;
    private final EnvironmentConfig environmentConfig;



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

    @Override
    public List<Object[]> getChangedList( GetDelegatorsDTO delegatorsDTO) {
        Integer fromYear = Integer.valueOf(delegatorsDTO.getFromYear());
        Integer toYear = Integer.valueOf(delegatorsDTO.getToYear());
        Integer fromMonth = Integer.valueOf(delegatorsDTO.getFromMonth());
        Integer toMonth = Integer.valueOf(delegatorsDTO.getToMonth());
        List<String> arrayOidDealer = Arrays.asList(delegatorsDTO.getArrayOidDealer());
        List<Object[]> changedList = dataInfoRepository.getDistinctChangedByNames(fromYear, fromMonth, toYear, toMonth, arrayOidDealer);
        return changedList;
    }

    @Override
    public List<ContactType> getContactTypeList(String userLogin) {

        List<ContactType> contactTypeList = contactTypeRepositoryCRM.getByState('S');
        List<ContactType> contactTypeListWAcess = new ArrayList<>();

        for (ContactType contactType: contactTypeList) {
            if(this.getContactAcces().get(contactType.getId())!=null
                    && !this.getContactAcces().get(contactType.getId()).isEmpty()){

                String[] users = this.getContactAcces().get(contactType.getId()).split(",");
                boolean hasAccess = false;
                for(String user : users){
                    if(user.trim().contentEquals(userLogin.trim())){
                        hasAccess = true;
                        break;
                    }
                }
                // se n�o houver acesso, remove o motivo de contato da listagem de filtros
                if(hasAccess)
                    contactTypeListWAcess.add(contactType);
            }
        }

        return contactTypeListWAcess;
    }


    @Override
    public ClientContactsDTO getPAClientContacts(String nif, String selPlate, int idPaData, FilterBean oPAFilterBean) {
        try {

            List<ClientPropDTO> contactsForPlate = new ArrayList<>();
            List<ClientPropDTO> contactsForClient = new ArrayList<>();

            Map<Integer, List<String>> contactType = getMaintenanceTypesByContactType();


            List<ProgramaAvisosBean> vecPABean = paRepository.getOpenContactsforClient(oPAFilterBean,nif, selPlate, contactType);


            log.trace("vecPABean..." + vecPABean.size());
            for (ProgramaAvisosBean oProgramaAvisosBean: vecPABean) {
                if (oProgramaAvisosBean.getLicensePlate().equalsIgnoreCase(selPlate)
                        && oProgramaAvisosBean.getId() != idPaData) {

                    ClientPropDTO contactsDTO = ClientPropDTO.builder()
                            .id(oProgramaAvisosBean.getId())
                            .nextRevision(oProgramaAvisosBean.getNextRevision())
                            .build();

                    contactsForPlate.add(contactsDTO);

                } else if (!oProgramaAvisosBean.getLicensePlate().equalsIgnoreCase(selPlate)) {
                    ClientPropDTO contactsDTO = ClientPropDTO.builder()
                            .id(oProgramaAvisosBean.getId())
                            .nextRevision(oProgramaAvisosBean.getNextRevision())
                            .licencePlate(CarTasks.formatPlate(oProgramaAvisosBean.getLicensePlate()))
                            .build();

                    contactsForClient.add(contactsDTO);
                }
            }

            return ClientContactsDTO.builder()
                    .contactsForPlate(contactsForPlate)
                    .contactsForClient(contactsForClient)
                    .build();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching plates list for the same customer");
        }
    }
    public List<ClientType> getClientTypes() {
        try {
            return clientTypeRepository.getByStatus("S".charAt(0));
        }catch (Exception e){
            throw new ProgramaAvisosException("Error fetching clientType", e);
        }
    }

    @Override
    public void mapUpdate(UserPrincipal userPrincipal) {
//        MultipartWrapper mpWrapper = null;
//        int MAXFILESIZE = 20971520;//20*1024*1024=20 MB
//        String msg="O ficheiro est� a ser processado, ser� notificado sobre o resultado por email.";
//        PrintWriter pr = null;
//
//        try {
//            mpWrapper = new MultipartWrapper(request,MAXFILESIZE);
//
//            FileItem fileAttachItem = mpWrapper.getFileItem("arquivo");
//            PlateToSmsThread thread = new PlateToSmsThread(fileAttachItem, oGSCUser);
//
//            thread.start();
//
//            Gson gson = new Gson();
//            String json = gson.toJson(msg);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            pr = response.getWriter();
//            pr.write(json);
//
//        } catch (Exception e) {
//            throw new ProgramaAvisosException("Error reading file ", e);
//        }
    }

    public Map<Integer, List<String>> getMaintenanceTypesByContactType() {
        Map<Integer, List<String>> MAP_CONTACT_MAINTENANCE_TYPES = new HashMap<>();
        try {
            List<ContactMaintenanceTypes> maintenanceByContactType = maintenanceTypeRepository.getMaintenanceByContactType();

            for (ContactMaintenanceTypes currentContact : maintenanceByContactType) {
                Integer idContractType = currentContact.getId();

                if (!MAP_CONTACT_MAINTENANCE_TYPES.containsKey(idContractType))
                    MAP_CONTACT_MAINTENANCE_TYPES.put(idContractType, new ArrayList<String>());
                MAP_CONTACT_MAINTENANCE_TYPES.get(idContractType).add(currentContact.getMaintenanceType());
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error querying MaintenanceTypesByContactType", e);
        }
        return MAP_CONTACT_MAINTENANCE_TYPES;
    }

    public Map<Integer, String> getContactAcces() {
        Map<Integer, String> MAP_PA_CONTACT_TYPE_ACCESS = new HashMap<Integer, String>();

        String USERS_CAN_VIEW_CONNECTIVITY = "";

        String[] activeProfiles = env.getActiveProfiles();


        if (!Arrays.asList(activeProfiles).contains(PRODUCTION_SERVER_STR)){
            USERS_CAN_VIEW_CONNECTIVITY = "tcap1@tpo";
        }else {
            USERS_CAN_VIEW_CONNECTIVITY = "andre.dias@tpo, tiago.cardoso@tpo, antonio.calafate@tpo, "
                    + "luciano.teixeira@tpo, joao.marques@tpo, carlos.valentim@tpo, ricardo.dinis@tpo, "
                    + "nuno.araujo@tpo, paulo.cunha@tpo, jose.oliveira@tpo,"
                    + "ana.fazenda@tpo.sccalisboa.pvelho, celia.bento@tpo.sccalisboa.pvelho, cr.freitas@tpo.sccaporto.circunvalacao, "
                    + "cristina.santos@tpo.sccalisboa.pvelho, gilda.alves@tpo.scca, patricia.matias@tpo.sccalisboa.pvelho, "
                    + "sonia.martins@tpo.casintra.riodemouro, t.lucia@tpo.cacentronorte.aveiro";
        }

        MAP_PA_CONTACT_TYPE_ACCESS.put(ContactTypeB.CONNECTIVITY, USERS_CAN_VIEW_CONNECTIVITY);

        return MAP_PA_CONTACT_TYPE_ACCESS;
    }

    public List<Channel> getChannels() {
        try {
            return channelRepository.getByStatus("S".charAt(0));
        }catch (Exception e){
            throw new ProgramaAvisosException("Error fetching channel ", e);
        }
    }

    @Override
    public List<Source> getSources() {
        try {
            return sourceRepository.getByStatus("S".charAt(0));
        }catch (Exception e){
            throw new ProgramaAvisosException("Error fetching source ", e);
        }
    }

    @Override
    public List<ContactType> getAllContactTypes() {
        try {
            return contactTypeRepository.findAll();
        }catch (Exception e){
            throw new ProgramaAvisosException("Error fetching source ", e);
        }
    }

    public void readMapUpdate(int year, int month, Integer idsContactType) {


    }

    @Override
    public List<MaintenanceTypeDTO>  getMaintenanceTypes(){
        try {
            return paRepository.getMaintenanceTypes();
        }catch (Exception e){
            throw new ProgramaAvisosException("Error fetching MainTypes ", e);
        }
    }

    @Override
    public void downloadSimulation(UserPrincipal oGSCUser, TpaSimulation simulation, HttpServletResponse response) {
        Map<String, String> envV = environmentConfig.getEnvVariables();
        File oFile = null;
        try {
            List<TpaSimulation> simulations = new ArrayList<TpaSimulation>();
            simulations.add(simulation);

            if(oGSCUser.getOidNet().equals(Dealer.OID_NET_TOYOTA)){
                oFile = tpaToyotaUtil.writePdf(simulations, false, envV.get(CONST_STATIC_FILES_URL),
                        envV.get(CONST_IMG_POSTAL_ACCESSORY_URL), envV.get(CONST_IMG_POSTAL_SERVICE_URL),
                        envV.get(CONST_IMG_POSTAL_HIGHLIGHT_URL), envV.get(CONST_IMG_POSTAL_HEADER_URL),
                        simulation.getPaData().getYear(), simulation.getPaData().getMonth(), false, null);
            }else if(oGSCUser.getOidNet().equals(Dealer.OID_NET_LEXUS)){
                oFile = tpaLexusUtil.writePdf(simulations, false, envV.get(CONST_STATIC_FILES_URL),
                        envV.get(CONST_IMG_POSTAL_ACCESSORY_URL), envV.get(CONST_IMG_POSTAL_SERVICE_URL),
                        envV.get(CONST_IMG_POSTAL_HIGHLIGHT_URL), envV.get(CONST_IMG_POSTAL_HEADER_URL),
                        simulation.getPaData().getYear(), simulation.getPaData().getMonth(), false, null);
            }
            if(oFile!=null){
                HttpTasks.sendFileToClient(response, oFile, oFile.getName());
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error downloading simulation ", e);
        }finally{
            if(oFile!=null && oFile.exists()){
                oFile.delete();
            }
        }
    }


    @Override
    public NewsLetterDTO sendNewsletter(Integer id, String email) {
        if (id == 0 || email.equals(""))
            throw new ProgramaAvisosException("The id and the email can't be empty");

        ProgramaAvisosBean oPABean = null;
        Map<String, String> envV = environmentConfig.getEnvVariables();

        try {
            oPABean = paRepository.getProgramaAvisosById(id);
            WsInvokeNewsletter oWsInfo = new WsInvokeNewsletter(envV.get(CONST_WS_NEWSLETTER_SERVER));
            // Contactos

            String[] fields = ProgramaAvisosUtil.getNewslettersFields(oPABean.getIdContactType(), oPABean.getBrand());
            log.trace("fields >> " + (fields == null ? "is null!" : Arrays.toString(fields)));

            String personalData = oPABean.getNewsletterPersonalData();
            int _Pos = personalData.indexOf(";");
            if (_Pos != -1)
                personalData = email + personalData.substring(_Pos);

            String[] arrEmailContacts = new String[] {};
            if(fields!=null){
                arrEmailContacts = new String[] { fields[1] };
                arrEmailContacts = (String[]) ArrayTasks.Expand(arrEmailContacts, 1);
                arrEmailContacts[1] = new String(personalData.getBytes(), "ISO-8859-1");
            }
            WsResponse oWsResponse = oWsInfo.addContactsAndReschedule(oPABean.getOidNewsletter(), arrEmailContacts);

            String operation = "success", msg = "Reenvio efetuado para o email " + email;
            if (!oWsResponse.getErrorDescription().equals("")) {
                operation = "error";
                msg = oWsResponse.getErrorDescription();
            }

            return NewsLetterDTO.builder()
                    .message(msg)
                    .operation(operation)
                    .build();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error send newsletter ", e);
        }

    }
    public  LinkedHashMap<Integer, DocumentUnit> getMapAllDocumentUnits() {
        LinkedHashMap<Integer, DocumentUnit> map = new LinkedHashMap<>();
        List<DocumentUnit> documentUnits = documentUnitRepository.findAll();
        documentUnits.forEach(documentUnit -> map.put(documentUnit.getId(), documentUnit));
        return map;
    }

}
