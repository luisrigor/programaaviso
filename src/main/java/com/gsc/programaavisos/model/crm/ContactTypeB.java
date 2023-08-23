package com.gsc.programaavisos.model.crm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactTypeB {

    public static final int MAN							= 1;
    public static final int ITV							= 2;
    public static final int MAN_ITV						= 3;
    public static final int EXTRACARE_PLUS				= 4;
    public static final int MAINTENANCE_CONTRACT		= 5;
    public static final int TECHNICAL_CAMPAIGN			= 6;
    public static final int COMMERCIAL_CAMPAIGN			= 7; /*POR DEFEITO EXCLUIDO NA PESQUISA POR "TODOS"*/
    public static final int COMMERCIAL_CAMPAIGN_APV_MAP = 8; /*POR DEFEITO EXCLUIDO NA PESQUISA POR "TODOS"*/
    public final static int CONNECTIVITY 				= 9; /*POR DEFEITO EXCLUIDO NA PESQUISA POR "TODOS"*/

    protected int id;
    protected String name;
    protected String status;
    protected String orderColumn;
    protected String orderColumnDescription;
}
