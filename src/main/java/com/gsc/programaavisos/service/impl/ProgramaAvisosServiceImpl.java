package com.gsc.programaavisos.service.impl;

import com.gsc.claims.object.auxiliary.Area;
import com.gsc.claims.object.auxiliary.DealerLevel1;
import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.PaDataInfoP;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ProgramaAvisosService;
import com.gsc.programaavisos.service.converter.PaDataInfoConverter;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.rg.dealer.Dealer;
import com.sc.commons.comunications.Mail;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DateTimerTasks;
import com.sc.commons.utils.StringTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gsc.claims.object.core.ClaimDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.gsc.programaavisos.constants.AppProfile.*;


@Service
@Log4j
@RequiredArgsConstructor
public class ProgramaAvisosServiceImpl implements ProgramaAvisosService {

    public final SimpleDateFormat timeZoFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    private final PARepository paRepository;
    private final VehicleRepository vehicleRepository;
    private final QuarantineRepository quarantineRepository;
    private final PaDataInfoRepository paDataInfoRepository;
    private final PARepository programaAvisosRepository;
    private final ProgramaAvisosUtil programaAvisosUtil;

    public static final String MRS_MAINTENANCE_TYPE_PRE_ITV			= "Pr�-ITV + Pack �leo e Filtro";



    public void savePA(UserPrincipal userPrincipal, PADTO pa) {
        String contactChanged;
        try {
            if(pa.getId() > 0) {
                ProgramaAvisos oPA =  dataPA(pa);
                contactChanged = StringTasks.cleanString(pa.getContactChanged(), "0");
                String hrScheduleContact = StringTasks.cleanString(pa.getHrScheduleContact(), StringUtils.EMPTY).trim();
                if (!hrScheduleContact.equals(StringUtils.EMPTY)) {
                    hrScheduleContact += ":00";
                    Time otime;
                    try {
                        otime = Time.valueOf(hrScheduleContact);
                        oPA.setHrScheduleContact(otime);
                    } catch (Exception e) {
                        oPA.setHrScheduleContact(null);
                    }
                } else {
                    oPA.setHrScheduleContact(null);
                }
                String registerClaim = StringTasks.cleanString(pa.getRegisterClaim(), "N").trim();
                String userStamp = userPrincipal.getUsername().split("\\|\\|")[0]+"||"+userPrincipal.getUsername().split("\\|\\|")[1];
                if(registerClaim.equalsIgnoreCase("S") && !oPA.getObservations().equals(StringUtils.EMPTY)) {
                    try {
                        ClaimDetail oClaim;
                       Dealer oDealerPA = Dealer.getHelper().getByObjectId(userPrincipal.getOidNet(), oPA.getOidDealer());
                        oClaim = ClaimDetail.getHelper().createClaim(com.gsc.claims.initialization.ApplicationConfiguration.PORTAL_EXTRANET,
                                userPrincipal.getOidNet().equals(Dealer.OID_NET_TOYOTA)?com.gsc.claims.initialization.ApplicationConfiguration.TOYOTA_APP:com.gsc.claims.initialization.ApplicationConfiguration.LEXUS_APP, null, null, oDealerPA.getOid_Parent(), null, oPA.getName(),
                                oPA.getAddress(), oPA.getCp4(),	oPA.getCp3(), oPA.getCpext(), oPA.getEmail(), oPA.getContactPhone(), null,
                                pa.getObservations(), userPrincipal.getOidNet().equals(Dealer.OID_NET_TOYOTA)? DealerLevel1.TCAP_TOYOTA_DEALERLEVEL1:DealerLevel1.TCAP_LEXUS_DEALERLEVEL1,
                                oDealerPA.getOid_Parent(), oPA.getOidDealer(), oPA.getLicensePlate(), oPA.getBrand().equals("T")?"TOYOTA":oPA.getBrand().equals("L")?"LEXUS":"", oPA.getModel(), Area.TCAP_TOYOTA_AFTERSALES,
                                PaConstants.CLAIMS_PA_CHANNEL, null, null, null, userStamp, null);
                        oPA.setIdClaim(oClaim.getId());
                    } catch (Exception e) {
                        throw new ProgramaAvisosException("There was an error registering the contact as a claim", e);
                    }
                }

                String isToSendSchedule = StringTasks.cleanString(pa.getSendSchedule(), StringUtils.EMPTY);
                if(isToSendSchedule!=null && isToSendSchedule.equals("S")){
                    String oidDealerSchedule = StringTasks.cleanString(pa.getOidDealerSchedule(), StringUtils.EMPTY).trim();
                    int hrHrSchedule = StringTasks.cleanInteger(pa.getHrHrSchedule(), 0);
                    int minHrSchedule = StringTasks.cleanInteger(pa.getMinHrSchedule(), 0);
                    java.util.Date dtSchedule = StringTasks.cleanDate(pa.getDtSchedule());
                    oPA.setOidDealerSchedule(oidDealerSchedule);
                    oPA.setDtSchedule(dtSchedule);
                    String hrSchedule = "";
                    if(hrHrSchedule<10){
                        hrSchedule+="0";
                    }
                    hrSchedule += String.valueOf(hrHrSchedule);
                    hrSchedule += ":";
                    if(minHrSchedule<10){
                        hrSchedule+="0";
                    }
                    hrSchedule += String.valueOf(minHrSchedule);
                    if (!hrSchedule.equals(StringUtils.EMPTY)) {
                        hrSchedule += ":00";
                        Time otime;
                        try {
                            otime = Time.valueOf(hrSchedule);
                            oPA.setHrSchedule(otime);
                        } catch (Exception e) {
                            oPA.setHrSchedule(null);
                        }
                    } else {
                        oPA.setHrSchedule(null);
                    }
                    sendMail(oPA,dtSchedule,hrSchedule,oidDealerSchedule);
                }
                oPA.setBlockedBy(StringUtils.EMPTY);
                programaAvisosUtil.save(String.valueOf(137), userPrincipal.getRoles().contains(ROLE_VIEW_CALL_CENTER_DEALERS), oPA);

                if(oPA.getRevisionScheduleMotive().equalsIgnoreCase(PaConstants.RSM_NOT_OWNER) || oPA.getRevisionSchedule().equalsIgnoreCase(PaConstants.RSM_NOT_OWNER2)) {
                    dataVehicle(oPA.getLicensePlate());
                }
                if(contactChanged.equals("S")) {
                    dataQuarantine(oPA,userStamp, userPrincipal.getOidNet());
                }
            }
        }catch (Exception e) {
            log.error("An error occurred while saving contact record");
            throw new ProgramaAvisosException("An error occurred while saving contact record", e);
        }
    }

