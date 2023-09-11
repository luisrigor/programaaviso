package com.gsc.programaavisos.config.environment;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapProfileVariables {

    public static String CONST_FTP_MANAGE_ITEM_SERVER = "FTP_MANAGE_ITEM_SERVER";
    public static String CONST_FTP_MANAGE_ITEM_LOGIN = "FTP_MANAGE_ITEM_LOGIN";
    public static String CONST_FTP_MANAGE_ITEM_PWD = "FTP_MANAGE_ITEM_PWD";
    public static String CONST_FTP_MANAGE_ITEM_ADDRESS = "FTP_MANAGE_ITEM_ADDRESS";
    public static String CONST_CONSENT_CENTER_URL = "CONSENT_CENTER_URL";
    public static String CONST_AS400_WEBSERVICE_ADDRESS = "AS400_WEBSERVICE_ADDRESS";


    public static Map<String, String> getEnvVariablesLocal() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesDevelopment() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesStaging() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesProduction() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrva");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_PRODUCTION_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_PRODUCTION_HTTPS);

        return envVariables;
    }

}
