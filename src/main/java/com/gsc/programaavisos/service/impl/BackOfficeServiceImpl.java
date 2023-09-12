package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.State;
import com.gsc.programaavisos.dto.PACampaing;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.TechnicalCampaigns;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.PaDataInfoRepository;
import com.gsc.programaavisos.repository.crm.TechnicalCampaignsRepository;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.BackOfficeService;
import com.gsc.programaavisos.service.impl.pa.ExcelUtils;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import com.gsc.programaavisos.util.PAUtil;
import com.rg.dealer.Dealer;
import com.sc.commons.comunications.Mail;
import com.sc.commons.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import static com.gsc.programaavisos.constants.ApiConstants.PRODUCTION_SERVER_STR;


@Log4j
@RequiredArgsConstructor
@Service
public class BackOfficeServiceImpl implements BackOfficeService {

    private final PaDataInfoRepository dataInfoRepository;
    private final ExcelUtils excelUtils;

    public static final int NUMBER_OF_REGISTERS_FOR_ROW = 25;
    public static final String MAIL_ADDRESS_EXTRANET_TOYOTA = "extranettoyota@toyotacaetano.pt";
    public static final String CSV_SEPARATOR = ";";
    private final ProgramaAvisosUtil paUtils;
    private final TechnicalCampaignsRepository tcRepository;
    private final Environment env;