    @Override
    public void removePA(UserPrincipal userPrincipal, Integer id, String removedOption, String removedObs) {
        log.info("removePA service");
        try {
             ProgramaAvisos oPA = paRepository.findById(id).orElseThrow(()-> new ProgramaAvisosException("Id not found: " + id));
            if(oPA.getId() > 0) {
              oPA = ProgramaAvisos.builder()
                      .successContact("N")
                      .successMotive(StringUtils.EMPTY)
                      .dtScheduleContact(null)
                      .hrScheduleContact(null)
                      .revisionSchedule(PaConstants.REMOVED_MANUALLY_DESC)
                      .revisionScheduleMotive(StringUtils.EMPTY)
                      .revisionScheduleMotive2(StringUtils.EMPTY)
                      .removedObs(removedObs.equals(StringUtils.EMPTY) ? removedOption : removedOption + ": " + removedObs)
                      .build();

                programaAvisosUtil.save(String.valueOf(137), userPrincipal.getRoles().contains(ROLE_VIEW_CALL_CENTER_DEALERS), oPA);

            }
        } catch (Exception e) {
            log.error("An error occurred while removing a record from the list");
            throw new ProgramaAvisosException("An error occurred while removing a record from the list", e);
        }
    }

    @Override
    public FilterBean searchPA(UserPrincipal userPrincipal, SearchPADTO searchPADTO) {
        log.info("searchPA service");
        try {
            FilterBean filter = getFilter(userPrincipal);
            String changedBy  = StringTasks.cleanString(searchPADTO.getChangedBy(), PaConstants.ALL);
            String flagHybrid = StringTasks.cleanString(searchPADTO.getFlagHibrid(), StringUtils.EMPTY);
            String delegatedTo = StringTasks.cleanString(searchPADTO.getDelegatedTo(), PaConstants.ALL);
            String filterOptions[] 	= searchPADTO.getFilterOptions();
            String hasMaintenanceContract = StringTasks.cleanString(searchPADTO.getHasMaintenanceContract(), StringUtils.EMPTY);
            String mrsMissedCalls = StringTasks.cleanString(searchPADTO.getMrsMissedCalls(), StringUtils.EMPTY);
            String plate = StringTasks.ReplaceStr(StringTasks.cleanString(searchPADTO.getPlate(), StringUtils.EMPTY), "'", StringUtils.EMPTY);
            String flag5Plus = StringTasks.cleanString(searchPADTO.getFlag5Plus(), StringUtils.EMPTY);
            boolean ShowImportByExcel 	= StringTasks.cleanString(searchPADTO.getShowImportByExcell(), "N").equalsIgnoreCase("S");
            String filterOwner 	= StringTasks.cleanString(searchPADTO.getFilterOwner(), StringUtils.EMPTY);
            log.debug("filterOwner: "+filterOwner);

            filter.setFromYear(searchPADTO.getFromYear());
            filter.setFromMonth(searchPADTO.getFromMonth());
            filter.setToYear(searchPADTO.getToYear());
            filter.setToMonth(searchPADTO.getToMonth());
            if (searchPADTO.getArrOidDealer()!=null)
                filter.setArrSelDealer(searchPADTO.getArrOidDealer());

            filter.setChangedBy(changedBy);
            filter.setChangedList(null);
            filter.setIdSource(searchPADTO.getIdSource());
            filter.setIdChannel(searchPADTO.getIdChannel());
            filter.setIdContactType(searchPADTO.getIdContactType());
            filter.setFlagHibrid(flagHybrid);
            filter.setDelegatedTo(delegatedTo);
            filter.setDelegators(null);
            filter.clearState();
            if(filterOptions != null) {
                for (String filterOption : filterOptions) {
                    if (filterOption.equalsIgnoreCase("pending")) {
                        filter.setStatePending(1);
                    } else if (filterOption.equalsIgnoreCase("hasSchedule")) {
                        filter.setStateHasSchedule(1);
                    } else if (filterOption.equalsIgnoreCase("scheduleDone")) {
                        filter.setStateScheduleDone(1);
                    } else if (filterOption.equalsIgnoreCase("scheduleRejected")) {
                        filter.setStateScheduleRejected(1);
                    } else if (filterOption.equalsIgnoreCase("notOwner")) {
                        filter.setStateNotOwner(1);
                    } else if (filterOption.equalsIgnoreCase("astContactsClient")) {
                        filter.setStateAstContactsClient(1);
                    } else if (filterOption.equalsIgnoreCase("clientScheduledAtWorkshop")) {
                        filter.setStateClientScheduledAtWorkshop(1);
                    } else if (filterOption.equalsIgnoreCase("showRemovedManually")) {
                        filter.setStateShowRemovedManually(1);
                    } else if (filterOption.equalsIgnoreCase("showRemovedAutoByManut")) {
                        filter.setStateShowRemovedAutoByManut(1);
                    } else if (filterOption.equalsIgnoreCase("showRemovedAutoByPeriod")) {
                        filter.setStateShowRemovedAutoByPeriod(1);
                    }
                }
            }
            filter.setArrSelMaintenanceTypes(searchPADTO.getArrMaintenanceTypes());
            filter.setHasMaintenanceContract(hasMaintenanceContract);
            filter.setMissedCalls(mrsMissedCalls);
            filter.setPlate(plate);
            filter.setFlag5Plus(flag5Plus);
            filter.setIdClientType(searchPADTO.getIdClientType());
            filter.setShowImportByExcell(ShowImportByExcel);
            filter.setCurrPage(1);
            filter.setOrderColumn("");
            filter.setOwner(filterOwner);
            return filter;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error searchPA ", e);
        }
    }

