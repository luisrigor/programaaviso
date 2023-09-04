package com.gsc.programaavisos.util;

import com.gsc.cardb.car.*;
import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.config.CacheConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.dto.TpaSimulation;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.entity.CarInfo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.ws.core.AccessoryInstalled;
import com.gsc.ws.core.Repair;
import com.gsc.ws.core.objects.response.AccessoryInstalledResponse;
import com.gsc.ws.core.objects.response.CarInfoResponse;
import com.gsc.ws.core.objects.response.RepairResponse;
import com.gsc.ws.invoke.WsInvokeCarServiceTCAP;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.StringTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import static com.gsc.programaavisos.config.environment.MapProfileVariables.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Log4j
@RequiredArgsConstructor
@Service
public class TPAInvokerSimulator {

    public static final int CAR_DB_COMBUSTIVEL_SEM_INFO = 4;


    private static final String BRAND_TOYOTA = "T";
    private static final String BRAND_LEXUS = "L";

    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK = "https://www.toyota.pt/aposvenda/pecas-acessorios/index.json";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK = "#";
    private static final String TPA_DEBUG = "*****TPA_MRS_SIMULATION******";


    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK_1 = "https://www.toyota.pt/aposvenda/pecas-acessorios/seguranca/femeas-de-seguranca";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK_1= "#";

    private static final String TOYOTA_ACCESSORY_DEFAULT_LINK_2 = "https://www.toyota.pt/aposvenda/pecas-acessorios/loja-merchandising";
    private static final String LEXUS_ACCESSORY_DEFAULT_LINK_2= "#";

    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_1 = "toyota_acessory_default_img_postal_1.jpg";
    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_1 = "toyota_acessory_default_img_e_postal_1.jpg";

    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_POSTAL_2 = "toyota_acessory_default_img_postal_2.jpg";
    private static final String TOYOTA_ACCESSORY_DEFAULT_IMG_E_POSTAL_2 = "toyota_acessory_default_img_e_postal_2.jpg";

    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_POSTAL_2 = "lexus_acessory_default_img_postal_1.jpg";
    private static final String LEXUS_ACCESSORY_DEFAULT_IMG_E_POSTAL_2 = "lexus_acessory_default_img_e_postal_1.jpg";

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
    private static final String HEADER = "Header";
    private static final String DESTAQUE = "Destaque";
    private static final String SERVICIO = "Servicio";

    private final EnvironmentConfig environmentConfig;
    private final PARepository paRepository;
    private final MrsRepository mrsRepository;
    private final ContactReasonRepository contactRepository;
    private final DocumentUnitRepository documentUnitRepository;
    private final GenreRepository genreRepository;
    private final CacheConfig cacheConfig;

    public TpaSimulation getTpaSimulation(String nif, String numberplate, LocalDate localDate, boolean isTPAImportFromBi)
            throws SCErrorException{
        Map<String, String> envV = environmentConfig.getEnvVariables();
        log.debug("getTapService: "+envV.get(CONST_WS_CAR_LOCATION));
        return getTpaSimulation(nif,numberplate, localDate, null, envV.get(CONST_WS_CAR_LOCATION),isTPAImportFromBi);
    }