    @Override
    public void importTechnicalCampaign(UserPrincipal userPrincipal, MultipartFile file) {
        XSSFSheet lstFields = null;
        try{

            if (file == null)
                throw new ProgramaAvisosException("The file is null, won't be loaded");
            log.trace("importTechnicalCampaing: Starting reading excel file");
            InputStream fileContent = file.getInputStream();
            lstFields = readTechnicalCampaignFile(fileContent);
            log.trace(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.trace("importTechnicalCampaing: start import");
            log.trace(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            parseAndUpdate(lstFields, userPrincipal, file);

        } catch (Exception e) {
            throw new ProgramaAvisosException("Error when importing Technical Campaigns from Excel file ", e);
        }
    }

    public void parseAndUpdate(XSSFSheet lstFields, UserPrincipal userPrincipal, MultipartFile contentFile) throws Exception {

        String[] activeProfiles = env.getActiveProfiles();
        Boolean isProd = Arrays.asList(activeProfiles).contains(PRODUCTION_SERVER_STR);

        List<PACampaing> lstProgramaAvisos = new ArrayList<>();

        List<Dealer> vecAllAfterSalesDealers = new ArrayList<>();

        StringBuilder bodyOfMail = new StringBuilder(StringUtils.EMPTY);
        StringBuilder errorCSV = new StringBuilder(StringUtils.EMPTY);

        File errorFile = convertToFile(contentFile);

        // RE-195442
        List<PaDataInfo> paDataInfo = dataInfoRepository.geProgramaAvisosByContactTypeAndStatus(ContactTypeB.TECHNICAL_CAMPAIGN, State.PENDING);
        Map<String, PaDataInfo> mapPATechnicalCampaignOpen = this.resultToMap(paDataInfo);

        Map<String, Dealer> mapMainDealersToyota = Dealer.getToyotaHelper().getDealerCodeForAllActiveDealers();
        Map<String, Dealer> mapMainDealersLexus = Dealer.getLexusHelper().getDealerCodeForAllActiveDealers();
        Map<String, Dealer> mapAfterSalesDealers = new HashMap<>();

        vecAllAfterSalesDealers.addAll(Dealer.getLexusHelper().getAllActiveDealerAftersales());
        vecAllAfterSalesDealers.addAll(Dealer.getToyotaHelper().getAllActiveDealerAftersales());
        for (Dealer dealer : vecAllAfterSalesDealers) {
            mapAfterSalesDealers.put(dealer.getDealerCode() + "-" + dealer.getAfterSalesCode(), dealer);
        }

        log.debug(">>>>> Beginning of data processing of the excel file");

        int rowcount = lstFields.getLastRowNum();
        XSSFRow lstFieldsRow;

        for (int i = 2, nrow = 3; i <= rowcount; i++, nrow++) {
            lstFieldsRow = lstFields.getRow(i);
            PACampaing oProgramaAvisos = excelUtils.putInformationInObjects(lstFieldsRow,
                    DateTimerTasks.getCurYear(), DateTimerTasks.getCurMonth(), nrow, mapMainDealersToyota,
                    mapMainDealersLexus, mapAfterSalesDealers, mapPATechnicalCampaignOpen);
            if (oProgramaAvisos!=null)
                lstProgramaAvisos.add(oProgramaAvisos);
        }

        log.debug(">>>>> In�cio do registo em BD dos dados do ficheiro excel");

        int numberOfRegistersImported = confirmTechnicalCampaing(lstProgramaAvisos, userPrincipal.getUserStamp());
        boolean withErrors = false;
        if(lstProgramaAvisos.size() > 0) {
            bodyOfMail.append("Foram processados " + numberOfRegistersImported + " de um total de " + lstProgramaAvisos.size() + " registos.\n");

            for (PACampaing oProgramaAvisos : lstProgramaAvisos) {
                if(!withErrors) {
                    errorCSV.append("Linha"+CSV_SEPARATOR+"Descri��o"+"\n");
                }

                if(!oProgramaAvisos.getTc().getStErrorImport().toString().trim().equals("")) {
                    String[] text = oProgramaAvisos.getTc().getStErrorImport().toString().split(":");
                    errorCSV.append((text!=null && text.length==2 ? text[0] : "")+CSV_SEPARATOR+(text!=null && text.length==2 ? text[1] : "")+"\n");
                }

                withErrors = true;
            }

            if(withErrors) {
                bodyOfMail.append("Segue em anexo um ficheiro .csv com os erros da importa��o.\n");
            }
        }

        String subject = "Programa de Avisos - Importa��o Ficheiro Campanhas T�cnicas";
        String from = MAIL_ADDRESS_EXTRANET_TOYOTA;
        String to = isProd ? userPrincipal.getEmail() : "telmo.sousa@rigorcg.pt";
        String bcc = isProd ? Mail.MAIL_ADDRESS_WEBNOFITICATIONS : "";
        String message = bodyOfMail.toString() + Mail.getFooterNoReply();

        Mail.SendMail(from, to, "", bcc, subject, message, errorFile);
    }

    public Map<String, PaDataInfo> resultToMap(List<PaDataInfo> paDataInfoList) {
        Map<String, PaDataInfo> mapProgramaAvisosTC = new HashMap<>();

        for (PaDataInfo oProgramaAvisosBean: paDataInfoList) {
            ProgramaAvisosServiceImpl.fillPABean(oProgramaAvisosBean);
            String keyPaTechincalCampign = PAUtil.getKeyByListElements(Arrays.asList(oProgramaAvisosBean.getPaLicensePlate(),
                    oProgramaAvisosBean.getTcCampaign(),
                   oProgramaAvisosBean.getTcGenerationDate().toString()));
                    //DateTimerTasks.fmtDT2.format(oProgramaAvisosBean.getTcGenerationDate())));

            if(!mapProgramaAvisosTC.containsKey(keyPaTechincalCampign))
                mapProgramaAvisosTC.put(keyPaTechincalCampign, oProgramaAvisosBean);
        }

        return mapProgramaAvisosTC;
    }



    public static XSSFSheet readTechnicalCampaignFile(InputStream ivFileContent) {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(ivFileContent);
            XSSFSheet spreadsheet = (XSSFSheet) workbook.getRelationById("rId3");

            return spreadsheet;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error reading file", e);
        }
    }

    public int confirmTechnicalCampaing(List<PACampaing> lstProgramaAvisos, String userStamp) {
        int numberOfRegistersImported = 0;
        try {
            for(int i = 0 ; i < lstProgramaAvisos.size(); i++){
                TechnicalCampaigns oTechnicalCampaigns = lstProgramaAvisos.get(i).getTc();

                if(oTechnicalCampaigns.getStErrorImport().length()==0) {
                    ProgramaAvisos oProgramaAvisos = paUtils.insert(lstProgramaAvisos.get(i).getPa(), false, userStamp);
                    oTechnicalCampaigns.setIdPaData(oProgramaAvisos.getId());
                    oTechnicalCampaigns.setCreatedBy(userStamp);
                    oTechnicalCampaigns.setDtCreated(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
                    tcRepository.save(oTechnicalCampaigns);
                    numberOfRegistersImported++;
                }
            }

        } catch (Exception e) {
            throw new ProgramaAvisosException("confirmTechnicalCampaing ",  e);
        }

        return numberOfRegistersImported;
    }


    public File convertToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

}
