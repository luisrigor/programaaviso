package com.gsc.programaavisos.config.environment;

import com.gsc.ws.newsletter.invoke.WsInvokeNewsletter;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapProfileVariables {

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


    public static Map<String, String> getEnvVariablesLocal() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_STATIC_FILES_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/static_files");
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/acessorios");
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/servicos");
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/comum");
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);
        envVariables.put(CONST_WS_CAR_LOCATION, "http://localhost:10080");
        return envVariables;
    }

    public static Map<String, String> getEnvVariablesDevelopment() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_STATIC_FILES_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/static_files");
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/acessorios");
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/servicos");
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);
        envVariables.put(CONST_WS_CAR_LOCATION, "https://wscar.gruposalvadorcaetano.pt");
        return envVariables;
    }

    public static Map<String, String> getEnvVariablesStaging() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_WS_CAR_LOCATION, "https://wscar.gruposalvadorcaetano.pt");
        envVariables.put(CONST_STATIC_FILES_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/static_files");
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/acessorios");
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/servicos");
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_STAGING);

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesProduction() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrva");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_WS_CAR_LOCATION, "https://wscar.gruposalvadorcaetano.pt");
        envVariables.put(CONST_STATIC_FILES_URL, "https://w3.toyota.pt/files/programa_de_avisos/static_files");
        envVariables.put(CONST_IMG_POSTAL_ACCESSORY_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/acessorios");
        envVariables.put(CONST_IMG_POSTAL_SERVICE_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/servicos");
        envVariables.put(CONST_IMG_POSTAL_HIGHLIGHT_URL, "https://w3.toyota.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_IMG_POSTAL_HEADER_URL, "https://dev.rigorcg.pt/files/programa_de_avisos/postais/headers");
        envVariables.put(CONST_WS_NEWSLETTER_SERVER, WsInvokeNewsletter.SERVER_PRODUCTION);


        return envVariables;
    }

}
