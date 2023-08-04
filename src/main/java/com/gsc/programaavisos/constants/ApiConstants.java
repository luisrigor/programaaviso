package com.gsc.programaavisos.constants;

import com.rg.dealer.Dealer;

public class ApiConstants {

    public static final int TOYOTA_APP = 59;
    public static final int LEXUS_APP = 10047;


    public static final int ID_BRAND_TOYOTA = 1;
    public static final int ID_BRAND_LEXUS = 2;


    public static int getIdBrand(String oidNet) {
        int idBrand = -1;
        if (oidNet.equals(Dealer.OID_NET_TOYOTA))
            idBrand = ID_BRAND_TOYOTA;
        if (oidNet.equals(Dealer.OID_NET_LEXUS))
            idBrand = ID_BRAND_LEXUS;
        return idBrand;
    }

}
