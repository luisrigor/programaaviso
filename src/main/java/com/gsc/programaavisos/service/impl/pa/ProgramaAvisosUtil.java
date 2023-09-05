package com.gsc.programaavisos.service.impl.pa;

import com.gsc.gesdoc.core.GesDocUpload;
import com.gsc.gesdoc.invoke.GesDocVehicleDossierInvoke;
import com.gsc.programaavisos.constants.PAInfo;
import com.gsc.programaavisos.constants.State;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.Calls;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.CallsRepository;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.AEScrypterTasks;
import com.sc.commons.utils.StringTasks;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;


@Log4j
@Component
public class ProgramaAvisosUtil {

    private final PARepository paRepository;
    private final CallsRepository callsRepository;
    private static final String BRAND_TOYOTA = "T";

    private static final String BRAND_LEXUS = "L";
    private static final String PERSONAL_FIELDS = "email;urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;linkservice1;imgservice1;nameservice1;descservice1;linkservice2;imgservice2;nameservice2;descservice2;linkservice3;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;linkaccessory1;imgaccessory1;nameaccessory2;descaccessory2;linkaccessory2;imgaccessory2;linkhighlight1;imghighlight1";

    @Autowired
    public ProgramaAvisosUtil(PARepository paRepository, CallsRepository callsRepository) {
        this.paRepository = paRepository;
        this.callsRepository = callsRepository;
    }


