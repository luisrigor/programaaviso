package com.gsc.programaavisos.config;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.ItemsKilometersRepository;
import com.gsc.programaavisos.service.impl.ParametrizationServiceImpl;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class ApplicationConfiguration {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PA_CONTACTTYPE_CONECTIVIDADE = 9;
    public static Map<String, Dealer> MAP_DEALERS = null;
    private static Map<String, Dealer> MAP_DEALERS_DEALER_AFTERSALESCODE = null;
    
    private static ParametrizationServiceImpl parametrizationService;

    public static Dealer getDealer(String oidDealer) throws SCErrorException {
        initializeDealerCodeMap();
        return MAP_DEALERS.containsKey(oidDealer) ? MAP_DEALERS.get(oidDealer): null;
    }

    private static ApplicationConfiguration ivInstance = null;
    protected static Calendar ivDtLastGeneratedValues = null;

    public boolean PARAM_CHANGE = false;
    private Date DT_START = null;
    private Date DT_END = null;
    private Map<Integer, List<PaParameterization>> MAP_PARAMETERIZATIONS = null;
    public static final String CHAR_SEPARATOR = "#";

    public synchronized static ApplicationConfiguration getInstance() {
        if (ivInstance == null || ivDtLastGeneratedValues==null) {
            ivDtLastGeneratedValues = Calendar.getInstance();
            ivInstance = new ApplicationConfiguration();
        } else {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.DAY_OF_MONTH) != ivDtLastGeneratedValues.get(Calendar.DAY_OF_MONTH)) {
                ivDtLastGeneratedValues = Calendar.getInstance();
                ivInstance = new ApplicationConfiguration();
            }
        }
        return ivInstance;
    }

    public List<PaParameterization> getParameterizationsByClient(int idClient, ParameterizationFilter filter) throws SCErrorException {

        if((PARAM_CHANGE)||(DT_START == null && MAP_PARAMETERIZATIONS == null && DT_END == null) ||
                (filter.getDtStart().before(DT_START) || filter.getDtStart().after(DT_START) || filter.getDtEnd().before(DT_END)
                        || filter.getDtEnd().after(DT_END) || MAP_PARAMETERIZATIONS==null)){
            MAP_PARAMETERIZATIONS = parametrizationService.getByIdClient(filter, true);
            DT_START = filter.getDtStart();
            DT_END = filter.getDtEnd();
            PARAM_CHANGE = false;
        }
        return MAP_PARAMETERIZATIONS.getOrDefault(idClient, null);
    }



    public static Dealer getDealerByDealerAndAfterSalesCode(String oidNet, String dealerCode, String afterSalesCode) throws SCErrorException {
        String key = oidNet + CHAR_SEPARATOR + dealerCode + CHAR_SEPARATOR + afterSalesCode;
        Dealer dealer = MAP_DEALERS_DEALER_AFTERSALESCODE.get(key);
        if (dealer == null) {
            initializeDealerCodeMap();
            dealer = MAP_DEALERS_DEALER_AFTERSALESCODE.get(key);
        }
        return dealer;
    }

    private static void initializeDealerCodeMap() throws SCErrorException {
        if (MAP_DEALERS_DEALER_AFTERSALESCODE == null) {
            MAP_DEALERS_DEALER_AFTERSALESCODE = new Hashtable<>();
            if (MAP_DEALERS == null) {
                MAP_DEALERS = new Hashtable<>();
                MAP_DEALERS.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_TOYOTA));
                MAP_DEALERS.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_LEXUS));
            }
            MAP_DEALERS.forEach((s, oDealer) -> MAP_DEALERS_DEALER_AFTERSALESCODE.put(
                    oDealer.getOIdNet() + CHAR_SEPARATOR + oDealer.getDealerCode() + CHAR_SEPARATOR + oDealer.getAfterSalesCode(),
                    oDealer));
        }
    }
}
