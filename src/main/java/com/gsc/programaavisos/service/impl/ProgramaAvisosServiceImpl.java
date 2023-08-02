package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.cardb.ModeloRepository;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ProgramaAvisosService;
import com.rg.dealer.Dealer;
import com.sc.commons.comunications.Mail;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DateTimerTasks;
import com.sc.commons.utils.StringTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


@Service
@Log4j
@RequiredArgsConstructor
public class ProgramaAvisosServiceImpl implements ProgramaAvisosService {

    public final SimpleDateFormat timeZoFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    private final PaParameterizationRepository paParameterizationRepository;
    private final ContactReasonRepository contactReasonRepository;
    private final ModeloRepository modeloRepository;
    private final GenreRepository genreRepository;
    private final EntityTypeRepository entityTypeRepository;
    private final DocumentUnitRepository documentUnitRepository;
    private final PARepository paRepository;
    private final VehicleRepository vehicleRepository;
    private final QuarantineRepository quarantineRepository;
    private final AgeRepository ageRepository;

    @Override
        public List<PaParameterization> searchParametrizations(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            List<String> selectedTypes = new ArrayList<>();

            if(!StringUtils.isEmpty(selectedTypeParam))
                selectedTypes = Arrays.asList(selectedTypeParam.split(","));

            ParameterizationFilter filter = ParameterizationFilter.builder()
                    .dtStart(startDate)
                    .dtEnd(endDate)
                    .selectedTypes(selectedTypes)
                    .idBrand(idBrand)
                    .build();

            return  paParameterizationRepository.getByFilter(filter);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching parametrizations ", e);
        }
    }

    @Override
    public List<DocumentUnit> searchDocumentUnit(Integer type, UserPrincipal userPrincipal) {
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
    public List<DocumentUnit> searchItems(String searchInput,Date startDate,Integer tpaItemType, UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            ItemFilter filter = ItemFilter.builder()
                    .searchInput(searchInput)
                    .itemType(tpaItemType)
                    .dtEnd(startDate)
                    .idBrand(idBrand)
                    .build();
            return  documentUnitRepository.getByFilter(filter);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching search items ", e);
        }
    }

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
            return ageRepository.findAll();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching age", e);
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
    public void savePA(UserPrincipal userPrincipal, PADTO pa) {
        Calendar startRequestJava = Calendar.getInstance();
        String contactChanged = "";
        int id = StringTasks.cleanInteger(String.valueOf(pa.getId()), 0);
        try {
            if(id > 0) {
                ProgramaAvisos oPA =  dataPA(pa);
                contactChanged = StringTasks.cleanString(pa.getContactChanged(), "0");
                String hrScheduleContact = StringTasks.cleanString(pa.getHrScheduleContact(), "").trim();
                if (!hrScheduleContact.equals("")) {
                    hrScheduleContact += ":00";
                    Time otime = null;
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
                if(registerClaim.equalsIgnoreCase("S") && !oPA.getObservations().equals("")) {
                    try {
                        ClaimDetail oClaim = new ClaimDetail();
                       /* Dealer oDealerPA = Dealer.getHelper().getByObjectId(oGSCUser.getOidNet(), oPA.getOidDealer());
                        oClaim = ClaimDetail.getHelper().createClaim(com.gsc.claims.initialization.ApplicationConfiguration.PORTAL_EXTRANET,
                                oGSCUser.getOidNet().equals(Dealer.OID_NET_TOYOTA)?com.gsc.claims.initialization.ApplicationConfiguration.TOYOTA_APP:com.gsc.claims.initialization.ApplicationConfiguration.LEXUS_APP, null, null, oDealerPA.getOid_Parent(), null, oPA.getName(),
                                oPA.getAddress(), oPA.getCp4(),	oPA.getCp3(), oPA.getCpext(), oPA.getEmail(), oPA.getContactPhone(), null,
                                observations, oGSCUser.getOidNet().equals(Dealer.OID_NET_TOYOTA)? DealerLevel1.TCAP_TOYOTA_DEALERLEVEL1:DealerLevel1.TCAP_LEXUS_DEALERLEVEL1,
                                oDealerPA.getOid_Parent(), oPA.getOidDealer(), oPA.getLicensePlate(), oPA.getBrand().equals("T")?"TOYOTA":oPA.getBrand().equals("L")?"LEXUS":"", oPA.getModel(), Area.TCAP_TOYOTA_AFTERSALES,
                                CLAIMS_PA_CHANNEL, null, null, null, oGSCUser.getUserStamp(), null);*/
                        oPA.setIdClaim(1);
                    } catch (Exception e) {
                        log.error("Ocorreu um erro ao registar o contato como reclamação");
                    }
                }

                String isToSendSchedule = StringTasks.cleanString(pa.getSendSchedule(), "");
                if(isToSendSchedule!=null && isToSendSchedule.equals("S")){
                    String oidDealerSchedule = StringTasks.cleanString(pa.getOidDealerSchedule(), "").trim();
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
                    if (!hrSchedule.equals("")) {
                        hrSchedule += ":00";
                        Time otime = null;
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
                oPA.setBlockedBy("");
                //oPA.save(String.valueOf(oGSCUser.getIdUser()), oGSCUser.containsRole(ApplicationConfiguration.ROLE_VIEW_CALL_CENTER_DEALERS));
                paRepository.save(oPA);

                if(oPA.getRevisionScheduleMotive().equalsIgnoreCase(PaConstants.RSM_NOT_OWNER) || oPA.getRevisionSchedule().equalsIgnoreCase(PaConstants.RSM_NOT_OWNER2)) {
                    dataVehicle(oPA.getLicensePlate());
                }
                if(contactChanged.equals("S")) {
                    String userStamp = userPrincipal.getUsername().split("\\|\\|")[0]+"||"+userPrincipal.getUsername().split("\\|\\|")[1];
                    dataQuarantine(oPA,userStamp);
                }
            }
        }catch (Exception e) {
            log.error("Ocorreu um erro ao guardar registo de contato");
        }
        //com.sc.commons.utils.MonitorTasks.MonitorOperation("Programa de Avisos","Save","SaveContact", oGSCUser.getLogin(), startRequestJava, java.util.Calendar.getInstance());
    }


    private ProgramaAvisos dataPA(PADTO pa){
        ProgramaAvisos oPA;
        oPA = paRepository.findById(pa.getId()).get();
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
        //Vehicle vehicle = Vehicle.getHelper().getVehicle(licencePlate);
        Vehicle vehicle = null;
        if(vehicle != null) {
            vehicle.setIdOwner(0);
            vehicle.setIdUser(0);
            vehicle.setIdFinancial(0);
            vehicleRepository.save(vehicle);
        }
    }
    private void dataQuarantine(ProgramaAvisos  oPA,String userStamp){
        Quarantine quarantine = new Quarantine();
        //Dealer dealer = Dealer.getHelper().getByObjectId(oGSCUser.getOidNet(), oPA.getOidDealer());
        Calendar cal = Calendar.getInstance();
        quarantine.setDealerParent("dealer.oidDealerParent");
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
                quarantine.setPaPreferredCommunicationnChannel("");
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
}