    public void save(String changedBy, boolean isUserCallCenterRigor, ProgramaAvisos pa) {

        try {
            String recoveryAndShipping = StringTasks.cleanString(pa.getRecoveryAndShipping(), "");
            String revisionSchedule = StringTasks.cleanString(pa.getRevisionSchedule(), "");
            String revisionScheduleMotive = StringTasks.cleanString(pa.getRevisionScheduleMotive(), "");
            String revisionScheduleMotive2 = StringTasks.cleanString(pa.getRevisionScheduleMotive2(), "");
            String removedObs = StringTasks.cleanString(pa.getRemovedObs(), "");

            if (PAInfo.RSM_NOT_OWNER.equalsIgnoreCase(revisionScheduleMotive) || PAInfo.RSM_NOT_OWNER2.equalsIgnoreCase(revisionScheduleMotive))
                revisionSchedule = "";

            // Calcular id_Status
            Integer ivIdStatus = this.calculateIdStatus(pa, revisionSchedule, revisionScheduleMotive, removedObs, changedBy);
            pa.setIdStatus(ivIdStatus);

            Calls oCall = new Calls();
            if (!"".equals(changedBy)) {
                // registar contato efetuado
                oCall.setIdPaData(pa.getId());
                oCall.setSuccessContact(pa.getSuccessContact().charAt(0));
                oCall.setSuccessMotive(pa.getSuccessMotive());
                oCall.setDtScheduleContact(pa.getDtScheduleContact());
                oCall.setHrScheduleContact(pa.getHrScheduleContact());
                oCall.setRevisionSchedule(StringTasks.cleanString(revisionSchedule, ""));
                oCall.setRevisionScheduleMotive(revisionScheduleMotive);
                oCall.setRevisionScheduleMotive2(revisionScheduleMotive2);
                oCall.setRemovedObs(removedObs);
                oCall.setIsContactCcRigor(isUserCallCenterRigor ? 1 : 0);
                oCall.setRecoveryShipping(StringTasks.cleanString(pa.getRecoveryAndShipping(), ""));
                oCall.setCreatedBy(changedBy);
                oCall.setDtCreated(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

                oCall = callsRepository.save(oCall);
            }

            // Ao atualizar aqui, corrigir os triggers para situa��es de delete ou update
            //TODO - TS Perguntar qual � o trigger
            pa.setIdLastCall(oCall.getId());

            Integer ivNrCalls = pa.getNrCalls();
            if (!State.PENDING_DESC.equalsIgnoreCase(revisionSchedule) && !State.REMOVED_MANUALLY_DESC.equalsIgnoreCase(revisionSchedule) && !State.UNAVAILABLE_FOR_CONTACT_DESC.equalsIgnoreCase(revisionSchedule))
                ivNrCalls++;


            Integer ivNrMissedCalls = pa.getNrMissedCalls();
            if ("N".equalsIgnoreCase(pa.getSuccessContact()) && !State.PENDING_DESC.equalsIgnoreCase(revisionSchedule) && !State.REMOVED_MANUALLY_DESC.equalsIgnoreCase(revisionSchedule) && !State.UNAVAILABLE_FOR_CONTACT_DESC.equalsIgnoreCase(revisionSchedule))
                ivNrMissedCalls++;

            this.setEmptyFields(pa);

            pa.setRecoveryAndShipping(recoveryAndShipping);
            pa.setRevisionSchedule(revisionSchedule);
            pa.setRevisionScheduleMotive(revisionScheduleMotive);
            pa.setRevisionScheduleMotive2(revisionScheduleMotive2);
            pa.setRemovedObs(removedObs);
            pa.setNrCalls(ivNrCalls);
            pa.setNrMissedCalls(ivNrMissedCalls);

            if(!StringUtils.isEmpty(changedBy))
                pa.setChangedBy(changedBy);

            pa.setDtChanged(new Timestamp(System.currentTimeMillis()));
            paRepository.save(pa);
        } catch (Exception ex) {
            throw new ProgramaAvisosException("Error saving pa ", ex);
        }
    }


    private Integer calculateIdStatus(ProgramaAvisos pa, String revisionSchedule,String revisionScheduleMotive,
                                      String removedObs, String changedBy) {

        Integer ivIdStatus = null;

        if (State.PENDING_DESC.equalsIgnoreCase(revisionSchedule))
            ivIdStatus = State.PENDING;
        else if ("S".equalsIgnoreCase(pa.getSuccessContact()) && State.HAS_SCHEDULE_DESC.equalsIgnoreCase(pa.getSuccessMotive()))
            ivIdStatus = State.HAS_SCHEDULE;
        else if (State.SCHEDULE_DONE_DESC.equalsIgnoreCase(revisionSchedule))
            ivIdStatus = State.SCHEDULE_DONE;
        else if (State.SCHEDULE_REJECTED_DESC.equalsIgnoreCase(revisionSchedule))
            ivIdStatus = State.SCHEDULE_REJECTED;
        else if (PAInfo.RSM_NOT_OWNER.equalsIgnoreCase(revisionScheduleMotive) || PAInfo.RSM_NOT_OWNER2.equalsIgnoreCase(revisionScheduleMotive))
            ivIdStatus = State.NOT_OWNER;
        else if ("S".equalsIgnoreCase(pa.getSuccessContact()) && State.AST_CONTACTS_CLIENT_DESC.equalsIgnoreCase(pa.getSuccessMotive()))
            ivIdStatus = State.AST_CONTACTS_CLIENT;
        else if ("S".equalsIgnoreCase(pa.getSuccessContact()) && State.CLIENT_SCHEDULED_AT_WORKSHOP_DESC.equalsIgnoreCase(pa.getSuccessMotive()))
            ivIdStatus = State.CLIENT_SCHEDULED_AT_WORKSHOP;
        else if (State.UNAVAILABLE_FOR_CONTACT_DESC.equalsIgnoreCase(revisionSchedule))
            ivIdStatus = State.UNAVAILABLE_FOR_CONTACT;

        else if ("N".equalsIgnoreCase(pa.getSuccessContact()) && "".equals(pa.getSuccessMotive()) && "".equals(pa.getRevisionSchedule()))
            ivIdStatus = State.PENDING;

        else if (State.REMOVED_MANUALLY_DESC.equalsIgnoreCase(revisionSchedule) && !PAInfo.DAEMON.equalsIgnoreCase(changedBy))
            ivIdStatus = State.REMOVED_MANUALLY;
        else if (State.REMOVED_MANUALLY_DESC.equalsIgnoreCase(revisionSchedule) && State.REMOVED_AUTO_BYMANUT_DESC.equalsIgnoreCase(removedObs) && PAInfo.DAEMON.equalsIgnoreCase(changedBy))
            ivIdStatus = State.REMOVED_AUTO_BYMANUT;
        else if (State.REMOVED_MANUALLY_DESC.equalsIgnoreCase(revisionSchedule) && State.REMOVED_AUTO_BYPERIOD_DESC.equalsIgnoreCase(removedObs) && PAInfo.DAEMON.equalsIgnoreCase(changedBy))
            ivIdStatus = State.REMOVED_AUTO_BYPERIOD;

        return ivIdStatus;
    }

    private void setEmptyFields(ProgramaAvisos pa) {
        pa.setCp4(StringTasks.cleanString(pa.getCp4(), ""));
        pa.setCp3(StringTasks.cleanString(pa.getCp3(), ""));
        pa.setCpext(StringTasks.cleanString(pa.getCpext(), ""));
        pa.setContactPhone(StringTasks.cleanString(pa.getContactPhone(), ""));
        pa.setEmail(StringTasks.cleanString(pa.getEmail(), ""));
        pa.setCp4(StringTasks.cleanString(pa.getCp4(), ""));


        pa.setNewNif(StringTasks.cleanString(pa.getNewNif(), ""));
        pa.setNewName(StringTasks.cleanString(pa.getNewName(), ""));
        pa.setNewAddress(StringTasks.cleanString(pa.getNewAddress(), ""));
        pa.setNewAddressNumber(StringTasks.cleanString(pa.getNewAddressNumber(), ""));
        pa.setNewFloor(StringTasks.cleanString(pa.getNewFloor(), ""));
        pa.setNewCp4(StringTasks.cleanString(pa.getNewCp4(), ""));
        pa.setNewCpExt(StringTasks.cleanString(pa.getNewCpExt(), ""));
        pa.setNewContactPhone(StringTasks.cleanString(pa.getNewContactPhone(), ""));
        pa.setNewEmail(StringTasks.cleanString(pa.getNewEmail(), ""));

        pa.setReceiveInformation(StringTasks.cleanString(pa.getReceiveInformation(), ""));
        pa.setObservations(StringTasks.cleanString(pa.getObservations(), ""));
        pa.setDelegatedTo(StringTasks.cleanString(pa.getDelegatedTo(), ""));
        pa.setClient(StringTasks.cleanString(pa.getClient(), ""));
        pa.setBlockedBy(StringTasks.cleanString(pa.getBlockedBy(), ""));
    }

    public String uploadFileGesDoc(String brand, String plate, String vin, File file, String gesDocLocation, String documentType) throws SCErrorException {
        String documentId = null;
        boolean visibleToDealer = true;

        GesDocVehicleDossierInvoke oGesDocVehicleDossierInvoke = new GesDocVehicleDossierInvoke(gesDocLocation);
        String token = AEScrypterTasks.encrypt(AEScrypterTasks.APP_VEHICLE_DOSSIER, getSystemUser(brand));
        GesDocUpload gesDocUpload = oGesDocVehicleDossierInvoke.uploadToVehicleDossier(AEScrypterTasks.APP_VEHICLE_DOSSIER, token, getSystemUser(brand), documentType, brand, plate, vin, visibleToDealer, file);
        documentId = gesDocUpload.getDocumentPair().get(0).getDocumentId();
        log.debug("DocumentId: " + documentId);

        return documentId;
    }

    public static String getSystemUser(String brand) {
        if (brand.equalsIgnoreCase("T") || brand.equalsIgnoreCase("L")) {
            return "rigor.master@tpo";
        }
        return "";
    }

    public static String[] getNewslettersFields(int idContactType, String brand) {
        // Por defeito define vers�o 1
        String requiredFields = "", personalFields = "";

        if(brand.equalsIgnoreCase(BRAND_TOYOTA)){

            switch (idContactType) {
                case ContactTypeB.MAN:
                    requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;fontSize;price;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name;footernextrevisiondesc";
                    personalFields = "email;urlimages;linkheader1;imgheader1;isToShowLastRevisionDate;isToShowNextRevisionDate;model;plate;lastrevisiondate;nextrevisiondate;fontSize;price;name;linkservice1;imgservice1;nameservice1;descservice1;linkservice2;imgservice2;nameservice2;descservice2;linkservice3;imgservice3;nameservice3;descservice3;linkgeneratedfromosb;nameaccessory1;descaccessory1;linkaccessory1;imgaccessory1;nameaccessory2;descaccessory2;linkaccessory2;imgaccessory2;linkhighlight1;imghighlight1;footernextrevisiondesc";
                    /*
                     * retirado nextitvdate (depois do price e antes do name) pois n�o foi encontrado no HTML da tabela:
                     * dblists TEMPLATES, nos ids:
                     * 	25	TPA BO Revis�o Toyota sem publicidade
                     *  19	TPA BO Revis�o Toyota
                     *
                     *  foi retirado tamb�m da coluna PERSONAL_FIELDS dos dois templates
                     *
                     * */
                    break;
                case ContactTypeB.ITV:
                    requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;nextitvdate;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name;footernextrevisiondesc";
                    personalFields = "email;urlimages;linkheader1;imgheader1;isToShowLastRevisionDate;isToShowNextRevisionDate;model;plate;lastrevisiondate;nextrevisiondate;fontSize;price;nextitvdate;name;linkservice1;imgservice1;nameservice1;descservice1;linkservice2;imgservice2;nameservice2;descservice2;linkservice3;imgservice3;nameservice3;descservice3;linkgeneratedfromosb;nameaccessory1;descaccessory1;linkaccessory1;imgaccessory1;nameaccessory2;descaccessory2;linkaccessory2;imgaccessory2;linkhighlight1;imghighlight1;footernextrevisiondesc";
                    break;
                case ContactTypeB.MAN_ITV:
                    requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;fontSize;price;nextitvdate;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name";
                    personalFields = "email;urlimages;linkheader1;imgheader1;isToShowLastRevisionDate;isToShowNextRevisionDate;model;plate;lastrevisiondate;nextrevisiondate;fontSize;price;nextitvdate;name;linkservice1;imgservice1;nameservice1;descservice1;linkservice2;imgservice2;nameservice2;descservice2;linkservice3;imgservice3;nameservice3;descservice3;linkgeneratedfromosb;nameaccessory1;descaccessory1;linkaccessory1;imgaccessory1;nameaccessory2;descaccessory2;linkaccessory2;imgaccessory2;linkhighlight1;imghighlight1;footernextrevisiondesc";
                    break;

                default:
                    break;
            }
        }else if(brand.equalsIgnoreCase(BRAND_LEXUS)){

            switch (idContactType) {
                case ContactTypeB.MAN:
                    requiredFields = "urlimages;linkheader1;imgheader1;model;plate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
                    personalFields = PERSONAL_FIELDS;
                    break;
                case ContactTypeB.ITV:
                    requiredFields = "urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
                    personalFields = PERSONAL_FIELDS;
                    break;
                case ContactTypeB.MAN_ITV:
                    requiredFields = "urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
                    personalFields = "email;urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;linkservice1;imgservice1;nameservice1;descservice1;linkservice2;imgservice2;nameservice2;descservice2;linkservice3;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;linkaccessory1;imgaccessory1;nameaccessory2;descaccessory2;linkaccessory2;imgaccessory2;linkhighlight1;imghighlight1";
                    break;

                default:
                    break;
            }
        }

        return new String[] { requiredFields, personalFields };
    }
}