    @Override
    public void uploadFile(UserPrincipal userPrincipal, MultipartFile file) {

        String importErrors = "";
        try {
            //TODO Validate file
            // validate file
//            String importErrors = CSVUploadFileUtils.validateFile(file);

            // parse file and update DB
            importErrors = parseAndUpdateDB(file, userPrincipal, importErrors);
        } catch(Exception e) {
            throw new ProgramaAvisosException("An error occurred while uploading files ", e);
        }

    }

    private ProgramaAvisos dataPA(PADTO pa){
        ProgramaAvisos oPA;
        oPA = paRepository.findById(pa.getId()).orElseThrow(()-> new ProgramaAvisosException("Id not found: " + pa.getId()));
        String revisionScheduleMotive = StringTasks.cleanString(pa.getRevisionScheduleMotive(), "").trim();
        String newsletterReceived = StringTasks.cleanString(pa.getNewsletterReceived(), "").trim();
        String notReceivedMotive = StringTasks.cleanString(pa.getNotReceivedMotive(), "").trim();
        String km = StringTasks.cleanString(pa.getKm(), "").trim();
        String revisionScheduleMotive2 = StringTasks.cleanString(pa.getGeralRevisionScheduleMotive2(), "");
        oPA.setDataIsCorrect(StringTasks.cleanString(pa.getDataIsCorrect(), ""));
        oPA.setNewNif(StringTasks.cleanString(pa.getNewNif(), "").trim());
        oPA.setNewName(StringTasks.cleanString(pa.getNewName(), "").trim());
        oPA.setNewAddress(StringTasks.cleanString(pa.getNewAddress(), "").trim());
        oPA.setNewAddressNumber(StringTasks.cleanString(pa.getNewAddressNumber(), "").trim());
        oPA.setNewFloor(StringTasks.cleanString(pa.getNewFloor(), "").trim());
        oPA.setNewCp4(StringTasks.cleanString(pa.getNewCp4(), "").trim());
        oPA.setNewCp3(StringTasks.cleanString(pa.getNewCp3(), "").trim());
        oPA.setNewCpExt(StringTasks.cleanString(pa.getNewCpExt(), "").trim());
        oPA.setNewContactPhone(StringTasks.cleanString(pa.getNewContactPhone(), "").trim());
        oPA.setNewEmail(StringTasks.cleanString(pa.getNewEmail(), "").trim());
        oPA.setSuccessContact( StringTasks.cleanString(pa.getSuccessContact(), ""));
        oPA.setSuccessMotive(StringTasks.cleanString(pa.getSuccessMotive(), "").trim());
        oPA.setDtScheduleContact(StringTasks.cleanDate(pa.getDtScheduleContact()));
        oPA.setRecoveryAndShipping(StringTasks.cleanString(pa.getRecoveryAndShipping(), "").trim());
        oPA.setRevisionSchedule(StringTasks.cleanString(pa.getRevisionSchedule(), "").trim());
        oPA.setRevisionScheduleMotive(revisionScheduleMotive);
        oPA.setRevisionScheduleMotive2(revisionScheduleMotive.equalsIgnoreCase(PaConstants.RSM_KM_ERROR) ? km : revisionScheduleMotive2);
        oPA.setReceiveInformation(PaConstants.RECEIVE_INFORMATION_NO.equals(newsletterReceived) ? notReceivedMotive : newsletterReceived);
        oPA.setIdClientChannelPreference(StringTasks.cleanInteger(pa.getIdClientChannelPreference(), 0));
        oPA.setObservations(StringTasks.cleanString(pa.getObservations(), "").trim());
        return oPA;
    }

