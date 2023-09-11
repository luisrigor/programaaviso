package com.gsc.programaavisos.service.impl.pa;

import com.gsc.consent.core.objects.SubscriptionCenterResponse;
import com.gsc.consent.invoke.ConsentCenterInvoke;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.State;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.CcRigorServiceRepository;
import com.gsc.programaavisos.repository.crm.EntityRealRepository;
import com.gsc.programaavisos.repository.crm.PaDataInfoRepository;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.ws.core.CarInfo;
import com.gsc.ws.core.objects.response.CarInfoResponse;
import com.gsc.ws.invoke.WsInvokeCarServiceTCAP;
import com.rg.dealer.Dealer;
import com.sc.commons.comunications.Mail;
import com.sc.commons.comunications.Sms;
import com.sc.commons.utils.ServerTasks;
import com.sc.commons.utils.StringTasks;
import com.sc.commons.validations.Validate;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static com.gsc.programaavisos.config.environment.MapProfileVariables.CONST_AS400_WEBSERVICE_ADDRESS;
import static com.gsc.programaavisos.config.environment.MapProfileVariables.CONST_CONSENT_CENTER_URL;
import static com.gsc.programaavisos.constants.ApiConstants.PRODUCTION_SERVER_STR;
import static com.gsc.programaavisos.constants.PaConstants.*;
import static com.gsc.programaavisos.constants.PaConstants.ERRO_LER_FICHEIRO;
import static com.gsc.programaavisos.constants.State.PENDING;

@Log4j
@Component
public class MapUpdateUtil {

    private final EntityRealRepository entityRealRepository;
    private final CcRigorServiceRepository ccRepository;
    private final PaDataInfoRepository dataInfoRepository;
    private final EnvironmentConfig environmentConfig;
    private final ProgramaAvisosUtil paUitl;
    private final Environment env;


    @Autowired
    public MapUpdateUtil(EntityRealRepository entityRealRepository, CcRigorServiceRepository ccRepository, PaDataInfoRepository dataInfoRepository, EnvironmentConfig environmentConfig, ProgramaAvisosUtil paUitl, Environment env) {
        this.entityRealRepository = entityRealRepository;
        this.ccRepository = ccRepository;
        this.dataInfoRepository = dataInfoRepository;
        this.environmentConfig = environmentConfig;
        this.paUitl = paUitl;
        this.env = env;
    }

