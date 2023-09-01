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
    public static String CONST_WS_CAR_LOCATION= "CONST_WS_CAR_LOCATION";


    public static Map<String, String> getEnvVariablesLocal() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_WS_CAR_LOCATION, "http://localhost:10080");

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesDevelopment() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrvd.sc.pt");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
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

        return envVariables;
    }

    public static Map<String, String> getEnvVariablesProduction() {
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put(CONST_FTP_MANAGE_ITEM_SERVER, "scwebsrva");
        envVariables.put(CONST_FTP_MANAGE_ITEM_LOGIN, "javauser");
        envVariables.put(CONST_FTP_MANAGE_ITEM_PWD, "java");
        envVariables.put(CONST_FTP_MANAGE_ITEM_ADDRESS, "/home/www/html/rede/files/programa_de_avisos");
        envVariables.put(CONST_WS_CAR_LOCATION, "https://wscar.gruposalvadorcaetano.pt");

        return envVariables;
    }

}
