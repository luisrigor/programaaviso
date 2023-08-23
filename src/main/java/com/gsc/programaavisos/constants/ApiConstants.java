package com.gsc.programaavisos.constants;

import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.ServerTasks;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiConstants {

    public static final int TOYOTA_APP = 59;
    public static final int LEXUS_APP = 10047;


    public static final int ID_BRAND_TOYOTA = 1;
    public static final int ID_BRAND_LEXUS = 2;

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;

    public static final String PRODUCTION_SERVER_STR = "production";




    public static int getIdBrand(String oidNet) {
        int idBrand = -1;
        if (oidNet.equals(Dealer.OID_NET_TOYOTA))
            idBrand = ID_BRAND_TOYOTA;
        if (oidNet.equals(Dealer.OID_NET_LEXUS))
            idBrand = ID_BRAND_LEXUS;
        return idBrand;
    }



}
