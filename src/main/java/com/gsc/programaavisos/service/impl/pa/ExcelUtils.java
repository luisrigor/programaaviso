package com.gsc.programaavisos.service.impl.pa;

import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.State;
import com.gsc.programaavisos.dto.PACampaing;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.TechnicalCampaigns;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.util.PAUtil;
import com.gsc.ws.core.CarInfo;
import com.gsc.ws.core.objects.response.CarInfoResponse;
import com.gsc.ws.invoke.WsInvokeCarServiceTCAP;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DateTimerTasks;
import com.sc.commons.utils.StringTasks;
import lombok.extern.log4j.Log4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

import static com.gsc.programaavisos.config.environment.MapProfileVariables.CONST_AS400_WEBSERVICE_ADDRESS;
import static com.gsc.programaavisos.constants.PaConstants.*;

@Log4j
@Component
public class ExcelUtils {

    private static final int platformCodePosition		= 0;	//A
    //	private static int platformPosition			= 1;	//B
    private static final int campaignPosition			= 2;	//C
    private static final int vinPosition				= 3;	//D
    private static final int licensePlatePosition		= 4;	//E
    private static final int cLicensePlatePosition	= 5;	//F
    private static final int sendDatePosition			= 6;	//G
    private static final int agePosition				= 7;	//H
    private static final int gammaPosition			= 8;	//I
    private static final int technicalModelPosition	= 9;	//J
    private static final int salesDatePosition		= 10;	//K
    private static final int contactSourcePosition	= 11;	//L
    private static final int namePosition				= 12;	//M
    private static final int addressPosition			= 13;	//N
    private static final int zipCodePosition			= 14;	//O
    private static final int localPosition			= 15;	//P
    private static final int phone1Position			= 16;	//Q
    private static final int phone2Position			= 17;	//R
    private static final int phone3Position			= 18;	//S
    private static final int emailPosition			= 19;	//T
    private static final int nifPosition				= 20;	//U
    private static final int companyPosition			= 21;	//V
    private static final int excludePosition			= 22;	//W
    private static final int generationDatePosition	= 23; 	//X
    private static final int sendDate2Position		= 24; 	//Y

    private static String[] fieldsTechnicalCampaign = {
            "COD. PLATAFORMA","PLATAFORMA","CAMPANHA","VIN","MATR�CULA","MAT COD","VIN_V�LIDO","IDADE","GAMA","MODELO_T�CNICO",
            "DATA_VENDA","FONTE_DO_CONTATO","NOME","MORADA","C�DIGO_POSTAL","LOCALIDADE","TELEFONE1","TELEFONE2","TELEFONE3","EMAIL",
            "NIF","EMPRESA","EXCLUIR","DATA_GERA��O","DATA 2 ENVIO"};

    private static int[] fieldsCheckExists = {campaignPosition,vinPosition,licensePlatePosition,cLicensePlatePosition,
            contactSourcePosition,namePosition,addressPosition,nifPosition,zipCodePosition,companyPosition};
    private final EnvironmentConfig environmentConfig;