    private void dataVehicle(String licencePlate){
        if(licencePlate!=null)
            licencePlate = StringTasks.ReplaceStr(licencePlate, "-", "").toUpperCase();

        Vehicle vehicle = vehicleRepository.getVehicle(licencePlate);
        if(vehicle != null) {
            vehicle.setIdOwner(0);
            vehicle.setIdUser(0);
            vehicle.setIdFinancial(0);
            vehicleRepository.save(vehicle);
        }
    }
    private void dataQuarantine(ProgramaAvisos  oPA,String userStamp,String oidNet) throws SCErrorException {
        Quarantine quarantine = new Quarantine();
        Dealer dealer = Dealer.getHelper().getByObjectId(oidNet, oPA.getOidDealer());
        Calendar cal = Calendar.getInstance();
        quarantine.setDealerParent(dealer.getOid_Parent());
        quarantine.setIdOrigin(PaConstants.ID_ORIGIN_MRS);
        quarantine.setIdEvent(PaConstants.ID_EVENT_MRS);
        quarantine.setEventDate(new java.sql.Date(cal.getTime().getTime()));
        quarantine.setName(oPA.getNewName());
        quarantine.setNif(oPA.getNewNif());
        quarantine.setAddress(oPA.getNewAddress());
        quarantine.setHouseNumber(oPA.getNewAddressNumber());
        quarantine.setFloor(oPA.getNewFloor());
        quarantine.setCp4(oPA.getNewCp4());
        quarantine.setCp3(oPA.getNewCp3());
        quarantine.setCpExt(oPA.getNewCpExt());
        quarantine.setPhone1(oPA.getNewContactPhone());
        if(!oPA.getNewEmail().equals("")) {
            quarantine.setEmail(oPA.getNewEmail());
        }
        quarantine.setLicencePlate(oPA.getLicensePlate());
        quarantine.setVin(oPA.getVin());
        quarantine.setIsVehicleOwner("S");

        switch(oPA.getIdClientChannelPreference()){
            case PaConstants.EMAIL:
                quarantine.setPaPreferredCommunicationnChannel(PaConstants.PA_CONTACT_CHANNEL_EPOSTAL);
                break;
            case PaConstants.POSTAL:
                quarantine.setPaPreferredCommunicationnChannel(PaConstants.PA_CONTACT_CHANNEL_POSTAL);
                break;
            case PaConstants.SMS:
                quarantine.setPaPreferredCommunicationnChannel(PaConstants.PA_CONTACT_CHANNEL_SMS);
                break;
            default:
                quarantine.setPaPreferredCommunicationnChannel(StringUtils.EMPTY);
                break;
        }
        quarantine.setCreatedBy(userStamp);
        quarantineRepository.save(quarantine);
    }

