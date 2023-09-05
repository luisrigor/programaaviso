package com.gsc.programaavisos.constants;

public class PaConstants {

    public static final int POSTAL	= 1;
    public static final int EMAIL	= 2;
    public static final int SMS		= 4;
    public static final String RSM_KM_ERROR	= "Erro de previsão de Km´s";
    public static final String RSM_NOT_OWNER = "Mudança de Proprietário";
    public static final String RSM_NOT_OWNER2 = "Proprietário Desconhecido";
    public static final String RECEIVE_INFORMATION_NO = "Não";
    public static final String REMOVED_MANUALLY_DESC = "Removido da Listagem";

    public static String PA_CONTACT_CHANNEL_EPOSTAL = "E";
    public static String PA_CONTACT_CHANNEL_POSTAL = "P";
    public static String PA_CONTACT_CHANNEL_SMS = "S";
    public static int ID_EVENT_MRS = 10;
    public static int ID_ORIGIN_MRS = 9;
    public static final int    CLAIMS_PA_CHANNEL = 36;
    public static final int PARAM_ID_TPA_ITEM_TYPE_SERVICE = 1;
    public static final int PARAM_ID_TPA_ITEM_TYPE_HIGHLIGHT = 2;
    public static final int PARAM_ID_TPA_ITEM_TYPE_HEADER = 3;
    public static final String FTP_EPOSTAL_PATH = "/epostais/comum";
    public static final String FTP_POSTAL_SERVICE = "/postais/servicos";
    public static final String FTP_POSTAL_DESTAQUE = "/postais/comum";
    public static final String FTP_POSTAL_HEADER = "/postais/headers";
    public static final String ALL = "*todos*";
    public static final String PENDING_DESC	= "Por Tratar";
    public static final String EURO_CARE_TEXT	= "Eleg�vel";
    public static final String EURO_CARE_TEXT_NO	= "N�o eleg�vel";
    public static final String CAMPAIGN_N	= "N";
    public static final String CAMPAIGN_I	= "I";

    public static final int COMMERCIAL_CAMPAIGN			= 7; /*POR DEFEITO EXCLUIDO NA PESQUISA POR "TODOS"*/
    public static final int COMMERCIAL_CAMPAIGN_APV_MAP = 8; /*POR DEFEITO EXCLUIDO NA PESQUISA POR "TODOS"*/
    private static final String PARAM_MENAGE_ITEMS_NAME = "serviceName";
    public static String WS_CAR_LOCATION="https://wscar.gruposalvadorcaetano.pt";
    public static final int ECARE = 1;

    public static String FTP_MANAGE_ITEM_SERVER;
    public static String FTP_MANAGE_ITEM_PWD;
    public static String FTP_MANAGE_ITEM_ADDRESS;
    public static String FTP_MANAGE_ITEM_LOGIN;

    public static String BACKSLASH = "/";

    public final static int BUSINESS_PLUS_ID	= 2;
    public final static int NORMAL_ID	= 1;
    public static final int ID_CONTACT_TYPE_MAN = 1;
    public static final int ID_CONTACT_TYPE_ITV = 2;
    public static final int ID_CONTACT_TYPE_MAN_ITV = 3;
    public static final int ID_CONTACT_TYPE_BUS = 4;
    public final static String PARAMETRIZATION_ITEM_TYPE_DEFAULT = "D";
    public final static String PARAMETRIZATION_ITEM_TYPE_SPECIFIC = "S";
    public static final int ID_PA_PARAMETRIZATION_AGE_7_PLUS = 8;
    public static final int MAN	= 1;
    public static final int ITV	= 2;
    public static final int MAN_ITV	= 3;
    public static String TPA_DOCUMENT_ERROR_MESSAGE ="TPAInvokerSimulator.validateDocumentUnit";

}
