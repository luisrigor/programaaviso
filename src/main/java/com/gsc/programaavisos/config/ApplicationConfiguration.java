package com.gsc.programaavisos.config;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;

import java.util.*;

public class ApplicationConfiguration {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PA_CONTACTTYPE_CONECTIVIDADE = 9;
    public static Map<String, Dealer> MAP_DEALERS = null;

    public static Dealer getDealer(String oidDealer) throws SCErrorException {
        if (MAP_DEALERS==null) {
            // Get all information about Dealers
            MAP_DEALERS = new Hashtable<String, Dealer>();
            MAP_DEALERS.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_TOYOTA));
            MAP_DEALERS.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_LEXUS));
        }
        return MAP_DEALERS.containsKey(oidDealer) ? MAP_DEALERS.get(oidDealer): null;
    }

    private static ApplicationConfiguration ivInstance = null;
    protected static Calendar ivDtLastGeneratedValues = null;

    public boolean PARAM_CHANGE = false;
    private Date DT_START = null;
    private Date DT_END = null;
    private static Map<Integer, List<PaParameterization>> MAP_PARAMETERIZATIONS = null;

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
/*
    public List<PaParameterization> getParameterizationsByClient(int idClient, ParameterizationFilter filter) throws SCErrorException {

        if((PARAM_CHANGE)||(DT_START == null && MAP_PARAMETERIZATIONS == null && DT_END == null) ||   (filter.getDtStart().before(DT_START) || filter.getDtStart().after(DT_START) || filter.getDtEnd().before(DT_END) || filter.getDtEnd().after(DT_END) || MAP_PARAMETERIZATIONS==null)){
            MAP_PARAMETERIZATIONS = PaParameterization.getHelper().getByIdClient(filter, true);
            DT_START = filter.getDtStart();
            DT_END = filter.getDtEnd();
            PARAM_CHANGE = false;
        }
        return MAP_PARAMETERIZATIONS.containsKey(idClient) ? MAP_PARAMETERIZATIONS.get(idClient): null;
    }
 */
}