    private void sendMail(ProgramaAvisos oPA, java.util.Date dtSchedule, String hrSchedule, String oidDealerSchedule) throws SCErrorException {
        String from = oPA.getBrand().equals("T")?"Toyota<toyota@toyotacaetano.pt>":"Lexus<info@lexus.pt>";
        String to = StringTasks.cleanString(oPA.getNewEmail(), "").equals("") ? oPA.getEmail() : oPA.getNewEmail();
        String subject = "Ir à "+ (oPA.getBrand().equals("T")?"Toyota":"Lexus");
        String eventDescription = (oPA.getBrand().equals("T")?"Toyota":"Lexus")+ " " + oPA.getModel() + " " + oPA.getLicensePlate();

        String dateScheduledFormatted = DateTimerTasks.fmtDT2.format(dtSchedule)+"T"+hrSchedule.replace(":","")+"Z";
        Calendar oCal = Calendar.getInstance();
        String currentDateFormatted = timeZoFormat.format(oCal.getTime());
        String strICalendar = getEmailStr(from, to, oidDealerSchedule, eventDescription , subject, dateScheduledFormatted, currentDateFormatted);
        Mail.SendMailICalendar(from, to, "", subject, strICalendar);
    }

    private String getEmailStr(String from, String to,String oidDealer, String eventDescription, String subject, String dateScheduledFormatted, String currentDateFormatted) throws SCErrorException {
        Dealer oDealer = ApplicationConfiguration.getDealer(oidDealer);
        String dealerDesc = oDealer.getDesig()+" ( "+oDealer.getCpExt() + " ) - " + oDealer.getEnd();
        StringBuffer buffer = new StringBuffer("BEGIN:VCALENDAR\n" +
                "PRODID:-//rede.toyota.pt/ProgramaAvisos\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "X-WR-TIMEZONE:Europe/Lisbon\n" +
                "BEGIN:VEVENT\n" +
                "UID:/ProgramaAvisos/" + System.currentTimeMillis() + "\n" +
                "CLASS:PUBLIC\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+to+"\n" +
                "DTSTART: "+dateScheduledFormatted+"\n" +
                "DTEND:"+dateScheduledFormatted+"\n" +
                "DTSTAMP:"+currentDateFormatted+"\n" +
                "DESCRIPTION:"+eventDescription+"\n" +
                "LOCATION:" +dealerDesc+"\n" +
                "GEO:"+oDealer.getGPS_X()+";"+oDealer.getGPS_Y()+"\n" +
                "ORGANIZER:MAILTO:"+from+"\n" +
                "PRIORITY:5\n" +
                "SEQUENCE:0\n" +
                "SUMMARY:"+subject+"\n" +
                "TRANSP:OPAQUE\n" +
                "CATEGORIES:Oficina\n" +
                "BEGIN:VALARM\n" +
                "TRIGGER:-PT1440M\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:Reminder\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
        return buffer.toString();
    }

    public FilterBean getFilter(UserPrincipal oGSCUser) throws SCErrorException {
        FilterBean filter = null;
        if (filter == null) {
            List<Dealer> vecDealers = new ArrayList<>();
            if (oGSCUser.getOidNet().equalsIgnoreCase(Dealer.OID_NET_TOYOTA)) {
                if (oGSCUser.getRoles().contains(ROLE_VIEW_ALL_DEALERS)) {
                    vecDealers = Dealer.getToyotaHelper().GetAllActiveDealers();
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_CA_DEALERS)) {
                    vecDealers = Dealer.getToyotaHelper().GetCADealers("S");
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_CALL_CENTER_DEALERS)) {
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

                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_DEALER_ALL_INSTALLATION)) {
                    vecDealers = Dealer.getToyotaHelper().GetActiveDealersForParent(oGSCUser.getOidDealerParent());
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_DEALER_OWN_INSTALLATION)) {
                    vecDealers.add(Dealer.getToyotaHelper().getByObjectId(oGSCUser.getOidDealer()));
                } else if (oGSCUser.getRoles().contains(ROLE_IMPORT_EXPORT)) {
                    vecDealers = Dealer.getToyotaHelper().GetActiveDealersForParent(oGSCUser.getOidDealerParent());
                }
            } else if (oGSCUser.getOidNet().equalsIgnoreCase(Dealer.OID_NET_LEXUS)) {
                if (oGSCUser.getRoles().contains(ROLE_VIEW_ALL_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetAllActiveDealers();
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_CA_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetCADealers("S");
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_CALL_CENTER_DEALERS)) {
                    vecDealers = Dealer.getLexusHelper().GetCADealers("S");
                    Dealer dlr10 = Dealer.getLexusHelper().getByObjectId("SC04500003");
                    if (dlr10!=null)vecDealers.add(dlr10);
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_DEALER_ALL_INSTALLATION)) {
                    vecDealers = Dealer.getLexusHelper().GetActiveDealersForParent(oGSCUser.getOidDealerParent());
                } else if (oGSCUser.getRoles().contains(ROLE_VIEW_DEALER_OWN_INSTALLATION)) {
                    vecDealers.add(Dealer.getLexusHelper().getByObjectId(oGSCUser.getOidDealer()));
                } else if (oGSCUser.getRoles().contains(ROLE_IMPORT_EXPORT)) {
                    vecDealers = Dealer.getLexusHelper().GetActiveDealersForParent(oGSCUser.getOidDealerParent());
                }
            }
            filter = new FilterBean();
            Calendar cal = Calendar.getInstance();
            int toYear = cal.get(Calendar.YEAR);
            int toMonth = cal.get(Calendar.MONTH) + 1;

            cal.add(Calendar.MONTH, -1);
            int fromYear = cal.get(Calendar.YEAR);
            int fromMonth = cal.get(Calendar.MONTH) + 1;

            filter.setFromYear(fromYear);
            filter.setFromMonth(fromMonth);
            filter.setToYear(toYear);
            filter.setToMonth(toMonth);
            filter.setVecDealers(vecDealers);
            String[] arrDealer;
            int pos = 0;
            if(vecDealers == null) {
                arrDealer = new String[] {oGSCUser.getOidDealer()};
            } else {
                arrDealer = new String[vecDealers.size()];
                for (Dealer oDealer : vecDealers) {
                    arrDealer[pos++] = oDealer.getObjectId();
                }
            }
            filter.setArrSelDealer(arrDealer);
            filter.setPlate(StringUtils.EMPTY);
            filter.setStatePending(1);
            filter.setStateHasSchedule(1);
            filter.setArrSelMaintenanceTypes(null);
            filter.setChangedBy(PaConstants.ALL);
            filter.setDelegatedTo(PaConstants.ALL);
            filter.setMissedCalls(PaConstants.ALL);
            filter.setCurrPage(1);
            filter.setFirstPage(1);
            filter.setShowImportByExcell(false);

            //TODO VALIDAR DE  DONDE TOMAR VARIABLE