    public TpaSimulation getTpaSimulation(String nif, String numberplate, LocalDate localDate, Integer idClientType, String wsCarLocation,boolean isTPAImportFromBi)
            throws SCErrorException {
        boolean isBusinessPlus = false;

        List<ParametrizationItems> paramItems = null;
        ParametrizationItems selectedDestaqueParam = null;
        String destaqueOriginParameterization = "";
        ParametrizationItems selectedServicoParam = null;
        String servicoOriginParameterization = "";
        ParametrizationItems selectedHeaderParam = null;
        String headerOriginParameterization = "";
        TpaSimulation simulation = new TpaSimulation();

        List<PaParameterization> parameterizations = null;
        CarInfo carInfo = new CarInfo();
        List<CarInfo> businessCarInfo = new ArrayList<>();
        Date dateCalendar = new Date(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth());
        ProgramaAvisos paData = null;
        Mrs mrs;
        List<Integer> contactList = new ArrayList<>(Arrays.asList(PaConstants.MAN,PaConstants.ITV,PaConstants.MAN_ITV));


        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        log.debug(TPA_DEBUG);
        if((numberplate==null || numberplate.isEmpty()) && nif!=null && !nif.isEmpty()){
            paData = paRepository.getPADataByNifData(nif, PaConstants.BUSINESS_PLUS_ID,month,year,contactList);
            if(paData!=null){
                mrs = mrsRepository.getByIdPaData(paData.getId());
                paData.setMRS(mrs);
            }
            simulation.setPaData(paData);
            isBusinessPlus = true;
            List<String> plates = paRepository.getPlateByNif(nif,PaConstants.BUSINESS_PLUS_ID,month,year,contactList);
            if(plates!=null && !plates.isEmpty()){
                numberplate = plates.get(0);
            }
            try {
                for(String plate : plates){

                    carInfo = getCarInfo(plate, dateCalendar, wsCarLocation,paData,isTPAImportFromBi);
                    if(carInfo!=null){
                        businessCarInfo.add(carInfo);
                    }
                }
            } catch (Exception e) {
                throw new SCErrorException(PaConstants.TPA_DOCUMENT_ERROR_MESSAGE,e);
            }
        }
        if(numberplate != null && !numberplate.isEmpty()) {

            if (wsCarLocation != null) {


                if (paData == null) {

                    if (idClientType == null) {
                        idClientType = PaConstants.NORMAL_ID;
                    }
                    paData = paRepository.getPADataByPlate(numberplate, month, year,contactList);

                    if (paData != null) {
                        mrs = mrsRepository.getByIdPaData(paData.getId());

                        paData.setMRS(mrs);
                    }

                }
                simulation.setPaData(paData);
                try {
                    carInfo = getCarInfo(numberplate, dateCalendar, wsCarLocation, paData, isTPAImportFromBi);
                } catch (Exception e) {
                    throw new SCErrorException(PaConstants.TPA_DOCUMENT_ERROR_MESSAGE);
                }
                if (carInfo != null) {
                    java.sql.Date date = new java.sql.Date(dateCalendar.getTime());

                    int idBrand = 0;
                    if (carInfo.getCar() != null && carInfo.getCar().getIdBrand() > 0) {
                        idBrand = carInfo.getCar().getIdBrand();
                    } else {
                        if (paData.getBrand() != null && BRAND_TOYOTA.equalsIgnoreCase(paData.getBrand())) {
                            idBrand = ApiConstants.ID_BRAND_TOYOTA;
                        } else if (paData.getBrand() != null && BRAND_LEXUS.equalsIgnoreCase(paData.getBrand())) {
                            idBrand = ApiConstants.ID_BRAND_LEXUS;
                        } else {
                            return null;
                        }

                    }
                    ParameterizationFilter filter = ParameterizationFilter.builder().idBrand(ApiConstants.ID_BRAND_TOYOTA).build();
                    parameterizations = cacheConfig.getParameterizationsByClient(idBrand,filter);
                }
            }
        }
        if (parameterizations != null && !parameterizations.isEmpty()) {
            LinkedHashMap<Integer, DocumentUnit> documentUnitsMap = getMapAllDocumentUnits(new LinkedHashMap<Integer, DocumentUnit>());
            ContactReason contactReason = contactRepository.findById(carInfo.getIdContactReason()).orElse(new ContactReason());
            simulation.setContactReason(contactReason.getContactReason());
            simulation.setCarInfo(carInfo);
            simulation.setBusinessCarInfo(businessCarInfo);

            for (PaParameterization parametrization : parameterizations) {
                paramItems = parametrization.getParametrizationItems();

                if (paramItems != null) {
                    for (ParametrizationItems paramItem : paramItems) {
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

                            if (dateCalendar.compareTo(parametrization.getDtStart()) >= 0 && (parametrization.getDtEnd() == null
                                    || localDate.compareTo(parametrization.getDtEnd().toLocalDate()) <= 0)) {

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
            log.debug(TPA_DEBUG);
            if (selectedDestaqueParam == null || selectedServicoParam == null || selectedHeaderParam == null) {
                for (PaParameterization parametrization : parameterizations) {
                    paramItems = parametrization.getParametrizationItems();
                    if (paramItems != null) {
                        for (ParametrizationItems paramItem : paramItems) {

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
            log.debug(TPA_DEBUG);
            if (selectedServicoParam != null) {
                DocumentUnit du1 = documentUnitsMap.get(selectedServicoParam.getIdService1());
                if (du1 != null) {
                    simulation.setService1Link(du1.getLink());
                    simulation.setService1Name(du1.getName());
                    simulation.setService1Desc(du1.getDescription());
                    simulation.setService1ImgPostal(du1.getImgPostal());
                    simulation.setService1ImgEPostal(du1.getImgEPostal());
                    simulation.setService1Code(du1.getCode());
                    validateDocumentUnit(du1.getName(),du1.getImgPostal(),du1.getImgEPostal(), SERVICIO, numberplate);
                }

                DocumentUnit du2 = documentUnitsMap.get(selectedServicoParam.getIdService2());
                if (du2 != null) {
                    simulation.setService2Link(du2.getLink());
                    simulation.setService2Name(du2.getName());
                    simulation.setService2Desc(du2.getDescription());
                    simulation.setService2ImgPostal(du2.getImgPostal());
                    simulation.setService2ImgEPostal(du2.getImgEPostal());
                    simulation.setService2Code(du2.getCode());
                    validateDocumentUnit(du2.getName(),du2.getImgPostal(),du2.getImgEPostal(), SERVICIO, numberplate);
                }

                DocumentUnit du3 = documentUnitsMap.get(selectedServicoParam.getIdService3());
                if (du3 != null) {
                    simulation.setService3Link(du3.getLink());
                    simulation.setService3Name(du3.getName());
                    simulation.setService3Desc(du3.getDescription());
                    simulation.setService3ImgPostal(du3.getImgPostal());
                    simulation.setService3ImgEPostal(du3.getImgEPostal());
                    simulation.setService3Code(du3.getCode());
                    validateDocumentUnit(du3.getName(),du3.getImgPostal(),du3.getImgEPostal(), SERVICIO, numberplate);
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
                    validateDocumentUnit(du4.getName(),du4.getImgPostal(),du4.getImgEPostal(), DESTAQUE, numberplate);
                }

                DocumentUnit du5 = documentUnitsMap.get(selectedDestaqueParam.getIdHighlight2());
                if (du5 != null) {
                    simulation.setHighlight2Link(du5.getLink());
                    simulation.setHighlight2Name(du5.getName());
                    simulation.setHighlight2Desc(du5.getDescription());
                    simulation.setHighlight2ImgPostal(du5.getImgPostal());
                    simulation.setHighlight2ImgEPostal(du5.getImgEPostal());
                    simulation.setHighlight2Code(du5.getCode());
                    validateDocumentUnit(du5.getName(),du5.getImgPostal(),du5.getImgEPostal(), DESTAQUE, numberplate);

                }

                simulation.setDestaqueOriginParameterization(destaqueOriginParameterization);
            }
            log.debug(TPA_DEBUG);

            if (selectedHeaderParam != null) {

                DocumentUnit du6 = documentUnitsMap.get(selectedHeaderParam.getIdHeader1());
                if (du6 != null) {
                    simulation.setHeader1Name(du6.getName());
                    simulation.setHeader1ImgPostal(du6.getImgPostal());
                    simulation.setHeader1ImgEPostal(du6.getImgEPostal());
                    simulation.setHeader1Code(du6.getCode());
                    simulation.setHeader1Link(du6.getLink());
                    validateDocumentUnit(du6.getName(),du6.getImgPostal(),du6.getImgEPostal(), HEADER, numberplate);
                }

                DocumentUnit du7 = documentUnitsMap.get(selectedHeaderParam.getIdHeader2());
                if (du7 != null) {
                    simulation.setHeader2Name(du7.getName());
                    simulation.setHeader2ImgPostal(du7.getImgPostal());
                    simulation.setHeader2ImgEPostal(du7.getImgEPostal());
                    simulation.setHeader2Code(du7.getCode());
                    simulation.setHeader2Link(du7.getLink());
                    validateDocumentUnit(du7.getName(),du7.getImgPostal(),du7.getImgEPostal(), HEADER, numberplate);

                }
                log.debug(TPA_DEBUG);

                DocumentUnit du8 = documentUnitsMap.get(selectedHeaderParam.getIdHeader3());
                if (du8 != null) {
                    simulation.setHeader3Name(du8.getName());
                    simulation.setHeader3ImgPostal(du8.getImgPostal());
                    simulation.setHeader3ImgEPostal(du8.getImgEPostal());
                    simulation.setHeader3Code(du8.getCode());
                    simulation.setHeader3Link(du8.getLink());
                    validateDocumentUnit(du8.getName(),du8.getImgPostal(),du8.getImgEPostal(),HEADER, numberplate);

                }
                log.debug(TPA_DEBUG);

                simulation.setHeaderOriginParameterization(headerOriginParameterization);
            }
            List<Acessories> acessories = null;
            if(isTPAImportFromBi){
                acessories = getAcessory(carInfo.getIdModel(), carInfo.getIdVc(), numberplate, wsCarLocation);
            }

            Acessories accessory = null;
            AcessorioGrupo kit = null;
            if (acessories != null && acessories.size() >= 2) {

                if (acessories.get(0) != null) {
                    accessory = acessories.get(0);
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());

                    simulation.setAccessory1Link(accessoryLink.getLink());
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());
                    simulation.setAccessory1Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty()?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty() ?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
                log.debug(TPA_DEBUG);

                if (acessories.get(1) != null) {
                    accessory = acessories.get(1);
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());

                    simulation.setAccessory2Link(accessoryLink.getLink());
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());

                    simulation.setAccessory2Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory2Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory2ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory2ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory2Code(kit.getReferencia());
                            simulation.setAccessory2Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty()?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory2Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory2ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory2ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory2Code(accessory.getReferencia());
                            simulation.setAccessory2Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory2Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory2ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory2ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory2Code(kit.getReferencia());
                            simulation.setAccessory2Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty()?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory2Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory2ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory2ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory2Code(accessory.getReferencia());
                            simulation.setAccessory2Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
                    Acessories accessoryLink = AcessoriesHelper.getAcessory(accessory.getIdAcessorio());
                    simulation.setAccessory1Link(accessoryLink.getLink());
                    kit  = AcessorioGrupo.getHelper().getKitByAccessoryAndIdVc(accessory.getIdAcessorio(), carInfo.getIdVc());
                    simulation.setAccessory1Name(accessory.getDescricao());
                    if(simulation.getPaData().getBrand().equals(BRAND_TOYOTA)){
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory1Link(TOYOTA_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalToyota()!=null && kit.getImgPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalToyota());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty()?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalToyota()!=null && accessory.getImgEPostalToyota()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalToyota());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalToyota());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
                        if(accessoryLink.getLink() !=null && !accessoryLink.getLink().isEmpty()){
                            simulation.setAccessory1Link(LEXUS_ACCESSORY_DEFAULT_LINK);
                        }
                        if(kit!=null && kit.getImgEPostalLexus()!=null && kit.getImgPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(kit.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(kit.getImgEPostalLexus());
                            simulation.setAccessory1Code(kit.getReferencia());
                            simulation.setAccessory1Name(kit.getDescricaoMarketing()!=null && !kit.getDescricaoMarketing().trim().isEmpty()?kit.getDescricaoMarketing():kit.getDescricao());
                            simulation.setAccessory1Desc(kit.getObservacoes());
                        }else if(accessory.getImgPostalLexus()!=null && accessory.getImgEPostalLexus()!=null){
                            simulation.setAccessory1ImgPostal(accessory.getImgPostalLexus());
                            simulation.setAccessory1ImgEPostal(accessory.getImgEPostalLexus());
                            simulation.setAccessory1Code(accessory.getReferencia());
                            simulation.setAccessory1Name(accessory.getDescricaoMarketing()!=null && !accessory.getDescricaoMarketing().trim().isEmpty()?accessory.getDescricaoMarketing():accessory.getDescricao());
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
            }else {
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

        if(name==null || name.isEmpty()){
            SCErrorException ex = new SCErrorException(PaConstants.TPA_DOCUMENT_ERROR_MESSAGE);
            log.error(ex.getErrorMessage(), ex);
            throw ex;
        }

        if(imgPostal==null || imgPostal.isEmpty()){
            SCErrorException ex = new SCErrorException(PaConstants.TPA_DOCUMENT_ERROR_MESSAGE);
            log.error(ex.getErrorMessage(), ex);
            throw ex;
        }

        if(imgEPostal==null || imgEPostal.isEmpty()){
            SCErrorException ex = new SCErrorException(PaConstants.TPA_DOCUMENT_ERROR_MESSAGE);
            log.error(ex.getErrorMessage(), ex);
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

        List<Acessories> priorityAcessories = new ArrayList<>();
        List<Integer> lstIdsModel = new ArrayList<>();
        lstIdsModel.add(idModel);

        List<AccessoryInstalled> installedAccessories = null;

        WsInvokeCarServiceTCAP invoker = new WsInvokeCarServiceTCAP(wsCarLocation);

        List<Acessories> acessories = CarHelper.getAccessoryByCommercialVersion(idVc);

        AccessoryInstalledResponse oAccessoryInstalledResponse = invoker.getInstalledAccessoriesByPlate(plate);

        if (oAccessoryInstalledResponse != null &&
                oAccessoryInstalledResponse.getAcessoryInstalled() != null &&
                !oAccessoryInstalledResponse.getAcessoryInstalled().isEmpty()) {
            installedAccessories = oAccessoryInstalledResponse.getAcessoryInstalled();
        }

        if (!lstIdsModel.isEmpty()) {

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
            if (!lstIdsAcessories.isEmpty()) {

                HashMap<Integer, String> priorityAccessories = AcessoriesHelper.getPriorityAcessories(idVc,
                        lstIdsAcessories, true, 2);
                if (!priorityAccessories.isEmpty()) {
                    int i = 0;

                    for (Map.Entry<Integer, String> entry : priorityAccessories.entrySet()) {
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

    public CarInfo getCarInfo(String numberplate, Date date, String wsCarLocation, ProgramaAvisos paData, boolean isTPAImportFromBi) throws SCErrorException, ParseException {

       try {
           CarInfo carInfo = new CarInfo();

           com.gsc.ws.core.CarInfo as400Car = null;
           Car carDBInfo1 = null;

           WsInvokeCarServiceTCAP oWsInfo = new WsInvokeCarServiceTCAP(wsCarLocation);
           CarInfoResponse oCarInfoResponse = oWsInfo.getCarByPlate(numberplate);
           RepairResponse repairs = null;
           if (isTPAImportFromBi) {
               repairs = oWsInfo.getCarRepairsByPlate(numberplate);
           }
           List<Repair> listRepairs = null;

           if (repairs != null) {
               listRepairs = repairs.getRepairInfo();
           }

           if (oCarInfoResponse != null && oCarInfoResponse.getCarInfo() != null) {
               as400Car = oCarInfoResponse.getCarInfo();
           }

           if (listRepairs != null && !listRepairs.isEmpty()) {
               Repair lastRepair = listRepairs.get(listRepairs.size() - 1);
               Dealer oDealer = ApplicationConfiguration.getDealerByDealerAndAfterSalesCode(Dealer.OID_NET_TOYOTA, lastRepair.getDealerCode(), StringTasks.cleanString(lastRepair.getAfterSalesCode(), StringUtils.EMPTY));
               if (oDealer != null) {
                   String dealerName = oDealer.getDesig();

                   String dealerLocal = "";
                   if (oDealer.getCpExt() != null && !oDealer.getCpExt().isEmpty()) {
                       dealerLocal = "(" + oDealer.getCpExt() + ")";
                   }
                   carInfo.setLastServiceDealer(dealerName + " " + dealerLocal);
                   carInfo.setLastServiceDealerContact(oDealer.getEmail() + " " + oDealer.getTel());
               }
           }
           if (as400Car != null) {
               carInfo.setAs400CarInfo(as400Car);
               String comercialModelCode = StringTasks.cleanString(as400Car.getComercialModelCode(), "");
               String versionCode = StringTasks.cleanString(as400Car.getVersionCode(), "");
               String dataMatricula = StringTasks.cleanString(as400Car.getDtPlate(), "");
               if (!dataMatricula.isEmpty()) {
                   Date date1 = new SimpleDateFormat("yyyy").parse(dataMatricula);
                   Date today = new Date();

                   Calendar a = getCalendar(date1);
                   Calendar b = getCalendar(today);
                   int carAge = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
                   if (carAge == 1) {
                       carInfo.setIdAge(1);
                   } else if (carAge == 2) {
                       carInfo.setIdAge(2);
                   } else if (carAge == 3) {
                       carInfo.setIdAge(3);
                   } else if (carAge == 4) {
                       carInfo.setIdAge(4);
                   } else if (carAge == 5) {
                       carInfo.setIdAge(5);
                   } else if (carAge == 6) {
                       carInfo.setIdAge(6);
                   } else if (carAge == 7) {
                       carInfo.setIdAge(7);
                   } else if (carAge == 8) {
                       carInfo.setIdAge(8);
                   } else if (carAge == 9) {
                       carInfo.setIdAge(9);
                   } else if (carAge == 10) {
                       carInfo.setIdAge(10);
                   } else if (carAge > 10) {
                       carInfo.setIdAge(11);
                   }
                   carInfo.setPlateDate(dataMatricula);
               } else {
                   carInfo.setIdAge(99);
               }

               carDBInfo1 = CarHelper.getCarInfo(comercialModelCode, versionCode);

               if (carDBInfo1 != null) {
                   Fuel fuel = CarHelper.getFuelsByIdVc(carDBInfo1.getIdVc());

                   if (fuel != null) {
                       carInfo.setIdFuel(fuel.getId());
                       carInfo.setFuel(fuel.getDescription());
                   }

                   carInfo.setIdVc(carDBInfo1.getIdVc());
                   carInfo.setIdModel(carDBInfo1.getModel().getId());
                   carInfo.setCar(carDBInfo1);
               }

               if (paData != null) {
                   Dealer dealer = null;
                   if (paData.getBrand().equalsIgnoreCase("L") && paData.getOidDealer() != null && !paData.getOidDealer().isEmpty()) {
                       dealer = Dealer.getLexusHelper().getByObjectId(paData.getOidDealer());
                   } else if (paData.getBrand().equalsIgnoreCase("T") && paData.getOidDealer() != null && !paData.getOidDealer().isEmpty()) {
                       dealer = Dealer.getToyotaHelper().getByObjectId(paData.getOidDealer());
                   }


                   if (dealer != null) {
                       String dealerName = dealer.getDesig();
                       String dealerLocal = "";
                       if (dealer.getCpExt() != null && !dealer.getCpExt().isEmpty()) {
                           dealerLocal = "(" + dealer.getCpExt() + ")";
                       }
                       paData.setOidDealer(dealer.getObjectId());
                       carInfo.setDealer(dealerName + " " + dealerLocal);
                   } else {
                       carInfo.setDealer("N/D");
                       paData.setOidDealer("99");
                   }


                   Calendar calDate = getCalendar(date);

                   int year = calDate.get(Calendar.YEAR);
                   int month = calDate.get(Calendar.MONTH);

                   if (paData.getYear() != year || paData.getMonth() != (month + 1)) {
                       return null;
                   }

                   Mrs mrs = paData.getMRS();

                   if (!paData.getNif().isEmpty()) {
                       String nif = paData.getNif();
                       carInfo.setNif(nif);
                       if (nif.startsWith("1") || nif.startsWith("2")) {
                           carInfo.setIdentity(2);
                       } else if (nif.startsWith("5") || nif.startsWith("6") || nif.startsWith("9")) {
                           carInfo.setIdentity(1);
                       }
                   } else {
                       carInfo.setIdentity(99);
                   }

                   carInfo.setIdContactReason(paData.getIdContactType());
                   if (mrs != null) {
                       if (mrs.getDtItv() != null) {
                           carInfo.setDtItv(mrs.getDtItv());
                       } else {
                           if (as400Car.getDtNextITV() != null) {
                               String sDate1 = as400Car.getDtNextITV();
                               Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                               carInfo.setDtItv(convertToLocalDateViaInstant(date1));
                           }
                       }

                       if (mrs.getDtNextRevision() != null) {
                           carInfo.setDtNextRevision(mrs.getDtNextRevision());
                       }

                       if (mrs.getDtLastRevision() != null) {
                           carInfo.setDtLastRevision(mrs.getDtLastRevision());

                           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                           carInfo.setDtLastRevisionString(sdf.format(mrs.getDtLastRevision()));

                           java.sql.Date dateLastRevision = java.sql.Date.valueOf(mrs.getDtLastRevision());
                           Calendar c = Calendar.getInstance();
                           c.roll(Calendar.YEAR, -2);
                           java.sql.Date dateToCompare1 = new java.sql.Date(c.getTimeInMillis());
                           c = Calendar.getInstance();
                           c.roll(Calendar.YEAR, -3);
                           java.sql.Date dateToCompare2 = new java.sql.Date(c.getTimeInMillis());
                           if (dateLastRevision.compareTo(dateToCompare1) > 0) {
                               carInfo.setIdFidelity(1);
                           } else if (dateLastRevision.compareTo(dateToCompare1) <= 0 && dateLastRevision.compareTo(dateToCompare2) >= 0) {
                               carInfo.setIdFidelity(2);
                           } else if (dateLastRevision.compareTo(dateToCompare2) < 0) {
                               carInfo.setIdFidelity(3);
                           }
                       } else {
                           carInfo.setIdFidelity(99);
                       }

                       if (mrs.getGenre() != null && !mrs.getGenre().trim().isEmpty()) {

                           String genreName = getGenreName(mrs.getGenre());
                           Genre genre = genreRepository.getGenderByName(genreName).orElseThrow(() -> new ProgramaAvisosException("Genre not found"));
                           carInfo.setGender(mrs.getGenre());
                           if (genre != null) {
                               carInfo.setIdGender(genre.getId());
                           }

                       } else {
                           carInfo.setIdGender(99);
                       }
                       if (mrs.getExpectedKm() != null && !mrs.getExpectedKm().isEmpty()) {
                           double expected = Double.valueOf(mrs.getExpectedKm());
                           carInfo.setKilometers(mrs.getExpectedKm());
                           if (expected <= 100000) {
                               carInfo.setIdKilometers(1);
                           } else if (expected > 100000 && expected <= 130000) {
                               carInfo.setIdKilometers(2);
                           } else if (expected > 130000 && expected <= 160000) {
                               carInfo.setIdKilometers(3);
                           } else if (expected > 160000 && expected <= 185000) {
                               carInfo.setIdKilometers(4);
                           } else if (expected > 185000 && expected <= 195000) {
                               carInfo.setIdKilometers(5);
                           } else if (expected > 195000 && expected <= 200000) {
                               carInfo.setIdKilometers(6);
                           } else if (expected > 200000) {
                               carInfo.setIdKilometers(7);
                           }
                       } else {
                           carInfo.setIdKilometers(99);
                       }
                   }
               }

               carInfo.setNumberPlate(numberplate);
           }
           return carInfo;
       }catch (Exception e){
           throw new ProgramaAvisosException("Car Info Error");
       }
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
    }

    public LinkedHashMap<Integer, DocumentUnit> getMapAllDocumentUnits(LinkedHashMap<Integer, DocumentUnit> map) {
        List<DocumentUnit> documentUnits = documentUnitRepository.findAll();
        documentUnits.forEach(documentUnit -> map.put(documentUnit.getId(), documentUnit));
        return map;
    }

    public static String getGenreName(String character){
        if (character.equalsIgnoreCase("M"))
            return "MASCULINO";
        return character.equalsIgnoreCase("F") ? "FEMENINO" : "N/D";
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
