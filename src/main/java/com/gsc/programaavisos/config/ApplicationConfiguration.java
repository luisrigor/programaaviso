package com.gsc.programaavisos.config;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.service.ParametrizationService;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;



@Getter
public class ApplicationConfiguration {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PA_CONTACTTYPE_CONECTIVIDADE = 9;
    public static Map<String, Dealer> MAP_DEALERS = null;
    private static Map<String, Dealer> MAP_DEALERS_DEALER_AFTERSALESCODE = null;

    public static Dealer getDealer(String oidDealer) throws SCErrorException {
        initializeDealerCodeMap();
        return MAP_DEALERS.containsKey(oidDealer) ? MAP_DEALERS.get(oidDealer): null;
    }
    public static final String CHAR_SEPARATOR = "#";

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