//            filter.setGSCUserLogin(oGSCUser.getLogin());
        }
        return filter;
    }

    public String parseAndUpdateDB(MultipartFile file, UserPrincipal oGSCUser, String importErrors) {

        try(Reader reader = new BufferedReader(new InputStreamReader((file.getInputStream())))) {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
            List<String[]> rows = csvReader.readAll();

            for (String[] tokens : rows.subList(1, rows.size())) {
                int j = 0;
                String temp			=  tokens[j++];
                String dealerCode	= temp.substring(0, 2);
                String afterSalesCode	= temp.substring(3,6);
                int year =  0, month = 0;
                String licencePlate = "";
                try {
                    year			=  Integer.parseInt(tokens[j++]);
                    month			= Integer.parseInt(tokens[j++]);
                    licencePlate	= tokens[j++];
                } catch (Exception e) {
                   log.error("Error parsing csv rows ", e);
                }
                String delegatedTo = "";
                if(tokens.length > 4) {
                    delegatedTo = tokens[j++];
                    delegatedTo = CleanDelegatedTo(delegatedTo);
                }
                String clientNumber = "";
                if(tokens.length > 5) {
                    clientNumber = tokens[j++];
                }

                //Update in DB
                Dealer oDealer = null;
                if (oGSCUser.getOidNet().equalsIgnoreCase(Dealer.OID_NET_TOYOTA)) {
                    oDealer = Dealer.getToyotaHelper().getByDealerAndAfterSalesCode(dealerCode, afterSalesCode);
                } else if (oGSCUser.getOidNet().equalsIgnoreCase(Dealer.OID_NET_LEXUS)) {
                    oDealer = Dealer.getLexusHelper().getByDealerAndAfterSalesCode(dealerCode, afterSalesCode);
                }
                String oidDealer = "";
                if (oDealer!=null)
                    oidDealer = oDealer.getObjectId();
                PaDataInfo oPABeanO = paDataInfoRepository.getProgramaAvisosByYearMonthPlateDealer(year, oidDealer);
                PaDataInfoP oPABean = fillPABean(oPABeanO);


                boolean isToUpdate = false;
                if(oPABean != null ) {
                    if(! StringTasks.cleanString(oPABean.getPaLicensePlate(),"").equals("")) {
                        if(!StringTasks.cleanString(oPABean.getPaDelegatedTo(),"").equalsIgnoreCase(delegatedTo)) {
                            isToUpdate = true;
                        }
                        if(!StringTasks.cleanString(oPABean.getPaClient(),"").equalsIgnoreCase(clientNumber)) {
                            isToUpdate = true;
                        }
                        if(isToUpdate) {
                            ProgramaAvisos oPA = programaAvisosRepository.findById(oPABean.getPaId()).orElse(null);

                            if(oPA!=null) {
                                oPA.setDelegatedTo(StringTasks.cleanString(delegatedTo,"").toUpperCase().trim());
                                oPA.setClient(clientNumber.trim());
                                programaAvisosUtil.save("", false, oPA);
                                log.debug("Atualizar para id:" + oPA.getId());
                            }
                        } else {
                            log.debug("Nao atualizar linha: ");
                        }
                    }
                } else {
                    importErrors +=  tokens + " - Linha inválida."+ "<BR/>";
                }
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("There was an error reading the file", e);
        }

        return importErrors;
    }
    private static String CleanDelegatedTo(String delegatedTo) {

        String delegated = delegatedTo;
        if (delegatedTo==null)
            delegated = "";
        else if (delegatedTo.equalsIgnoreCase("#N/D"))
            delegated = "";
        else if (delegatedTo.equalsIgnoreCase("."))
            delegated = "";
        else if (delegatedTo.equalsIgnoreCase("0"))
            delegated = "";

        return delegated;
    }


    public static PaDataInfoP fillPABean(PaDataInfo oPABeanO) {
        if(oPABeanO!=null) {
            PaDataInfoP paDataInfoP = PaDataInfoConverter.paDataInfoToP(oPABeanO);
            switch (paDataInfoP.getPaIdContactType()) {
                case ContactTypeB.MAN:
                    paDataInfoP.setDescription(paDataInfoP.getNextRevision());
                    paDataInfoP.setExpectedDate(paDataInfoP.getMrsDtNextRevision() == null ? "" : paDataInfoP.getMrsDtNextRevision().toString());
                    break;
                case ContactTypeB.ITV:
                    if (MRS_MAINTENANCE_TYPE_PRE_ITV.equalsIgnoreCase(paDataInfoP.getNextRevision()))
                        paDataInfoP.setDescription("Pr�-ITV + Pack");
                    else
                        paDataInfoP.setDescription(paDataInfoP.getNextRevision());
                    paDataInfoP.setExpectedDate(paDataInfoP.getMrsDtItv() == null ? "" : paDataInfoP.getMrsDtItv() .toString());
                    break;
                case ContactTypeB.MAN_ITV:
                    paDataInfoP.setDescription(paDataInfoP.getNextRevision());
                    paDataInfoP.setExpectedDate((paDataInfoP.getMrsDtNextRevision()== null ? "" : paDataInfoP.getMrsDtNextRevision().toString())+ "</br>" + (paDataInfoP.getMrsDtItv()  == null ? "" : paDataInfoP.getMrsDtItv().toString()));
                    break;
                case ContactTypeB.EXTRACARE_PLUS:
                    paDataInfoP.setDescription("Ext. Garantia Mais");
                    paDataInfoP.setExpectedDate(paDataInfoP.getExtracarePlusDtLimitRenovation() == null ? "" : paDataInfoP.getExtracarePlusDtLimitRenovation().toString());
                    break;
                case ContactTypeB.MAINTENANCE_CONTRACT:
                    paDataInfoP.setDescription("Contrato de manuten��o Expirado");
                    paDataInfoP.setExpectedDate(paDataInfoP.getMcDtFinishContract() == null ? "" : paDataInfoP.getMcDtFinishContract().toString());
                    break;
                case ContactTypeB.TECHNICAL_CAMPAIGN:
                    paDataInfoP.setDescription("Campanha T�cnica ("+StringTasks.cleanString(paDataInfoP.getTcCampaign(),"")+")");
                    paDataInfoP.setExpectedDate("");
                    break;
                case ContactTypeB.COMMERCIAL_CAMPAIGN:
                    paDataInfoP.setDescription("Campanha Comercial");
                    paDataInfoP.setExpectedDate("");
                    break;
                case ContactTypeB.COMMERCIAL_CAMPAIGN_APV_MAP:
                    paDataInfoP.setDescription("Atualiza��o Mapas");
                    paDataInfoP.setExpectedDate("");
                    break;
                case ContactTypeB.CONNECTIVITY:
                    paDataInfoP.setDescription("Conectividade MyT");
                    paDataInfoP.setExpectedDate(paDataInfoP.getPaDtVisible() == null ? "" : paDataInfoP.getPaDtVisible().toString());
                    break;
                default:
                    break;
            }

            return paDataInfoP;
        }

        return null;
    }
}
