package com.gsc.programaavisos.config.environment;

import com.gsc.ws.newsletter.invoke.WsInvokeNewsletter;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapProfileVariables {

    public static final String CONST_CONSENT_CENTER_URL = "CONSENT_CENTER_URL";
    public static final String CONST_AS400_WEBSERVICE_ADDRESS = "AS400_WEBSERVICE_ADDRESS";
    public static final String CONST_FTP_MANAGE_ITEM_SERVER = "FTP_MANAGE_ITEM_SERVER";
    public static final String CONST_FTP_MANAGE_ITEM_LOGIN = "FTP_MANAGE_ITEM_LOGIN";
    public static final String CONST_FTP_MANAGE_ITEM_PWD = "FTP_MANAGE_ITEM_PWD";
    public static final String CONST_FTP_MANAGE_ITEM_ADDRESS = "FTP_MANAGE_ITEM_ADDRESS";
    public static final String CONST_STATIC_FILES_URL = "STATIC_FILES_URL";
    public static final String CONST_IMG_POSTAL_ACCESSORY_URL = "IMG_POSTAL_ACCESSORY_URL";
    public static final String CONST_IMG_POSTAL_SERVICE_URL = "IMG_POSTAL_SERVICE_URL";
    public static final String CONST_IMG_POSTAL_HIGHLIGHT_URL = "IMG_POSTAL_HIGHLIGHT_URL";
    public static final String CONST_IMG_POSTAL_HEADER_URL = "IMG_POSTAL_HEADER_URL";
    public static final String CONST_WS_NEWSLETTER_SERVER = "WS_NEWSLETTER_SERVER";
    public static final String  CONST_WS_CAR_LOCATION= "CONST_WS_CAR_LOCATION";
    public static final String CONST_FTP_MANAGE_ITEM_SERVER_VALUE= "scwebsrvd.sc.pt";
    public static final String CONST_FTP_MANAGE_ITEM_LOGIN_VALUE= "javauser";
    public static final String CONST_FTP_MANAGE_ITEM_JAVA_VALUE= "java";
    public static final String CONST_FTP_MANAGE_ITEM_ADDRESS_VALUE ="/home/www/html/rede/files/programa_de_avisos";
    public static final String CONST_STATIC_FILES_URL_VALUE = "https://dev.rigorcg.pt/files/programa_de_avisos/static_files";
    public static final String CONST_IMG_POSTAL_ACCESSORY_URL_VALUE= "https://dev.rigorcg.pt/files/programa_de_avisos/postais/acessorios";
    public static final String CONST_IMG_POSTAL_SERVICE_URL_VALUE= "https://dev.rigorcg.pt/files/programa_de_avisos/postais/servicos";
    public static final String CONST_IMG_POSTAL_HIGHLIGHT_URL_VALUE= "https://dev.rigorcg.pt/files/programa_de_avisos/postais/comum";
    public static final String CONST_IMG_POSTAL_HEADER_URL_VALUE= "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers";
    private static final String WS_CAR_LOCATION = "https://wscar.gruposalvadorcaetano.pt";


    public static Map<String, String> getEnvVariablesLocal() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, CONST_FTP_MANAGE_ITEM_SERVER_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, CONST_FTP_MANAGE_ITEM_LOGIN_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, CONST_FTP_MANAGE_ITEM_JAVA_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, CONST_FTP_MANAGE_ITEM_ADDRESS_VALUE);
        envVariables.put(CONST_STATIC_FILES_URL,CONST_STATIC_FILES_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, CONST_IMG_POSTAL_ACCESSORY_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL,CONST_IMG_POSTAL_SERVICE_URL_VALUE );
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, CONST_IMG_POSTAL_HIGHLIGHT_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, CONST_IMG_POSTAL_HEADER_URL_VALUE);
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);
        envVariables.put(CONST_WS_CAR_LOCATION, "http://localhost:10080");
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);
        return envVariables;
    }

    public static Map<String, String> getEnvVariablesDevelopment() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, CONST_FTP_MANAGE_ITEM_SERVER_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, CONST_FTP_MANAGE_ITEM_LOGIN_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, CONST_FTP_MANAGE_ITEM_JAVA_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, CONST_FTP_MANAGE_ITEM_ADDRESS_VALUE);
        envVariables.put(CONST_STATIC_FILES_URL, CONST_STATIC_FILES_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, CONST_IMG_POSTAL_ACCESSORY_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, CONST_IMG_POSTAL_SERVICE_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, CONST_IMG_POSTAL_HIGHLIGHT_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, CONST_IMG_POSTAL_HEADER_URL_VALUE);
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);
        envVariables.put(CONST_WS_CAR_LOCATION, WS_CAR_LOCATION);
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);
        return envVariables;
    }

    public static Map<String, String> getEnvVariablesStaging() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, CONST_FTP_MANAGE_ITEM_SERVER_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, CONST_FTP_MANAGE_ITEM_LOGIN_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, CONST_FTP_MANAGE_ITEM_JAVA_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, CONST_FTP_MANAGE_ITEM_ADDRESS_VALUE);
        envVariables.put(CONST_STATIC_FILES_URL, CONST_STATIC_FILES_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, CONST_IMG_POSTAL_ACCESSORY_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, CONST_IMG_POSTAL_SERVICE_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, CONST_IMG_POSTAL_HIGHLIGHT_URL_VALUE);
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, CONST_IMG_POSTAL_HEADER_URL_VALUE);
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);
        envVariables.put(CONST_WS_CAR_LOCATION, WS_CAR_LOCATION);
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_STAGING_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_STAGING_HTTPS);
        return envVariables;
    }

    public static Map<String, String> getEnvVariablesProduction() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrva");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, CONST_FTP_MANAGE_ITEM_LOGIN_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, CONST_FTP_MANAGE_ITEM_JAVA_VALUE);
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, CONST_FTP_MANAGE_ITEM_ADDRESS_VALUE);
        envVariables.put(CONST_STATIC_FILES_URL, "https://w3.toyota.pt/files/programa_de_avisos/static_files");
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/acessorios");
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/servicos");
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_PRODUCTION);
        envVariables.put(CONST_WS_CAR_LOCATION, WS_CAR_LOCATION);
        envVariables.put(CONST_CONSENT_CENTER_URL, com.gsc.consent.util.DATA.CONSENT_CENTER_URL_PRODUCTION_HTTPS);
        envVariables.put(CONST_AS400_WEBSERVICE_ADDRESS, com.gsc.ws.util.DATA.SERVER_PRODUCTION_HTTPS);
        return envVariables;
    }

}