    public void plateToSms(UserPrincipal oGSCUser, MultipartFile fileContent) {

        String[] activeProfiles = env.getActiveProfiles();
        Boolean isProd = Arrays.asList(activeProfiles).contains(PRODUCTION_SERVER_STR);

        InputStream fis;
        try {
            fis = fileContent.getInputStream();
        } catch (IOException e) {
            sendMail(oGSCUser, null, ERRO_LER_FICHEIRO, isProd);
            throw new ProgramaAvisosException("Error reading file ", e);
        }

        int line = 1;
        Calendar calSendSMS = Calendar.getInstance();

        List<CcRigorService> dealersWorkedByContactCenterRigor = ccRepository.getDealersWorkedByContactCenterRigor(
                calSendSMS.get(Calendar.YEAR), calSendSMS.get(Calendar.MONTH) + 1,
                ContactTypeB.COMMERCIAL_CAMPAIGN_APV_MAP);

        Map<String, CcRigorService> hashDealersWorkedByContactCenterRigor = dealersWorkedToMap(dealersWorkedByContactCenterRigor);

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Ignore first line (Header)
            if (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell3 = row.createCell(2); // Third column
                cell3.setCellValue("Resultado");
            }

            // Iterator for next lines
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                line++;
                Cell cell1 = row.getCell(0); // Primeira coluna
                Cell cell2 = row.getCell(1); // Segunda coluna

                if (cell1 != null && cell2 != null) {
                    CarInfo oCarInfo = null;
                    String plate = cell1.getStringCellValue();
                    String text = cell2.getStringCellValue();

                    try {
                        WsInvokeCarServiceTCAP oWsInfo = new WsInvokeCarServiceTCAP(environmentConfig.getEnvVariables().get(CONST_AS400_WEBSERVICE_ADDRESS));
                        CarInfoResponse oCarInfoResponse = oWsInfo.getCarByPlate(plate);

                        if (oCarInfoResponse != null && oCarInfoResponse.getCarInfo() != null) {
                            oCarInfo = oCarInfoResponse.getCarInfo();
                        } else {
                            throw new ProgramaAvisosException("WS_INFO.getCarByPlate Não foi possível carregar o objecto WS_INFO.getCarByPlate");
                        }
                    } catch (Exception e) {
                        Cell cell3 = row.createCell(2); // Terceira coluna
                        cell3.setCellValue(ERRO_MATRICULA);
                        log.info("Não existe informação sobre a matricula apresentada, dados insuficientes na linha "+ line);
                        continue;
                    }

                    // The logic is to retrieve the client first by user and
                    // then by owner. If they don't exist, do not send an
                    // SMS.
                    String carInfoPLate = oCarInfo.getPlate();
                    carInfoPLate = StringTasks.ReplaceStr(carInfoPLate, "-", "").toUpperCase();
                    EntityReal client = entityRealRepository.getVehicleUser(carInfoPLate);

                    if (client == null) {
                        client = entityRealRepository.getVehicleOwner(carInfoPLate);
                    }
                    if (client == null) {
                        Cell cell3 = row.createCell(2);
                        cell3.setCellValue(ERRO_USER);// Result
                        log.info("Não existe informação sobre utilizador/proprietario, dados insuficientes na linha " + line);
                        continue;
                    }

                    // destination number logic
                    String destNr = consentPriorityMobileNumber(client.getPhone1(), client.getPhone2(),
                            client.getPhone3(), oCarInfo.getBrand());
                    if (destNr.equals("")) {
                        Cell cell3 = row.createCell(2);
                        cell3.setCellValue(ERRO_MOBILE);// Result
                        log.info("Não existe Contacto telefonico sobre utilizador/proprietario,dados insuficientes na linha " + line);
                        continue;
                    }

                    log.debug("Contact: " + destNr);
                    log.debug("Plate: " + plate);
                    log.debug("Message: " + text);

                    log.trace("Processar SMS para o Cliente (Normal) com NIF:" + client.getNif());
                    String sSmsOrigin = oCarInfo.getBrand().toUpperCase();
                    String sSmsEntity = "0-DAV-MKT-T";

                    destNr = (destNr.startsWith("+") || destNr.startsWith("00")) ? destNr : "+351" + destNr;

                    /*
                     * We can always do Sms.SendSMS
                     * The "daemon" present on the SMS project only sends sms to the operator on prod env
                     * */
                    long smsResult = Sms.SendSMS(sSmsOrigin, destNr, text, 0, 10, sSmsEntity, "", "",
                            "Programa de Avisos", "Programa de Avisos",
                            new Timestamp(calSendSMS.getTimeInMillis()));


                    if(!isProd){
                        /*
                         * On env != prod, send email with a representation of each SMS send
                         */
                        sendMail(oGSCUser, null,
                                "Este email � uma representa��o da mensagem que seria enviada via SMS para o cliente com a matricula "+plate+". Em ambiente de testes n�o enviamos SMS.<br>Mensagem: "+text,
                                isProd);
                    }

                    if (smsResult == 0) {
                        Cell cell3 = row.createCell(2);
                        cell3.setCellValue(ERRO_SMS);// Result
                        log.info("Ocorreu um erro a enviar o SMS para o cliente: " + client.getNif());
                        continue;
                    }

                    // Create and insert TPA
                    ProgramaAvisos oProgramaAvisos = new ProgramaAvisos();

                    oProgramaAvisos.setIdSource(DMV_APV);
                    oProgramaAvisos.setIdChannel(SMS);
                    oProgramaAvisos.setIdContactType(ContactTypeB.COMMERCIAL_CAMPAIGN_APV_MAP);
                    oProgramaAvisos.setIdClientType(NORMAL_ID);
                    oProgramaAvisos.setIdClientOrigin(CRM_ID);
                    String oiDealer = getOidDealer(client, oCarInfo);

                    if (StringUtils.isEmpty(oiDealer)) {
                        Cell cell3 = row.createCell(2);
                        cell3.setCellValue(ERRO_PREFERRED_DEALER);// Result
                        log.info("Não existe Preferred dealer sobre utilizador/proprietario,dados insuficientes na linha"+ line);
                        continue;
                    }

                    fillTPA(oProgramaAvisos, client ,calSendSMS, oCarInfo, destNr, oiDealer);

                    boolean isDealersWorkedByContactCenterRigor = hashDealersWorkedByContactCenterRigor.containsKey(oProgramaAvisos.getOidDealer());
                    oProgramaAvisos = paUitl.insert(oProgramaAvisos,isDealersWorkedByContactCenterRigor, "PROGRAMA DE AVISOS");
                    log.trace("PA_DATA gravado com sucesso (ID" + oProgramaAvisos.getId() + ")");

                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(SUCESSO);//Result
                    log.debug("SMS e TPA com sucesso na linha: " + line);

                } else {
                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(ERRO_CELULAS);//Result
                    log.info("celulas do excel sem dados suficientes na linha" + line);
                }
            }


