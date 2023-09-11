package com.gsc.programaavisos.config;

import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import lombok.Getter;

import java.util.Hashtable;
import java.util.Map;



@Getter
public class ApplicationConfiguration {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PA_CONTACTTYPE_CONECTIVIDADE = 9;
    private static Map<String, Dealer> mapDealers;
    private static Map<String, Dealer> mapDealersDealerAfterSalesCode;

    public static Dealer getDealer(String oidDealer) throws SCErrorException {
        initializeDealerCodeMap();
        return mapDealers.getOrDefault(oidDealer, null);
    }
    public static final String CHAR_SEPARATOR = "#";

    public static Dealer getDealerByDealerAndAfterSalesCode(String oidNet, String dealerCode, String afterSalesCode) throws SCErrorException {
        String key = oidNet + CHAR_SEPARATOR + dealerCode + CHAR_SEPARATOR + afterSalesCode;
        Dealer dealer = mapDealersDealerAfterSalesCode.get(key);
        if (dealer == null) {
            initializeDealerCodeMap();
            dealer = mapDealersDealerAfterSalesCode.get(key);
        }
        return dealer;
    }

    private static void initializeDealerCodeMap() throws SCErrorException {
        if (mapDealersDealerAfterSalesCode == null) {
            mapDealersDealerAfterSalesCode = new Hashtable<>();
            if (mapDealers == null) {
                mapDealers = new Hashtable<>();
                mapDealers.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_TOYOTA));
                mapDealers.putAll(Dealer.getHelper().getAllDealers(Dealer.OID_NET_LEXUS));
            }
            mapDealers.forEach((s, oDealer) -> mapDealersDealerAfterSalesCode.put(
                    oDealer.getOIdNet() + CHAR_SEPARATOR + oDealer.getDealerCode() + CHAR_SEPARATOR + oDealer.getAfterSalesCode(),
                    oDealer));
        }
    }
}
