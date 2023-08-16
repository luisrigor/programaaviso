package com.gsc.programaavisos.constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PAInfo {

    public static final String DAEMON								= "DAEMON";

    public static final String MRS_MAINTENANCE_TYPE_PRE_ITV			= "Pr�-ITV + Pack �leo e Filtro";
    public static final String MRS_MAINTENANCE_TYPE_ITV				= "ITV";

    //REVISION_SCHEDULE_MOTIVE
    public static final String RSM_SERVICE_DONE						= "Servi�o j� executado";
    public static final String RSM_SERVICE_ITV_DONE_BY_CLIENT		= "ITV efetuado pelo Cliente";
    public static final String RSM_KM_ERROR							= "Erro de previs�o de Km�s";
    public static final String RSM_SERVICE_OUTSIDE					= "N�o assiste na rede";
    public static final String RSM_SERVICE_IN_OTHER_TOYOTA_WORKSHOP	= "Assiste noutra oficina Toyota";
    public static final String RSM_NOT_OWNER						= "Mudan�a de Propriet�rio";
    public static final String RSM_NOT_OWNER2						= "Propriet�rio Desconhecido";
    public static final String RSM_BUYED							= "J� comprou";
    public static final String RSM_HIGH_PRICE						= "Pre�o elevado";
    public static final String RSM_NO_INTEREST						= "N�o valoriza";
    public static final String RSM_NO_MAINTENANCE_PLAN				= "N�o cumpre plano de manuten��o";
    public static final String RSM_NO_VALUE							= "N�o valoriza, mas vai fazer servi�o na Rede";
    public static final String RSM_OTHER_SERVICE					= "Marcou outro servi�o";

    public static final String RSM_POSTPONE							= "Faz mais tarde";
    public static final String RSM_REFUSE							= "Recusa-se a fazer";
    public static final String RSM_ALREADY_DONE						= "J� efetuou campanha";

    //REVISION_SCHEDULE_MOTIVE2
    public static final String RSM_SERVICE_OUTSIDE_UNHAPPY			= "Insatisfeito";
    public static final String RSM_SERVICE_OUTSIDE_PRICE			= "Pre�o elevado";
    public static final String RSM_SERVICE_OUTSIDE_OTHER_GARAGE		= "Tem mec�nico de confian�a";
    public static final String RSM_SERVICE_OUTSIDE_DISTANCE			= "Elevada dist�ncia at� ao Concession�rio/RTA";

    public static final String RSM_NO_INTEREST_NEVER_PROBLEMS		= "Nunca teve problemas";
    public static final String RSM_NO_INTEREST_NOT_INTERESTED		= "N�o tem interesse";

    //RECEIVE_INFORMATION_STATES
    public static final String RECEIVE_INFORMATION_YES				= "Sim";
    public static final String RECEIVE_INFORMATION_NO				= "N�o";
    public static final String RECEIVE_INFORMATION_NOT_RECEIVED		= "N�o recebeu";
    public static final String RECEIVE_INFORMATION_DONT_KNOW		= "N�o sabe";
    public static final String RECEIVE_INFORMATION_MAIL_UNCHECKED	= "N�o foi ao correio";

    public static final NumberFormat CURRENCY_FORMAT	= new DecimalFormat("#,###.00");
    public static final NumberFormat KM_FORMAT		= new DecimalFormat("#,###,###");

    private static final String BRAND_TOYOTA = "T";

    private static final String BRAND_LEXUS = "L";

}
