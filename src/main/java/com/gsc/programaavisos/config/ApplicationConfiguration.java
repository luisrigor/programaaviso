package com.gsc.programaavisos.config;

import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;

import java.util.Hashtable;
import java.util.Map;

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
}