    @Autowired
    public ExcelUtils(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    public PACampaing putInformationInObjects(XSSFRow lstFieldsRows, int year, int month, int currentColumn,
                                              Map<String, Dealer> hmAllActiveDealersToyota, Map<String, Dealer> hmAllActiveDealersLexus,
                                              Map<String, Dealer> mapAfterSalesDealers, Map<String, PaDataInfo> mapPATechnicalCampaignOpen) {

        List<String> lstFields = new ArrayList<>();
        for (int i = 0; i < lstFieldsRows.getLastCellNum(); i++) {
            String currentCell = lstFieldsRows.getCell(i) !=null ? lstFieldsRows.getCell(i).getRawValue(): "";
            lstFields.add(currentCell);
        }

        if (lstFields.isEmpty())
            return null;

        ProgramaAvisos oPA = new ProgramaAvisos();
        TechnicalCampaigns oTC = new TechnicalCampaigns();
        CarInfo oCarInfo = null;

        try {
            WsInvokeCarServiceTCAP oWsInfo = new WsInvokeCarServiceTCAP(environmentConfig.getEnvVariables().get(CONST_AS400_WEBSERVICE_ADDRESS));
            CarInfoResponse oCarInfoResponse = oWsInfo.getCarByPlate(lstFields.get(cLicensePlatePosition));

            if(oCarInfoResponse != null && oCarInfoResponse.getCarInfo() != null) {
                oCarInfo = oCarInfoResponse.getCarInfo();
            } else {
                throw new SCErrorException("WS_INFO.getCarByPlate", "N�o foi poss�vel carregar o objecto WS_INFO.getCarByPlate");
            }
        } catch (SCErrorException e) {
            oTC.setStErrorImport(new StringBuilder("Erro a obter informa��o da viatura"));
        }

        if(oTC.getStErrorImport() == null) {

            String platformCode = lstFields.get(platformCodePosition);

            Dealer oDealer = null;
            if(oCarInfo!=null && oCarInfo.getBrand()!=null && !oCarInfo.getBrand().equals("")){
                String brand = oCarInfo.getBrand().substring(0, 1).toUpperCase();
                if(platformCode.indexOf("-") == -1) {
                    if(brand.equals("T")){
                        oDealer = hmAllActiveDealersToyota.get(lstFields.get(platformCodePosition));
                    } else if(brand.equals("L")){
                        oDealer = hmAllActiveDealersLexus.get(lstFields.get(platformCodePosition));
                    }
                } else {
                    oDealer = mapAfterSalesDealers.get(platformCode);
                }

                //PA_DATA
                oPA.setIdSource(DMV_APV);
                oPA.setIdChannel(LETTER);
                oPA.setIdContactType(ContactTypeB.TECHNICAL_CAMPAIGN);
                oPA.setIdClientType(lstFields.get(companyPosition).equals("S") ? BUSINESS_PLUS_ID : NORMAL_ID);
                oPA.setIdClientOrigin(lstFields.get(11).trim().equals("AS400") ? AS400_ID : CRM_ID);
                oPA.setIdStatus(State.PENDING);
                oPA.setYear(year);
                oPA.setMonth(month);
                oPA.setOidDealer(oDealer!=null ? oDealer.getObjectId() : "");
                oPA.setLicensePlate(lstFields.get(cLicensePlatePosition));
                oPA.setVin(lstFields.get(vinPosition));
                oPA.setBrand(oCarInfo.getBrand().substring(0, 1));
                oPA.setModel(oCarInfo.getComercialModelDesig());
                oPA.setName(lstFields.get(namePosition));
                oPA.setAddress(lstFields.get(addressPosition));
                oPA.setNif(lstFields.get(nifPosition));
                oPA.setCp4(getZipCodeSplitted(lstFields.get(zipCodePosition),0));
                oPA.setCp3(getZipCodeSplitted(lstFields.get(zipCodePosition),1));
                oPA.setCpext(lstFields.get(localPosition));
                oPA.setContactPhone(!lstFields.get(phone1Position).equals("") ? lstFields.get(phone1Position) :  !lstFields.get(phone2Position).equals("") ? lstFields.get(phone2Position) : !lstFields.get(phone3Position).equals("") ? lstFields.get(phone3Position) : "");
                oPA.setEmail(lstFields.get(emailPosition));
                oPA.setVisible("S");
                oPA.setDtVisible(new Date(Calendar.getInstance().getTime().getTime()));

                //PA_TECHNICAL_CAMPAIGNS
                oTC.setAge(Integer.parseInt(lstFields.get(agePosition)));
                oTC.setCampaign(lstFields.get(campaignPosition));
                oTC.setContactSource(lstFields.get(contactSourcePosition));
                oTC.setExlude(lstFields.get(excludePosition));
                oTC.setGamma(lstFields.get(gammaPosition));
                oTC.setGenerationDate(StringTasks.cleanDateValidFormat(lstFields.get(generationDatePosition)) !=null ? StringTasks.cleanDateValidFormat(lstFields.get(generationDatePosition)) .toLocalDate():null );
                oTC.setPhone1(lstFields.get(phone1Position));
                oTC.setPhone2(lstFields.get(phone2Position));
                oTC.setPhone3(lstFields.get(phone3Position));
                oTC.setSalesDate(lstFields.get(salesDatePosition));
                oTC.setSendDate2(StringTasks.cleanDateValidFormat(lstFields.get(sendDate2Position))!=null ? StringTasks.cleanDateValidFormat(lstFields.get(sendDate2Position)).toLocalDate():null );
                oTC.setTechnicalModel(lstFields.get(technicalModelPosition));
                oTC.setSendDate(StringTasks.cleanDateValidFormat(lstFields.get(sendDatePosition))!=null ? StringTasks.cleanDateValidFormat(lstFields.get(sendDatePosition)).toLocalDate():null );
            }

            String keyPaTechincalCampign = PAUtil.getKeyByListElements(Arrays.asList(lstFields.get(cLicensePlatePosition), lstFields.get(campaignPosition), DateTimerTasks.fmtDT2.format(StringTasks.cleanDateValidFormat(lstFields.get(generationDatePosition)))));
            oTC.setStErrorImport(generateErrorField(lstFields, oDealer, oCarInfo, currentColumn, oPA, oTC, mapPATechnicalCampaignOpen.containsKey(keyPaTechincalCampign)));

        }

        return PACampaing.builder()
                .tc(oTC)
                .pa(oPA)
                .build();
    }

    private static String getZipCodeSplitted(String zipCode, int index){
        String[] zipCodeArray = zipCode.split("-");
        return zipCodeArray!=null && zipCodeArray.length==2 ? zipCodeArray[index] : "";
    }

    private static StringBuilder generateErrorField(List<String> lstFields, Dealer oDealer, CarInfo oCarInfo, int currentColumn, ProgramaAvisos oPA, TechnicalCampaigns oTC, boolean alreadyExists) {
        StringBuilder st = new StringBuilder();

        if(alreadyExists) {
            st.append("O registo j� existe. ");
            st.insert(0, ""+currentColumn+":");
            return st;
        }

        if(oDealer==null || oDealer.getObjectId().equals("")) { st.append("O campo "+fieldsTechnicalCampaign[platformCodePosition]+" � inv�lido. "); }

        if(oCarInfo==null || oCarInfo.getBrand().equals("") || oCarInfo.getComercialModelDesig().equals("")) { st.append("O campo "+fieldsTechnicalCampaign[cLicensePlatePosition]+" � inv�lido. "); }

        if((!lstFields.get(phone1Position).equals("") ? lstFields.get(phone1Position) :  !lstFields.get(phone2Position).equals("") ? lstFields.get(phone2Position) : !lstFields.get(phone3Position).equals("") ? lstFields.get(phone3Position) : "").equals("")){
            st.append("Os campos "+fieldsTechnicalCampaign[phone1Position]+"/"+fieldsTechnicalCampaign[phone2Position]+"/"+fieldsTechnicalCampaign[phone3Position]+" n�o est�o preenchidos. ");
        }

        for(int i = 0 ; i < fieldsCheckExists.length; i++){
            if(lstFields.get(fieldsCheckExists[i]).equals("")) { st.append("O campo "+fieldsTechnicalCampaign[fieldsCheckExists[i]]+" n�o est� preenchido. "); };
        }

        if(st.length()>0) { st.insert(0, ""+currentColumn+":");  }

        return st;
    }
}
