package com.gsc.programaavisos.constants;

public class State {

    public static final int PENDING	= 1;
    public static final String PENDING_DESC	= "Por Tratar";
    public static final int HAS_SCHEDULE = 2;		//Aberto com agendamento
    public static final String HAS_SCHEDULE_DESC = "Novo Agendamento";
    public static final int SCHEDULE_DONE = 3;
    public static final String SCHEDULE_DONE_DESC = "Marca��o Efetuada";

    public static final int SCHEDULE_REJECTED = 4;
    public static final String SCHEDULE_REJECTED_DESC = "Marca��o N�o Efetuada";
    public static final int NOT_OWNER = 5;		//N�o � dono

    public static final int AST_CONTACTS_CLIENT	= 6;
    public static final String AST_CONTACTS_CLIENT_DESC	= "AST ir� contactar o cliente";

    public static final int CLIENT_SCHEDULED_AT_WORKSHOP = 7;
    public static final String CLIENT_SCHEDULED_AT_WORKSHOP_DESC = "Cliente trata com oficina";

    public static final int REMOVED_MANUALLY = 8;		//Removidos (Manualmente)
    public static final String REMOVED_MANUALLY_DESC = "Removido da Listagem";
    public static final String REMOVED_AUTO_BY_DAEMON = "Removido da Listagem";

    public static final int REMOVED_AUTO_BYMANUT = 9;
    public static final String REMOVED_AUTO_BYMANUT_DESC = "Removido autom�ticamente (Revis�o j� efetuada)";

    public static final int REMOVED_AUTO_BYPERIOD = 10;
    public static final String REMOVED_AUTO_BYPERIOD_DESC = "Removido autom�ticamente (Prazo ultrapassado)";

    public static final int UNAVAILABLE_FOR_CONTACT	= 11;
    public static final String UNAVAILABLE_FOR_CONTACT_DESC	 = "Indispon�vel para contacto";

    public static final int REMOVED_MANUALLY_DUPLICATE_MRS_IMPORT = 12;
    public static final String REMOVED_MANUALLY_DUPLICATE_MRS_IMPORT_DESC = "Removidos (Carregamento duplicado)";
    public static final String RECOVERY_AND_SHIPPING_S = "S";
    public static final String RECOVERY_AND_SHIPPING_N = "N";
}
