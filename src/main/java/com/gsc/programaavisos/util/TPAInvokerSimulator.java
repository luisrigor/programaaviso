package com.gsc.programaavisos.util;

import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.dto.TpaSimulation;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Acessories;
import com.gsc.programaavisos.model.cardb.entity.AcessorioGrupo;
import com.gsc.programaavisos.model.cardb.entity.Car;
import com.gsc.programaavisos.model.cardb.entity.CarInfo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.ws.core.AccessoryInstalled;
import com.gsc.ws.core.Repair;
import com.gsc.ws.core.objects.response.AccessoryInstalledResponse;
import com.gsc.ws.core.objects.response.CarInfoResponse;
import com.gsc.ws.core.objects.response.RepairResponse;
import com.gsc.ws.invoke.WsInvokeCarServiceTCAP;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.StringTasks;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TPAInvokerSimulator {

    public static final int CAR_DB_COMBUSTIVEL_SEM_INFO = 4;


    private static final String BRAND_TOYOTA = "T";
    private static final String BRAND_LEXUS = "L";

    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK = "https://www.toyota.pt/aposvenda/pecas-acessorios/index.json";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK = "#";


    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK_1 = "https://www.toyota.pt/aposvenda/pecas-acessorios/seguranca/femeas-de-seguranca";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK_1= "#";

    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK_2 = "https://www.toyota.pt/aposvenda/pecas-acessorios/loja-merchandising";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK_2= "#";

    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_1 = "toyota_acessory_default_img_postal_1.jpg";
    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_1 = "toyota_acessory_default_img_e_postal_1.jpg";

    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_2 = "toyota_acessory_default_img_postal_2.jpg";
    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_2 = "toyota_acessory_default_img_e_postal_2.jpg";

    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_2 = "lexus_acessory_default_img_postal_1.jpg";
    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_2 = "lexus_acessory_default_img_e_postal_1.jpg";;

    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_1 = "lexus_acessory_default_img_postal_2.jpg";
    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_1 = "lexus_acessory_default_img_e_postal_2.jpg";

    private static final String TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_1 = "As f�meas de seguran�a t�m um perfil arredondado e uma chave codificada, impedindo a remo��o das jantes com uma chave convencional.";
    private static final String LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_2 = "Seguran�a � um dos principais pilares na constru��o de um Lexus e, as f�meas de seguran�a s�o uma extens�o deste pilar. A sua constru��o e o seu design arredondado proporcionam n�o s� a robustez necess�ria mas tamb�m o encaixe perfeito para as jantes do seu Lexus. Um acess�rio excelente para a sua maior prote��o.";

    private static final String TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_2 = "Quer seja um condutor orgulhoso ou um apaixonado pela Toyota Gazoo Racing, existe uma diversidade de artigos oficiais, como vestu�rio, acess�rios de moda e outros utilit�rios que permitem que vista os valores da Toyota.";
    private static final String LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_1 = "Com a caixa de arruma��o Lexus mantenha os seus itens essenciais de viagem organizados e seguros evitando danificar os seus produtos ou a bagageira do seu Lexus, eliminando ainda ru�dos desnecess�rios. Quando n�o precisar da caixa, a mesma pode ser dobrada e arrumada com facilidade.";

    private static final String TOYOTA_ACCESSORY_DEFAULT_NAME_1 = "F�MEAS DE SEGURAN�A";
    private static final String TOYOTA_ACCESSORY_DEFAULT_NAME_2 = "MERCHANDISING TOYOTA";

    private static final String LEXUS_ACCESSORY_DEFAULT_NAME_2 = "F�MEAS DE SEGURAN�A";
    private static final String LEXUS_ACCESSORY_DEFAULT_NAME_1 = "CAIXA DE ARRUMA��O LEXUS";

    private static final String TOYOTA_ACCESSORY_DEFAULT_REF_1 = "T1";
    private static final String LEXUS_ACCESSORY_DEFAULT_REF_1 = "L1";
    private static final String TOYOTA_ACCESSORY_DEFAULT_REF_2 = "T2";
    private static final String LEXUS_ACCESSORY_DEFAULT_REF_2 = "L2";

    private final static Logger logger = Logger.getLogger(TPAInvokerSimulator.class.getName());


    public static TpaSimulation getTpaSimulation(String nif,String numberplate, Calendar calDate, boolean isTPAImportFromBi)
            throws SCErrorException{

        return getTpaSimulation(nif,numberplate, calDate, null, MainInitServlet.WS_CAR_LOCATION,isTPAImportFromBi);
    }

    public static TpaSimulation getTpaSimulation(String nif, String numberplate, Calendar calDate, Integer idClientType, String wsCarLocation,boolean isTPAImportFromBi)
            throws SCErrorException {
        boolean isBusinessPlus = false;

        List<ParameterizationItems> paramItems = null;
        ParameterizationItems selectedDestaqueParam = null;
        String destaqueOriginParameterization = "";
        ParameterizationItems selectedServicoParam = null;
        String servicoOriginParameterization = "";
        ParameterizationItems selectedHeaderParam = null;
        String headerOriginParameterization = "";
        TpaSimulation simulation = new TpaSimulation();
        ParameterizationFilter filter = new ParameterizationFilter();
        List<PaParameterization> parameterizations = null;
        CarInfo carInfo = new CarInfo();
        List<CarInfo> businessCarInfo = new ArrayList<>();

        ProgramaAvisos paData = null;
        Mrs mrs = new Mrs();

        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH) +1;
        logger.debug("*****TPA_MRS_SIMULATION******   PLATE:"+numberplate +"||  NIF:"+nif );
        if((numberplate==null || numberplate.equals("")) && nif!=null && !nif.equals("")){
            logger.trace("*****TPA_MRS******PRE ->  ProgramaAvisos.getHelper().getPADataByNif(nif,ClientType.BUSINESS_PLUS_ID,month,year);");
            paData = ProgramaAvisos.getHelper().getPADataByNif(nif, PaConstants.BUSINESS_PLUS_ID,month,year);
            logger.trace("*****TPA_MRS******POS ->  ProgramaAvisos.getHelper().getPADataByNif(nif,ClientType.BUSINESS_PLUS_ID,month,year);");

            if(paData!=null){
                logger.trace("*****TPA_MRS******PRE ->  Mrs.getHelper().getByIdPaData(paData.getId());");
                mrs = Mrs.getHelper().getByIdPaData(paData.getId());
                logger.trace("*****TPA_MRS******POS ->  Mrs.getHelper().getByIdPaData(paData.getId());");

                paData.setMRS(mrs);
            }
            simulation.setPaData(paData);
            isBusinessPlus = true;
            List<String> plates = ProgramaAvisos.getHelper().getPlateByNif(nif,PaConstants.BUSINESS_PLUS_ID,month,year);
            if(plates!=null && plates.size() > 0){
                numberplate = plates.get(0);
            }
            try {
                for(String plate : plates){
                    carInfo = getCarInfo(plate, calDate.getTime(), wsCarLocation,paData,isTPAImportFromBi);
                    if(carInfo!=null){
                        businessCarInfo.add(carInfo);
                    }
                }
            } catch (Exception e) {
                throw new SCErrorException("TPAInvokerSimulator.getTpaSimulation"+"->numberplate: "+numberplate+" ->paDate:"+ calDate.getTime(),e);
            }
        }
        if(numberplate != null && !numberplate.equals("") &&  calDate.getTime() != null && wsCarLocation != null){
            if(paData == null){

                if(idClientType == null){
                    idClientType = PaConstants.NORMAL_ID;
                }
                logger.trace("*****TPA_MRS******PRE ->  ProgramaAvisos.getHelper().getPADataByPlate(numberplate,idClientType,month,year);");
                paData = ProgramaAvisos.getHelper().getPADataByPlate(numberplate,idClientType,month,year);
                logger.trace("*****TPA_MRS******POS ->  ProgramaAvisos.getHelper().getPADataByPlate(numberplate,idClientType,month,year);");

                if(paData!=null){
                    logger.trace("*****TPA_MRS******PRE ->  Mrs.getHelper().getByIdPaData(paData.getId());");
                    mrs = Mrs.getHelper().getByIdPaData(paData.getId());
                    logger.trace("*****TPA_MRS******POS ->  Mrs.getHelper().getByIdPaData(paData.getId());");

                    paData.setMRS(mrs);
                }

            }
            simulation.setPaData(paData);
            try {
                logger.trace("*****TPA_MRS******PRE ->  getCarInfo(numberplate, paDate, wsCarLocation,paData);");
                carInfo = getCarInfo(numberplate,  calDate.getTime(), wsCarLocation,paData,isTPAImportFromBi);
                logger.trace("*****TPA_MRS******POS ->  getCarInfo(numberplate, paDate, wsCarLocation,paData);");
            } catch (Exception e) {
                throw new SCErrorException("TPAInvokerSimulator.getTpaSimulation"+"->numberplate: "+numberplate+" ->paDate:"+calDate.getTime(),e);
            }

            if(carInfo!=null){
                java.sql.Date date = new java.sql.Date(calDate.getTime().getTime());
                filter.setDtStart(date);
                filter.setDtEnd(date);
                int idBrand = 0;
                if(carInfo.getCar()!= null && carInfo.getCar().getIdBrand() > 0){
                    idBrand = carInfo.getCar().getIdBrand();
                } else{
                    if(paData.getBrand() != null && BRAND_TOYOTA.equalsIgnoreCase(paData.getBrand())){
                        idBrand = ApiConstants.ID_BRAND_TOYOTA;
                    } else if(paData.getBrand() != null && BRAND_LEXUS.equalsIgnoreCase(paData.getBrand())){
                        idBrand = ApiConstants.ID_BRAND_LEXUS;
                    } else {
                        logger.error("A matricula "+numberplate+" n�o tem dados suficiente (vers�o, brand ou cod. local) na CarDB ou DBCRMTCP para fazer simula��o.");
                        return null;
                    }

                }
                logger.trace("*****TPA_MRS******PRE ->  ApplicationConfiguration.getInstance().getParameterizationsByClient(idBrand, filter);");
                parameterizations = ApplicationConfiguration.getInstance().getParameterizationsByClient(idBrand, filter);
                logger.trace("*****TPA_MRS******POS ->  ApplicationConfiguration.getInstance().getParameterizationsByClient(idBrand, filter);");

            }
        }

        if (parameterizations != null && parameterizations.size()>0) {
            logger.trace("*****TPA_MRS******PRE ->  ContactReason.getHelper().getAllContactReasons();");
            LinkedHashMap<Integer, ContactReason> contactReasonsMap = ContactReason.getHelper().getAllContactReasons();
            logger.trace("*****TPA_MRS******POS ->  ContactReason.getHelper().getAllContactReasons();");
            logger.trace("*****TPA_MRS******PRE ->  DocumentUnit.getHelper().getAllDocumentUnits();");
            LinkedHashMap<Integer, DocumentUnit> documentUnitsMap = DocumentUnit.getHelper().getAllDocumentUnits();
            logger.trace("*****TPA_MRS******POS ->  DocumentUnit.getHelper().getAllDocumentUnits();");

            simulation.setContactReason(contactReasonsMap.get(carInfo.getIdContactReason()).getContactReason());
            simulation.setCarInfo(carInfo);
            simulation.setBusinessCarInfo(businessCarInfo);

            for (PaParameterization parametrization : parameterizations) {
                paramItems = (ArrayList<ParameterizationItems>) parametrization.getParameterizationItems();

                if (paramItems != null) {
                    for (ParameterizationItems paramItem : paramItems) {
                        boolean isToCheck = true;
                        if(isBusinessPlus){
                            isToCheck = paramItem.getIdContactReason() == PaConstants.ID_CONTACT_TYPE_BUS;
                        }else{
                            isToCheck = paramItem.getIdContactReason() == carInfo.getIdContactReason();
                        }
                        if(paramItem.getParameterizationItemsType().equals(PaConstants.PARAMETRIZATION_ITEM_TYPE_SPECIFIC) && isToCheck ){
                            List<ItemsEntityType> entityTypes = paramItem.getItemEntityTypes();
                            List<ItemsAge> ages = paramItem.getItemAges();
                            List<ItemsDealer> dealers = paramItem.getItemDealers();
                            List<ItemsFuel> fuels = paramItem.getItemFuels();
                            List<ItemsGenre> genres = paramItem.getItemGenres();
                            List<ItemsModel> gamma = paramItem.getItemModels();
                            List<ItemsKilometers> kilometers = paramItem.getItemKilometers();
                            List<ItemsFidelitys> fidelitys = paramItem.getItemFidelitys();

                            if (!carModelIsInParametrization(gamma,carInfo.getIdModel())) {
                                continue;
                            }

                            if (!entityTypeIsInParametrization(entityTypes, carInfo.getIdentity())) {
                                continue;
                            }

                            if (!genreIsInParametrization(genres,carInfo.getIdGender())) {
                                continue;
                            }

                            if (!fuelIsInParametrization(fuels, carInfo.getIdFuel())) {
                                continue;
                            }

                            if (!ageIsInParametrization(ages, carInfo.getIdAge())) {
                                continue;
                            }

                            if (!kilometersIsInParametrization(kilometers,carInfo.getIdKilometers())) {
                                continue;
                            }

                            if (!fidelityIsInParametrization(fidelitys,carInfo.getIdFidelity())) {
                                continue;
                            }

                            if (!dealerIsInParametrization(dealers,paData.getOidDealer())) {
                                continue;
                            }

                            if (calDate.getTime().compareTo(parametrization.getDtStart()) >= 0 && (parametrization.getDtEnd() == null
                                    || calDate.getTime().compareTo(parametrization.getDtEnd()) <= 0)) {

                                if (parametrization.getType().equals("D")) {
                                    if (selectedDestaqueParam != null) {
                                        if (gamma.size() < selectedDestaqueParam.getItemModels().size()) {
                                            selectedDestaqueParam = paramItem;
                                            destaqueOriginParameterization = parametrization.getComments();
                                        } else if (gamma.size() == selectedDestaqueParam.getItemModels().size()) {

                                            if (entityTypes.size() < selectedDestaqueParam.getItemEntityTypes().size()) {
                                                selectedDestaqueParam = paramItem;
                                                destaqueOriginParameterization = parametrization.getComments();
                                            } else if (entityTypes.size() == selectedDestaqueParam.getItemEntityTypes().size()) {

                                                if (genres.size() < selectedDestaqueParam.getItemGenres().size()) {
                                                    selectedDestaqueParam = paramItem;
                                                    destaqueOriginParameterization = parametrization.getComments();
                                                } else if (genres.size() == selectedDestaqueParam.getItemGenres().size()) {

                                                    if (fuels.size() < selectedDestaqueParam.getItemFuels().size()) {
                                                        selectedDestaqueParam = paramItem;
                                                        destaqueOriginParameterization = parametrization.getComments();
                                                    } else if (fuels.size() == selectedDestaqueParam.getItemFuels().size()) {

                                                        if (ages.size() < selectedDestaqueParam.getItemAges().size()) {
                                                            selectedDestaqueParam = paramItem;
                                                            destaqueOriginParameterization = parametrization.getComments();
                                                        } else if (ages.size() == selectedDestaqueParam.getItemAges().size()) {

                                                            if(kilometers.size() < selectedDestaqueParam.getItemKilometers().size()){
                                                                selectedDestaqueParam = paramItem;
                                                                destaqueOriginParameterization = parametrization.getComments();
                                                            }else if (kilometers.size() == selectedDestaqueParam.getItemKilometers().size()){

                                                                if(fidelitys.size() < selectedDestaqueParam.getItemFidelitys().size()){
                                                                    selectedDestaqueParam = paramItem;
                                                                    destaqueOriginParameterization = parametrization.getComments();
                                                                }else if (fidelitys.size() == selectedDestaqueParam.getItemFidelitys().size()){

                                                                    if (dealers.size() < selectedDestaqueParam.getItemDealers().size()) {
                                                                        selectedDestaqueParam = paramItem;
                                                                        destaqueOriginParameterization = parametrization.getComments();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        selectedDestaqueParam = paramItem;
                                        destaqueOriginParameterization = parametrization.getComments();
                                    }
                                }

                                if (parametrization.getType().equals("S")) {
                                    if (selectedServicoParam != null) {
                                        if (gamma.size() < selectedServicoParam.getItemModels().size()) {
                                            selectedServicoParam = paramItem;
                                            servicoOriginParameterization = parametrization.getComments();
                                        } else if (gamma.size() == selectedServicoParam.getItemModels().size()) {

                                            if (entityTypes.size() < selectedServicoParam.getItemEntityTypes().size()) {
                                                selectedServicoParam = paramItem;
                                                servicoOriginParameterization = parametrization.getComments();
                                            } else if (entityTypes.size() == selectedServicoParam.getItemEntityTypes().size()) {

                                                if (genres.size() < selectedServicoParam.getItemGenres().size()) {
                                                    selectedServicoParam = paramItem;
                                                    servicoOriginParameterization = parametrization.getComments();
                                                } else if (genres.size() == selectedServicoParam.getItemGenres().size()) {

                                                    if (fuels.size() < selectedServicoParam.getItemFuels().size()) {
                                                        selectedServicoParam = paramItem;
                                                        servicoOriginParameterization = parametrization.getComments();
                                                    } else if (fuels.size() == selectedServicoParam.getItemFuels().size()) {

                                                        if (ages.size() < selectedServicoParam.getItemAges().size()) {
                                                            selectedServicoParam = paramItem;
                                                            servicoOriginParameterization = parametrization.getComments();
                                                        } else if (ages.size() == selectedServicoParam.getItemAges().size()) {

                                                            if(kilometers.size() < selectedServicoParam.getItemKilometers().size()){
                                                                selectedServicoParam = paramItem;
                                                                servicoOriginParameterization = parametrization.getComments();
                                                            }else  if (kilometers.size() == selectedServicoParam.getItemKilometers().size()){

                                                                if(fidelitys.size() < selectedServicoParam.getItemFidelitys().size()){
                                                                    selectedServicoParam = paramItem;
                                                                    servicoOriginParameterization = parametrization.getComments();
                                                                }else  if (fidelitys.size() == selectedServicoParam.getItemFidelitys().size()){

                                                                    if (dealers.size() < selectedServicoParam.getItemDealers().size()) {
                                                                        selectedServicoParam = paramItem;
                                                                        servicoOriginParameterization = parametrization.getComments();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        selectedServicoParam = paramItem;
                                        servicoOriginParameterization = parametrization.getComments();
                                    }
                                }

                                if (parametrization.getType().equals("H")) {
                                    if (selectedHeaderParam != null) {
                                        if (gamma.size() < selectedHeaderParam.getItemModels().size()) {
                                            selectedHeaderParam = paramItem;
                                            headerOriginParameterization = parametrization.getComments();
                                        } else if (gamma.size() == selectedHeaderParam.getItemModels().size()) {

                                            if (entityTypes.size() < selectedHeaderParam.getItemEntityTypes().size()) {
                                                selectedHeaderParam = paramItem;
                                                headerOriginParameterization = parametrization.getComments();
                                            } else if (entityTypes.size() == selectedHeaderParam.getItemEntityTypes().size()) {

                                                if (genres.size() < selectedHeaderParam.getItemGenres().size()) {
                                                    selectedHeaderParam = paramItem;
                                                    headerOriginParameterization = parametrization.getComments();
                                                } else if (genres.size() == selectedHeaderParam.getItemGenres().size()) {

                                                    if (fuels.size() < selectedHeaderParam.getItemFuels().size()) {
                                                        selectedHeaderParam = paramItem;
                                                        headerOriginParameterization = parametrization.getComments();
                                                    } else if (fuels.size() == selectedHeaderParam.getItemFuels().size()) {

                                                        if (ages.size() < selectedHeaderParam.getItemAges().size()) {
                                                            selectedHeaderParam = paramItem;
                                                            headerOriginParameterization = parametrization.getComments();
                                                        } else if (ages.size() == selectedHeaderParam.getItemAges().size()) {

                                                            if(kilometers.size() < selectedHeaderParam.getItemKilometers().size()){
                                                                selectedHeaderParam = paramItem;
                                                                headerOriginParameterization = parametrization.getComments();
                                                            }else if (kilometers.size() == selectedHeaderParam.getItemKilometers().size()){

                                                                if(fidelitys.size() < selectedHeaderParam.getItemFidelitys().size()){
                                                                    selectedHeaderParam = paramItem;
                                                                    headerOriginParameterization = parametrization.getComments();
                                                                }else if (fidelitys.size() == selectedHeaderParam.getItemFidelitys().size()){

                                                                    if (dealers.size() < selectedHeaderParam.getItemDealers().size()) {
                                                                        selectedHeaderParam = paramItem;
                                                                        headerOriginParameterization = parametrization.getComments();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        selectedHeaderParam = paramItem;
                                        headerOriginParameterization = parametrization.getComments();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (selectedDestaqueParam == null || selectedServicoParam == null || selectedHeaderParam == null) {
                for (PaParameterization parametrization : parameterizations) {
                    paramItems = (ArrayList<ParameterizationItems>) parametrization.getParameterizationItems();
                    if (paramItems != null) {
                        for (ParameterizationItems paramItem : paramItems) {

                            boolean isToCheck = true;
                            if(isBusinessPlus){
                                isToCheck = paramItem.getIdContactReason() == PaConstants.ID_CONTACT_TYPE_BUS;
                            }else{
                                isToCheck = paramItem.getIdContactReason() == carInfo.getIdContactReason();
                            }

                            if (paramItem.getParameterizationItemsType().equals(PaConstants.PARAMETRIZATION_ITEM_TYPE_DEFAULT) && isToCheck) {
                                if (parametrization.getType().equals("H") && selectedHeaderParam == null) {
                                    selectedHeaderParam = paramItem;
                                    headerOriginParameterization = parametrization.getComments();
                                    break;
                                }
                                if (parametrization.getType().equals("S") && selectedServicoParam == null) {
                                    selectedServicoParam = paramItem;
                                    servicoOriginParameterization = parametrization.getComments();
                                    break;
                                }
                                if (parametrization.getType().equals("D") && selectedDestaqueParam == null) {
                                    selectedDestaqueParam = paramItem;
                                    destaqueOriginParameterization = parametrization.getComments();
                                    break;
                                }
                            }

                        }
                    }
                    if (selectedDestaqueParam != null && selectedServicoParam != null && selectedHeaderParam != null) {
                        break;
                    }
                }
            }

            if (selectedServicoParam != null) {
                DocumentUnit du1 = documentUnitsMap.get(selectedServicoParam.getIdService1());
                if (du1 != null) {
                    simulation.setService1Link(du1.getLink());
                    simulation.setService1Name(du1.getName());
                    simulation.setService1Desc(du1.getDescription());
                    simulation.setService1ImgPostal(du1.getImgPostal());
                    simulation.setService1ImgEPostal(du1.getImgEPostal());
                    simulation.setService1Code(du1.getCode());
                    validateDocumentUnit(du1.getName(),du1.getImgPostal(),du1.getImgEPostal(), "Servi�o", numberplate);
                }

                DocumentUnit du2 = documentUnitsMap.get(selectedServicoParam.getIdService2());
                if (du2 != null) {
                    simulation.setService2Link(du2.getLink());
                    simulation.setService2Name(du2.getName());
                    simulation.setService2Desc(du2.getDescription());
                    simulation.setService2ImgPostal(du2.getImgPostal());
                    simulation.setService2ImgEPostal(du2.getImgEPostal());
                    simulation.setService2Code(du2.getCode());
                    validateDocumentUnit(du2.getName(),du2.getImgPostal(),du2.getImgEPostal(), "Servi�o", numberplate);
                }

                DocumentUnit du3 = documentUnitsMap.get(selectedServicoParam.getIdService3());
                if (du3 != null) {
                    simulation.setService3Link(du3.getLink());
                    simulation.setService3Name(du3.getName());
                    simulation.setService3Desc(du3.getDescription());
                    simulation.setService3ImgPostal(du3.getImgPostal());
                    simulation.setService3ImgEPostal(du3.getImgEPostal());
                    simulation.setService3Code(du3.getCode());
                    validateDocumentUnit(du3.getName(),du3.getImgPostal(),du3.getImgEPostal(), "Servi�o", numberplate);
                }

                simulation.setServicoOriginParameterization(servicoOriginParameterization);
            }

            if (selectedDestaqueParam != null) {

                DocumentUnit du4 = documentUnitsMap.get(selectedDestaqueParam.getIdHighlight1());
                if (du4 != null) {
                    simulation.setHighlight1Link(du4.getLink());
                    simulation.setHighlight1Name(du4.getName());
                    simulation.setHighlight1Desc(du4.getDescription());
                    simulation.setHighlight1ImgPostal(du4.getImgPostal());
                    simulation.setHighlight1ImgEPostal(du4.getImgEPostal());
                    simulation.setHighlight1Code(du4.getCode());
                    validateDocumentUnit(du4.getName(),du4.getImgPostal(),du4.getImgEPostal(), "Destaque", numberplate);
                }

                DocumentUnit du5 = documentUnitsMap.get(selectedDestaqueParam.getIdHighlight2());
                if (du5 != null) {
                    simulation.setHighlight2Link(du5.getLink());
                    simulation.setHighlight2Name(du5.getName());
                    simulation.setHighlight2Desc(du5.getDescription());
                    simulation.setHighlight2ImgPostal(du5.getImgPostal());
                    simulation.setHighlight2ImgEPostal(du5.getImgEPostal());
                    simulation.setHighlight2Code(du5.getCode());
                    validateDocumentUnit(du5.getName(),du5.getImgPostal(),du5.getImgEPostal(), "Destaque", numberplate);

                }

                simulation.setDestaqueOriginParameterization(destaqueOriginParameterization);
            }

            if (selectedHeaderParam != null) {

                DocumentUnit du6 = documentUnitsMap.get(selectedHeaderParam.getIdHeader1());
                if (du6 != null) {
                    simulation.setHeader1Name(du6.getName());
                    simulation.setHeader1ImgPostal(du6.getImgPostal());
                    simulation.setHeader1ImgEPostal(du6.getImgEPostal());
                    simulation.setHeader1Code(du6.getCode());
                    simulation.setHeader1Link(du6.getLink());
                    validateDocumentUnit(du6.getName(),du6.getImgPostal(),du6.getImgEPostal(), "Header", numberplate);
                }

                DocumentUnit du7 = documentUnitsMap.get(selectedHeaderParam.getIdHeader2());
                if (du7 != null) {
                    simulation.setHeader2Name(du7.getName());
                    simulation.setHeader2ImgPostal(du7.getImgPostal());
                    simulation.setHeader2ImgEPostal(du7.getImgEPostal());
                    simulation.setHeader2Code(du7.getCode());
                    simulation.setHeader2Link(du7.getLink());
                    validateDocumentUnit(du7.getName(),du7.getImgPostal(),du7.getImgEPostal(), "Header", numberplate);

                }

                DocumentUnit du8 = documentUnitsMap.get(selectedHeaderParam.getIdHeader3());
                if (du8 != null) {
                    simulation.setHeader3Name(du8.getName());
                    simulation.setHeader3ImgPostal(du8.getImgPostal());
                    simulation.setHeader3ImgEPostal(du8.getImgEPostal());
                    simulation.setHeader3Code(du8.getCode());
                    simulation.setHeader3Link(du8.getLink());
                    validateDocumentUnit(du8.getName(),du8.getImgPostal(),du8.getImgEPostal(),"Header", numberplate);

                }

                simulation.setHeaderOriginParameterization(headerOriginParameterization);
            }
            logger.trace("*****TPA_MRS******PRE ->  getAcessory(carInfo.getIdModel(), carInfo.getIdVc(), numberplate, wsCarLocation);");
            List<Acessories> acessories = null;
            if(isTPAImportFromBi){
                acessories = getAcessory(carInfo.getIdModel(), carInfo.getIdVc(), numberplate, wsCarLocation);
            }
            logger.trace("*****TPA_MRS******POS ->  getAcessory(carInfo.getIdModel(), carInfo.getIdVc(), numberplate, wsCarLocation);");

            Acessories accessory = null;
            AcessorioGrupo kit = null;
            if (acessories != null && acessories.size() >= 2) {

                if (acessories.get(0) != null) {
                    accessory = acessories.get(0);
                    logger.trace("*****TPA_MRS******PRE ->   AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());
                    logger.trace("*****TPA_MRS******POS ->   AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");

                    simulation.setAccessory1Link(accessoryLink.getLink());
                    logger.trace("*****TPA_MRS******PRE ->   AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());
                    logger.trace("*****TPA_MRS******POS ->   AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");
                    simulation.setAccessory1Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("")?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory1Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK_1);
                            simulation.setAccessory1ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                            simulation.setAccessory1ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                            simulation.setAccessory1Code(TOYOTA_ACCESSORY_DEFAULT_REF_1);
                            simulation.setAccessory1Name(TOYOTA_ACCESSORY_DEFAULT_NAME_1);
                            simulation.setAccessory1Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_1);
                        }
                    }else if(simulation.getPaData().getBrand().equals(BRAND_LEXUS)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("") ?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory1Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK_1);
                            simulation.setAccessory1ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                            simulation.setAccessory1ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                            simulation.setAccessory1Code(LEXUS_ACCESSORY_DEFAULT_REF_1);
                            simulation.setAccessory1Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_1);
                            simulation.setAccessory1Name(LEXUS_ACCESSORY_DEFAULT_NAME_1);
                        }
                    }
                }

                if (acessories.get(1) != null) {
                    accessory = acessories.get(1);
                    logger.trace("*****TPA_MRS******PRE -> 1  AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());
                    logger.trace("*****TPA_MRS******POS -> 1  AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");

                    simulation.setAccessory2Link(accessoryLink.getLink());
                    logger.trace("*****TPA_MRS******PRE -> 1  AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());
                    logger.trace("*****TPA_MRS******POS -> 1  AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");

                    simulation.setAccessory2Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory2Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory2ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory2ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory2Code(kit.getReferencia());
                            simulation.setAccessory2Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("")?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory2Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory2ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory2ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory2Code(accessory.getReferencia());
                            simulation.setAccessory2Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory2Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory2Link(TOYOTA_ACCESSORY_DEFAULT_LINK_2);
                            simulation.setAccessory2ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                            simulation.setAccessory2ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                            simulation.setAccessory2Code(TOYOTA_ACCESSORY_DEFAULT_REF_2);
                            simulation.setAccessory2Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_2);
                            simulation.setAccessory2Name(TOYOTA_ACCESSORY_DEFAULT_NAME_2);
                        }
                    }else if(simulation.getPaData().getBrand().equals(BRAND_LEXUS)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory2Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory2ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory2ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory2Code(kit.getReferencia());
                            simulation.setAccessory2Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("")?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory2Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory2ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory2ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory2Code(accessory.getReferencia());
                            simulation.setAccessory2Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory2Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory2Link(LEXUS_ACCESSORY_DEFAULT_LINK_2);
                            simulation.setAccessory2ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                            simulation.setAccessory2ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                            simulation.setAccessory2Code(LEXUS_ACCESSORY_DEFAULT_REF_2);
                            simulation.setAccessory2Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_2);
                            simulation.setAccessory2Name(LEXUS_ACCESSORY_DEFAULT_NAME_2);
                        }
                    }
                }

            } else if (acessories != null && acessories.size() == 1) {

                if (acessories.get(0) != null) {
                    accessory = acessories.get(0);
                    logger.trace("*****TPA_MRS******PRE -> 2  AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());
                    logger.trace("*****TPA_MRS******POS -> 2  AcessoriesHelper.getAcessory(accessory.getIdAcessorio());");
                    simulation.setAccessory1Link(accessoryLink.getLink());
                    logger.trace("*****TPA_MRS******PRE -> 2  AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());
                    logger.trace("*****TPA_MRS******POS -> 2  AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());");
                    simulation.setAccessory1Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("")?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory1Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK_1);
                            simulation.setAccessory1ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                            simulation.setAccessory1ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                            simulation.setAccessory1Code(TOYOTA_ACCESSORY_DEFAULT_REF_1);
                            simulation.setAccessory1Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_1);
                            simulation.setAccessory1Name(TOYOTA_ACCESSORY_DEFAULT_NAME_1);
                        }
                        simulation.setAccessory2Link(TOYOTA_ACCESSORY_DEFAULT_LINK_2);
                        simulation.setAccessory2Code(TOYOTA_ACCESSORY_DEFAULT_REF_2);
                        simulation.setAccessory2Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_2);
                        simulation.setAccessory2Name(TOYOTA_ACCESSORY_DEFAULT_NAME_2);
                        simulation.setAccessory2ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                        simulation.setAccessory2ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                    }else if(simulation.getPaData().getBrand().equals(BRAND_LEXUS)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().equals("")){
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().equals("")?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().equals("")?accessory.getDescricaoMarketing():accessory.getDescricao());
                            simulation.setAccessory1Desc(accessory.getLongDescription());
                        }else{
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK_1);
                            simulation.setAccessory1ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                            simulation.setAccessory1ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                            simulation.setAccessory1Code(LEXUS_ACCESSORY_DEFAULT_REF_1);
                            simulation.setAccessory1Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_1);
                            simulation.setAccessory1Name(LEXUS_ACCESSORY_DEFAULT_NAME_1);
                        }
                        simulation.setAccessory2Link(LEXUS_ACCESSORY_DEFAULT_LINK_2);
                        simulation.setAccessory2Code(LEXUS_ACCESSORY_DEFAULT_REF_2);
                        simulation.setAccessory2Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_2);
                        simulation.setAccessory2Name(LEXUS_ACCESSORY_DEFAULT_NAME_2);
                        simulation.setAccessory2ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                        simulation.setAccessory2ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                    }
                }
            }else if(acessories == null || acessories.size() == 0){
                if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                    simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK_1);
                    simulation.setAccessory1Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_1);
                    simulation.setAccessory1Name(TOYOTA_ACCESSORY_DEFAULT_NAME_1);
                    simulation.setAccessory1Code(TOYOTA_ACCESSORY_DEFAULT_REF_1);
                    simulation.setAccessory1ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                    simulation.setAccessory1ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                    simulation.setAccessory2Link(TOYOTA_ACCESSORY_DEFAULT_LINK_2);
                    simulation.setAccessory2Desc(TOYOTA_ACCESSORY_DEFAULT_DESCRIPTION_2);
                    simulation.setAccessory2Name(TOYOTA_ACCESSORY_DEFAULT_NAME_2);
                    simulation.setAccessory2Code(TOYOTA_ACCESSORY_DEFAULT_REF_2);
                    simulation.setAccessory2ImgPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                    simulation.setAccessory2ImgEPostal(TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                }else if(simulation.getPaData().getBrand().equals(BRAND_LEXUS)){
                    simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK_1);
                    simulation.setAccessory1Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_1);
                    simulation.setAccessory1Name(LEXUS_ACCESSORY_DEFAULT_NAME_1);
                    simulation.setAccessory1Code(LEXUS_ACCESSORY_DEFAULT_REF_1);
                    simulation.setAccessory1ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_1);
                    simulation.setAccessory1ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_1);
                    simulation.setAccessory2Link(LEXUS_ACCESSORY_DEFAULT_LINK_2);
                    simulation.setAccessory2Desc(LEXUS_ACCESSORY_DEFAULT_DESCRIPTION_2);
                    simulation.setAccessory2Name(LEXUS_ACCESSORY_DEFAULT_NAME_2);
                    simulation.setAccessory2Code(LEXUS_ACCESSORY_DEFAULT_REF_2);
                    simulation.setAccessory2ImgPostal(LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_2);
                    simulation.setAccessory2ImgEPostal(LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_2);
                }
            }
        }
        return simulation;
    }



    private static void validateDocumentUnit(String name, String imgPostal, String imgEPostal, String type, String numberplate) throws SCErrorException {

        if(name==null || name.equals("")){
            SCErrorException ex = new SCErrorException("TPAInvokerSimulator.validateDocumentUnit","Nome n�o definido para o "+type+" do carro com a matricula "+numberplate);
            logger.error(ex.getErrorMessage(), ex);
            throw ex;
        }

        if(imgPostal==null || imgPostal.equals("")){
            SCErrorException ex = new SCErrorException("TPAInvokerSimulator.validateDocumentUnit","Imagem Postal n�o definida para o "+type+" do carro com a matricula "+numberplate);
            logger.error(ex.getErrorMessage(), ex);
            throw ex;
        }

        if(imgEPostal==null || imgEPostal.equals("")){
            SCErrorException ex = new SCErrorException("TPAInvokerSimulator.validateDocumentUnit","Imagem e-Postal n�odefinida para o "+type+" do carro com a matricula "+numberplate);
            logger.error(ex.getErrorMessage(), ex);
            throw ex;
        }


    }

    private static boolean dealerIsInParametrization(List<ItemsDealer> dealers, String oidDealer) {
        boolean dealerIsInParametrization = false;
        for (ItemsDealer oItemsDealer : dealers) {
            if(oItemsDealer.getIdDealer().equalsIgnoreCase("99")){
                dealerIsInParametrization = true;
                break;
            }
            if (oidDealer!= null && !oidDealer.equalsIgnoreCase("") && oItemsDealer.getIdDealer().equalsIgnoreCase(oidDealer)) {
                dealerIsInParametrization = true;
                break;
            }
        }
        return dealerIsInParametrization;
    }

    private static boolean ageIsInParametrization(List<ItemsAge> ages, int idAge) {
        boolean ageIsInParametrization = false;
        for (ItemsAge oItemsAge : ages) {
            if (oItemsAge.getIdAge() ==idAge) {
                ageIsInParametrization = true;
                break;
            }
        }

        //Se n�o tiver encontrado, verificar se o carro tem 7+ e verificar se o parametro 7+ est� parametrizado
        if(ageIsInParametrization == false && idAge > 7){
            for (ItemsAge age:ages) {
                if(age.getId() == PaConstants.ID_PA_PARAMETRIZATION_AGE_7_PLUS){
                    ageIsInParametrization = true;
                    break;
                }
            }
        }

        return ageIsInParametrization;
    }

    private static boolean fuelIsInParametrization(List<ItemsFuel> fuels, int idFuel) {
        boolean fuelIsInParametrization = false;
        for (ItemsFuel oItemsFuel : fuels) {

            if (oItemsFuel.getIdFuel()==idFuel) {
                fuelIsInParametrization = true;
                break;
            }

            if(idFuel == 0 && oItemsFuel.getIdFuel() == CAR_DB_COMBUSTIVEL_SEM_INFO){
                fuelIsInParametrization = true;
                break;
            }

        }
        return fuelIsInParametrization;
    }

    private static boolean genreIsInParametrization(List<ItemsGenre> genres, int idGender) {
        boolean genreIsInParametrization = false;
        for (ItemsGenre oItemsGenre : genres) {
            if (oItemsGenre.getIdGenre() == idGender) {
                genreIsInParametrization = true;
                break;
            }
        }
        return genreIsInParametrization;
    }

    private static boolean entityTypeIsInParametrization(List<ItemsEntityType> entityTypes,int idEntityType) {

        boolean entityTypeIsInParametrization = false;
        for (ItemsEntityType oItemsEntityType : entityTypes) {
            if (oItemsEntityType.getIdEnittyType() == idEntityType) {
                entityTypeIsInParametrization = true;
                break;
            }
        }
        return entityTypeIsInParametrization;
    }

    private static boolean carModelIsInParametrization(List<ItemsModel> gamma, int idModel) {

        boolean carModelIsInParametrizationGamma = false;
        for (ItemsModel oItemsModel : gamma) {
            if (oItemsModel.getIdGamma() == idModel) {
                carModelIsInParametrizationGamma = true;
                break;
            }
        }
        return carModelIsInParametrizationGamma;
    }

    private static boolean fidelityIsInParametrization(List<ItemsFidelitys> fidelitys,int idFidelity) {

        boolean fidelityIsInParametrization = false;
        for (ItemsFidelitys oItemsFidelity : fidelitys) {
            if(oItemsFidelity.getIdFidelity() == idFidelity){
                fidelityIsInParametrization = true;
                break;
            }
        }

        return fidelityIsInParametrization;
    }

    private static boolean kilometersIsInParametrization(List<ItemsKilometers> kilometers,int kilometersId) {

        boolean kilometersIsInParametrization = false;
        for (ItemsKilometers oItemsKilometer : kilometers) {
            if (oItemsKilometer.getIdKilometers() == kilometersId) {
                kilometersIsInParametrization = true;
                break;
            }
        }
        return kilometersIsInParametrization;
    }

    private static List<Acessories> getAcessory(int idModel, int idVc, String plate, String wsCarLocation) throws SCErrorException {

        List<Acessories> priorityAcessories = new ArrayList<Acessories>();
        List<Integer> lstIdsModel = new ArrayList<Integer>();
        lstIdsModel.add(idModel);

        List<AccessoryInstalled> installedAccessories = null;

        WsInvokeCarServiceTCAP invoker = new WsInvokeCarServiceTCAP(wsCarLocation);

        List<Acessories> acessories = CarHelper.getAccessoryByCommercialVersion(idVc);

        AccessoryInstalledResponse oAccessoryInstalledResponse = invoker.getInstalledAccessoriesByPlate(plate);

        if (oAccessoryInstalledResponse != null &&
                oAccessoryInstalledResponse.getAcessoryInstalled() != null &&
                oAccessoryInstalledResponse.getAcessoryInstalled().size() > 0) {
            installedAccessories = oAccessoryInstalledResponse.getAcessoryInstalled();
        }

        if (lstIdsModel.size() > 0) {

            List<Integer> lstIdsAcessories = new ArrayList<Integer>();
            List<String> lstIdsInstalledAcessories = new ArrayList<String>();

            if (installedAccessories != null) {
                for (AccessoryInstalled acInstalled : installedAccessories) {
                    lstIdsInstalledAcessories.add(acInstalled.getPartNumber());
                }
            }

            for (Acessories acessory : acessories) {
                if (!lstIdsInstalledAcessories.contains(acessory.getReferencia())) {
                    //SO VAI APRESENTAR ACESSORIO DE PRIORIDADE 1
                    if(acessory.getPrioridade() == 1){
                        lstIdsAcessories.add(acessory.getIdAcessorio());
                    }
                }
            }
            if (lstIdsAcessories.size() > 0) {

                HashMap<Integer, String> priorityAccessories = AcessoriesHelper.getPriorityAcessories(idVc,
                        lstIdsAcessories, true, 2);
                if (!priorityAccessories.isEmpty()) {
                    int i = 0;

                    for (Entry<Integer, String> entry : priorityAccessories.entrySet()) {
                        i++;
                        if (i <= 2) {
                            priorityAcessories.add(AcessoriesHelper.getAcessory(entry.getKey()));

                        } else {
                            break;
                        }

                    }

                }
            }
        }

        return priorityAcessories;
    }

    public static CarInfo getCarInfo(String numberplate, Date date, String wsCarLocation, ProgramaAvisos paData, boolean isTPAImportFromBi) throws SCErrorException, ParseException {

        CarInfo carInfo = new CarInfo();

        com.gsc.ws.core.CarInfo as400Car = null;
        Car carDBInfo1 = null;

        WsInvokeCarServiceTCAP oWsInfo = new WsInvokeCarServiceTCAP(wsCarLocation);

        CarInfoResponse oCarInfoResponse = oWsInfo.getCarByPlate(numberplate);

        RepairResponse repairs = null;
        if(isTPAImportFromBi){
            repairs = oWsInfo.getCarRepairsByPlate(numberplate);
        }
        List<Repair> listRepairs = null;

        if(repairs != null){
            listRepairs = repairs.getRepairInfo();
        }

        if (oCarInfoResponse != null && oCarInfoResponse.getCarInfo() != null) {
            as400Car = oCarInfoResponse.getCarInfo();
        }

        if(listRepairs!=null && listRepairs.size()>0){
            Repair lastRepair = listRepairs.get(listRepairs.size()-1);
            Dealer oDealer = ApplicationConfiguration.getDealerByDealerAndAfterSalesCode(Dealer.OID_NET_TOYOTA, lastRepair.getDealerCode(),  StringTasks.cleanString(lastRepair.getAfterSalesCode(), ""));
            if(oDealer!=null){
                String dealerName = oDealer.getDesig();

                String dealerLocal = "";
                if(oDealer.getCpExt() != null && !oDealer.getCpExt().equals("")){
                    dealerLocal = "("+oDealer.getCpExt()+")";
                }
                carInfo.setLastServiceDealer(dealerName+" "+ dealerLocal);
                carInfo.setLastServiceDealerContact(oDealer.getEmail() + " " + oDealer.getTel());
            }
        }

        if (as400Car != null) {
            carInfo.setAs400CarInfo(as400Car);
            String comercialModelCode = StringTasks.cleanString(as400Car.getComercialModelCode(), "");
            String versionCode = StringTasks.cleanString(as400Car.getVersionCode(), "");
            String dataMatricula = StringTasks.cleanString(as400Car.getDtPlate(), "");
            if (!dataMatricula.equals("")) {
                Date date1 = new SimpleDateFormat("yyyy").parse(dataMatricula);
                Date today = new Date();

                Calendar a = getCalendar(date1);
                Calendar b = getCalendar(today);
                int carAge = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
                if(carAge==1){
                    carInfo.setIdAge(1);
                }else if(carAge==2){
                    carInfo.setIdAge(2);
                }else if(carAge==3){
                    carInfo.setIdAge(3);
                }else if(carAge==4){
                    carInfo.setIdAge(4);
                }else if(carAge==5){
                    carInfo.setIdAge(5);
                }else if(carAge==6){
                    carInfo.setIdAge(6);
                }else if(carAge==7){
                    carInfo.setIdAge(7);
                }else if(carAge==7){
                    carInfo.setIdAge(8);
                }else if(carAge==9){
                    carInfo.setIdAge(9);
                }else if(carAge==10){
                    carInfo.setIdAge(10);
                }
                else if(carAge>10){
                    carInfo.setIdAge(11);
                }
                carInfo.setPlateDate(dataMatricula);
            }else{
                carInfo.setIdAge(99);
            }

            carDBInfo1 = CarHelper.getCarInfo(comercialModelCode, versionCode);
            if (carDBInfo1 != null) {
                Fuel fuel = CarHelper.getFuelsByIdVc(carDBInfo1.getIdVc());

                if(fuel!=null){
                    carInfo.setIdFuel(fuel.getId());
                    carInfo.setFuel(fuel.getDescription());
                }

                carInfo.setIdVc(carDBInfo1.getIdVc());
                carInfo.setIdModel(carDBInfo1.getModel().getId());
                carInfo.setCar(carDBInfo1);
            }

            if (paData != null) {
                Dealer dealer = null;
                if(paData.getBrand().equalsIgnoreCase("L") && paData.getOidDealer()!=null && !paData.getOidDealer().equals("")){
                    dealer = Dealer.getLexusHelper().getByObjectId(paData.getOidDealer());
                }else if(paData.getBrand().equalsIgnoreCase("T") && paData.getOidDealer()!=null && !paData.getOidDealer().equals("")){
                    dealer = Dealer.getToyotaHelper().getByObjectId(paData.getOidDealer());
                }


                if(dealer!=null){
                    String dealerName = dealer.getDesig();
                    String dealerLocal = "";
                    if(dealer.getCpExt() != null && !dealer.getCpExt().equals("")){
                        dealerLocal = "("+dealer.getCpExt()+")";
                    }
                    paData.setOidDealer(dealer.getObjectId());
                    carInfo.setDealer(dealerName +" "+ dealerLocal);
                }else{
                    carInfo.setDealer("N/D");
                    paData.setOidDealer("99");
                }


                Calendar calDate = getCalendar(date);

                int year = calDate.get(Calendar.YEAR);
                int month = calDate.get(Calendar.MONTH);

                if (paData.getYear() != year) {
                    return null;
                } else if (paData.getMonth() != (month + 1)) {
                    return null;
                }

                Mrs mrs = paData.getMRS();

                if (paData.getNif().length() > 0) {
                    String nif = paData.getNif();
                    carInfo.setNif(nif);
                    if (nif.startsWith("1") || nif.startsWith("2")) {
                        carInfo.setIdentity(2);
                    } else if (nif.startsWith("5") || nif.startsWith("6") || nif.startsWith("9")) {
                        carInfo.setIdentity(1);
                    }
                }else{
                    carInfo.setIdentity(99);
                }

                carInfo.setIdContactReason(paData.getIdContactType());
                if (mrs != null) {
                    if (mrs.getDtItv() != null ) {
                        carInfo.setDtItv(mrs.getDtItv());
                    }else{
                        if(as400Car.getDtNextITV() != null){
                            String sDate1=as400Car.getDtNextITV();
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                            carInfo.setDtItv(date1);
                        }
                    }

                    if (mrs.getDtNextRevision() != null) {
                        carInfo.setDtNextRevision(mrs.getDtNextRevision());
                    }

                    if(mrs.getDtLastRevision()!=null){
                        carInfo.setDtLastRevision((java.sql.Date) mrs.getDtLastRevision());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        carInfo.setDtLastRevisionString(sdf.format(mrs.getDtLastRevision()));

                        java.sql.Date dateLastRevision = (java.sql.Date) mrs.getDtLastRevision();
                        Calendar c = Calendar.getInstance();
                        c.roll(Calendar.YEAR, -2);
                        java.sql.Date dateToCompare1 = new java.sql.Date(c.getTimeInMillis());
                        c = Calendar.getInstance();
                        c.roll(Calendar.YEAR, -3);
                        java.sql.Date dateToCompare2 = new java.sql.Date(c.getTimeInMillis());
                        if(dateLastRevision.compareTo(dateToCompare1)>0){
                            carInfo.setIdFidelity(1);
                        }else if(dateLastRevision.compareTo(dateToCompare1)<=0 && dateLastRevision.compareTo(dateToCompare2)>=0){
                            carInfo.setIdFidelity(2);
                        }else if(dateLastRevision.compareTo(dateToCompare2)<0){
                            carInfo.setIdFidelity(3);
                        }
                    }else{
                        carInfo.setIdFidelity(99);
                    }

                    if(mrs.getGenre() != null && !mrs.getGenre().trim().equals("")){
                        Genre genre = Genre.getHelper().getGenderByDesc(mrs.getGenre());
                        carInfo.setGender(mrs.getGenre());
                        if(genre!=null){
                            carInfo.setIdGender(genre.getId());
                        }
                    }else{
                        carInfo.setIdGender(99);
                    }
                    if(mrs.getExpectedKm()!=null && !mrs.getExpectedKm().equals("")){
                        double expected = Double.valueOf(mrs.getExpectedKm());
                        carInfo.setKilometers(mrs.getExpectedKm());
                        if(expected <= 100000){
                            carInfo.setIdKilometers(1);
                        }else if(expected > 100000 && expected <= 130000){
                            carInfo.setIdKilometers(2);
                        }else if(expected > 130000 && expected <= 160000){
                            carInfo.setIdKilometers(3);
                        }else if(expected > 160000 && expected <= 185000){
                            carInfo.setIdKilometers(4);
                        }else if(expected > 185000 && expected <= 195000){
                            carInfo.setIdKilometers(5);
                        }else if(expected > 195000 && expected <= 200000){
                            carInfo.setIdKilometers(6);
                        }else if(expected > 200000 ){
                            carInfo.setIdKilometers(7);
                        }
                    }else{
                        carInfo.setIdKilometers(99);
                    }
                }
            }

            carInfo.setNumberPlate(numberplate);
        }

        return carInfo;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
    }



}