            if (line == 1) {
                sendMail(oGSCUser, null, FICHEIRO_VAZIO, isProd);
            } else {
                sendMail(oGSCUser, fileContent, SUCESSO_TOTAL, isProd);
            }
        } catch (Exception ex) {
            sendMail(oGSCUser, null, ERRO_LER_FICHEIRO, isProd);
            throw new ProgramaAvisosException("Error updating map");
        }
    }

    private void sendMail(UserPrincipal user, MultipartFile fileContent, String mailBody, Boolean isProd) {
        try {
            File file = null;
            if ( fileContent!=null)
                file = convertToFile(fileContent);

            String subject = (isProd ? "" : "[STAGING] ") + "Atualiza��o de Mapas";
            String mailBcc = isProd ? "" : "joel.brandao@parceiro.rigorcg.pt,luciano.teixeira@rigorcg.pt";
            if(ServerTasks.getServerType() == ServerTasks.STAGING_SERVER){
                //TODO: disponibiliza��o para aceita��o, depois em GO2PROD, retirar
                mailBcc = mailBcc + ",ricardo.dinis@toyotacaetano.pt,nuno.araujo@toyotacaetano.pt,paulo.cunha@toyotacaetano.pt,jose.oliveira@rigorcg.pt,ricardo.monteiro@toyotacaetano.pt";
            }

            String mailDestination = user.getEmail();

            try {
                mailBody += "<br><br>" + Mail.getMailSignatureTCAP();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, File> images = Mail.getMailSignatureTCAPEmbeddedImg();
            if (file != null) {
                Mail.SendMailHTML(Mail.MAIL_ADDRESS_DO_NOT_REPLY, mailDestination, "", mailBcc, subject, mailBody, file,
                        images);
            } else {
                Mail.SendMailHTML(Mail.MAIL_ADDRESS_DO_NOT_REPLY, mailDestination, "", mailBcc, subject, mailBody, images);
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error sending mail ", e);
        }
    }

    private String consentPriorityMobileNumber(String phone1, String phone2, String phone3, String brand) {
        boolean goodPhone1 = Validate.PTMobile(phone1);
        boolean goodPhone2 = Validate.PTMobile(phone2);
        boolean goodPhone3 = Validate.PTMobile(phone3);

        if (goodPhone1 && validateRGPD(brand, phone1)) {
            return phone1;
        } else if (goodPhone2 && validateRGPD(brand, phone2)) {
            return phone2;
        } else if (goodPhone3 && validateRGPD(brand, phone3)) {
            return phone3;
        } else if (goodPhone1) {
            return phone1;
        } else if (goodPhone2) {
            return phone2;
        } else if (goodPhone3) {
            return phone3;
        }

        return "";
    }

    public boolean validateRGPD(String brand, String phone) {

        brand.toUpperCase();
        String contactPhone = phone;
        if ("".equals(StringTasks.cleanString(phone, "")))
            return false;
        int idClientSC = brand.contains("TOYOTA") ? com.gsc.consent.util.DATA.CLIENT_TCAP
                : com.gsc.consent.util.DATA.CLIENT_TCAP_LEXUS;
        ConsentCenterInvoke WS_CONSENT_CENTER = new ConsentCenterInvoke(environmentConfig.getEnvVariables().get(CONST_CONSENT_CENTER_URL), false);

        SubscriptionCenterResponse oSubscriptionCenterResponse = WS_CONSENT_CENTER.validateConsent(
                com.gsc.consent.util.DATA.HASH_TOKEN_BACKOFFICE, idClientSC, com.gsc.consent.util.DATA.CONTACT_TYPE_MKT,
                com.gsc.consent.util.DATA.CHANNEL_SMS, contactPhone);
        if (oSubscriptionCenterResponse == null)
            return false;

        return oSubscriptionCenterResponse.getReturnMessage().getCode() == 0
                && "S".equalsIgnoreCase(oSubscriptionCenterResponse.getConsents().get(0).getAuthorize());
    }

    private String getOidDealer(EntityReal client, CarInfo oCarInfo) {

        String preferredDealer = client.getOidPreferedDealer();

        // Check if the user has no preferred dealer (and use backup dealer instead)
        if (StringUtils.isEmpty(preferredDealer)) {
            PaDataInfo programa = dataInfoRepository.getLastPaDataForPlate(oCarInfo.getPlate());
            try {
                if (programa != null && !StringUtils.isEmpty(programa.getPaOidDealer())) {
                    // Get dealer from last maintenance
                    preferredDealer = programa.getPaOidDealer();
                } else {
                    // If that's null too, get original dealer that the vehicle was purchased from.
                    Dealer oDealer = Dealer.getToyotaHelper().getByDealerCode(oCarInfo.getDealerCode());
                    if (oDealer == null) {
                        oDealer = Dealer.getLexusHelper().getByDealerCode(oCarInfo.getDealerCode());
                    }
                    if (oDealer != null) {
                        preferredDealer = oDealer.getOid_Parent();
                    }
                }
            } catch (Exception e) {
                log.info("Não foi possivel obter o preferred Dealer ", e);
            }
        }

        return preferredDealer;
    }

    public void fillTPA(ProgramaAvisos oProgramaAvisos, EntityReal client, Calendar calSendSMS, CarInfo oCarInfo,
                        String destNr, String oiDealer) {
        oProgramaAvisos.setOidDealer(oiDealer);
        oProgramaAvisos.setIdStatus(PENDING);
        oProgramaAvisos.setRevisionSchedule(State.PENDING_DESC);

        oProgramaAvisos.setYear(calSendSMS.get(Calendar.YEAR));
        oProgramaAvisos.setMonth(calSendSMS.get(Calendar.MONTH) + 1);
        oProgramaAvisos.setDay(calSendSMS.get(Calendar.DAY_OF_MONTH));

        oProgramaAvisos.setLicensePlate(oCarInfo.getPlate());
        oProgramaAvisos.setVin(oCarInfo.getVin());
        oProgramaAvisos.setBrand(
                oCarInfo != null && oCarInfo.getBrand() != null && !oCarInfo.getBrand().isEmpty()
                        ? String.valueOf(oCarInfo.getBrand().charAt(0)) : "");
        oProgramaAvisos.setModel(oCarInfo.getComercialModelDesig());
        oProgramaAvisos.setName(client.getName());
        oProgramaAvisos.setAddress(client.getAddress());
        oProgramaAvisos.setNif(client.getNif());
        oProgramaAvisos.setCp4(client.getCp4());
        oProgramaAvisos.setCp3(client.getCp3());
        oProgramaAvisos.setCpext(client.getCpext());
        oProgramaAvisos.setContactPhone(destNr);
        oProgramaAvisos.setEmail(client.getEmail());

        oProgramaAvisos.setVisible("S");
        oProgramaAvisos.setDtVisible(new Date(Calendar.getInstance().getTime().getTime()));
    }

    public Map<String, CcRigorService> dealersWorkedToMap(List<CcRigorService> dealersWorkedByContactCenterRigor) {
        Map<String, CcRigorService> mapDealers = new HashMap<>();
        for (CcRigorService currentCC: dealersWorkedByContactCenterRigor) {
            mapDealers.put(currentCC.getOidDealer(), currentCC);
        }

        return mapDealers;
    }

    public File convertToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
