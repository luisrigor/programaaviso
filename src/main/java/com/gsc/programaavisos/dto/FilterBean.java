package com.gsc.programaavisos.dto;


import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.State;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.DateTimerTasks;
import com.sc.commons.utils.StringTasks;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterBean implements Cloneable{

    //1� Linha
    private int fromYear;
    private int toYear;
    private int fromMonth;
    private int toMonth;
    //	private String ivOidsDealer;
    private List<Dealer> vecDealers;
    private String[] arrSelDealer;
    private String arrSelDealerToString;
    private LinkedHashMap<String, String> changedList;//1
    private String changedBy;

    private String GSCUserLogin;


    //2� Linha
//    private List<Source> ivSourceList;
    private int idSource;
//    private List<Channel> ivChannelList;
    private int idChannel;
//    private List<ContactType> ivContactTypeList;
    private int idContactType;
    private String flagHibrid;
    private List<String> delegators;
    private String delegatedTo;

    //3� Linha
    private int statePending;
    private int stateHasSchedule;
    private int stateScheduleDone;
    private int stateScheduleRejected;
    private int stateNotOwner;
    private int stateAstContactsClient;
    private int stateClientScheduledAtWorkshop;
    private int stateShowRemovedManually;
    private int stateShowRemovedAutoByManut;
    private int stateShowRemovedAutoByPeriod;
    private String[] arrSelMaintenanceTypes;
    private String arrSelMaintenanceTypesToString;
    private String hasMaintenanceContract;
    private String missedCalls;
    private String plate;

    //4� Linha
    private String flag5Plus;
//    private List<ClientType> clientTypeList;
    private int idClientType;

    //Outros
    private boolean showImportByExcell;
    private int firstPage;
    private int currPage;
    private int lastPage;
    private String orderColumn;
    private String orderOrientation;

    // OWNER
    private String owner;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void clearState() {
        setStatePending(0);
        setStateHasSchedule(0);
        setStateScheduleDone(0);
        setStateScheduleRejected(0);
        setStateNotOwner(0);
        setStateAstContactsClient(0);
        setStateClientScheduledAtWorkshop(0);
        setStateShowRemovedManually(0);
        setStateShowRemovedAutoByManut(0);
        setStateShowRemovedAutoByPeriod(0);
    }



    public String getWhereClause(Map<Integer, List<String>> getMaintenanceTypesByContactType) throws Exception {
        return createWhereClause(true, getMaintenanceTypesByContactType);
    }

    private String createWhereClause(boolean withStateCondition, Map<Integer, List<String>> getMaintenanceTypesByContactType) throws Exception {

        StringBuffer filter = new StringBuffer();
        filter.append (" WHERE PA_VISIBLE = 'S' AND PA_DT_VISIBLE <= CURRENT DATE ");

        //Excluir registos marcados como estado 12 "carregamento duplicado"
        filter.append (" AND PA_ID_STATUS != 12 ");

        //---------------------  1� Linha

        Calendar calStart = Calendar.getInstance();		calStart.clear();
        calStart.set(Calendar.YEAR, getFromYear());		calStart.set(Calendar.MONTH, getFromMonth()-1);			calStart.set(Calendar.DAY_OF_MONTH, 1);

        Calendar calTo 	= Calendar.getInstance();		calTo.clear();
        calTo.set(Calendar.YEAR, getToYear());		calTo.set(Calendar.MONTH, getToMonth());					calTo.set(Calendar.DAY_OF_MONTH, 1);
        calTo.add(Calendar.DAY_OF_MONTH, -1);

        //FT - Corrigido em 29/Maio
        filter.append(" AND ( ");
        filter.append(" ( PA_ID_CONTACTTYPE != " + ApiConstants.PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS + " AND "
                + "DATE(PA_YEAR ||'-' || RIGHT('00'||PA_MONTH, 2) || '-01') BETWEEN {d '"+ DateTimerTasks.fmtDT.format(calStart.getTime())+"'} AND {d '"+DateTimerTasks.fmtDT.format(calTo.getTime())+"'}"
                + ")" );

        filter.append(" or ( PA_ID_CONTACTTYPE = " + ApiConstants.PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS + " AND "
                + " DATE(MC_DT_FINISH_CONTRACT) BETWEEN {d '"+DateTimerTasks.fmtDT.format(calStart.getTime())+"'} AND {d '"+DateTimerTasks.fmtDT.format(calTo.getTime())+"'}"
                + " )"
                + ")");

        if(getArrSelDealer() != null)
            filter.append(" AND PA_OID_DEALER IN (" + arrSelDealerToStringM() + ") ");

        if(getChangedBy() != null )
            if(!getChangedBy().equalsIgnoreCase("*todos*")) {
                if (getChangedBy().equalsIgnoreCase("*ninguem*")) {
                    filter.append (" AND VALUE(PA_CHANGED_BY, '') = '' ");
                } else {
                    filter.append (" AND PA_CHANGED_BY = '" + getChangedBy() + "' ");
                }
            }

        //---------------------  2� Linha
        if(getIdSource() >0)
            filter.append(" AND PA_ID_SOURCE = " + getIdSource() + " ");

        if(getIdChannel() >0)
            filter.append(" AND PA_ID_CHANNEL = " + getIdChannel() + " ");

        if(getIdContactType() >0){
            filter.append(" AND PA_ID_CONTACTTYPE = " + getIdContactType() + " ");
        } else {
            filter.append(" AND ("
                    + "	PA_ID_CONTACTTYPE != " + ContactTypeB.COMMERCIAL_CAMPAIGN + " "
                    + "	AND PA_ID_CONTACTTYPE != " + ContactTypeB.COMMERCIAL_CAMPAIGN_APV_MAP + " "
                    + " ) ");
        }

        if(! StringTasks.cleanString(getFlagHibrid(), "").equals("")) {
            if (getFlagHibrid().equals("YES_WITH_ACTIVE_HHC"))
                filter.append(" AND MRS_FLAG_HYBRID = 'S' AND CURRENT DATE BETWEEN HHC.CONTRACT_START_DATE AND HHC.CONTRACT_END_DATE ");
            else if (getFlagHibrid().equals("YES_WITHOUT_ACTIVE_HHC"))
                filter.append(" AND MRS_FLAG_HYBRID = 'S' AND CURRENT DATE NOT BETWEEN HHC.CONTRACT_START_DATE AND HHC.CONTRACT_END_DATE ");
            else if (getFlagHibrid().equals("N"))
                filter.append(" AND MRS_FLAG_HYBRID = 'N'");
        }

        if(getDelegatedTo() != null ) {
            if(! getDelegatedTo().equalsIgnoreCase("*todos*")) {
                filter.append (" AND UPPER(PA_DELEGATED_TO) = '" + getDelegatedTo().toUpperCase()+ "' ");
            }
        }

        //---------------------  3� Linha
        if (withStateCondition) {
            // Checkboxes
            //NOTA: Qualquer altera��o aqui tem de ser efetuada na view BI_PADATA
            if(	statePending + stateHasSchedule + stateScheduleDone + stateScheduleRejected + stateNotOwner + stateAstContactsClient + stateClientScheduledAtWorkshop + stateShowRemovedManually + stateShowRemovedAutoByManut + stateShowRemovedAutoByPeriod > 0) {
                StringBuffer stateCondition = new StringBuffer("-1,");
                if(statePending > 0) {
                    stateCondition.append(State.PENDING + ",");
                }
                if(stateHasSchedule > 0) {
                    stateCondition.append(State.HAS_SCHEDULE + ",");
                }
                if(stateScheduleDone > 0){
                    stateCondition.append(State.SCHEDULE_DONE + ",");
                }
                if(stateScheduleRejected > 0) {
                    stateCondition.append(State.SCHEDULE_REJECTED + ",");
                }
                if(stateNotOwner > 0) {
                    stateCondition.append(State.NOT_OWNER + ",");
                }
                if(stateAstContactsClient > 0) {
                    stateCondition.append(State.AST_CONTACTS_CLIENT + ",");
                }
                if(stateClientScheduledAtWorkshop > 0) {
                    stateCondition.append(State.CLIENT_SCHEDULED_AT_WORKSHOP + ",");
                }
                if(stateShowRemovedManually > 0) {
                    stateCondition.append(State.REMOVED_MANUALLY + ",");
                }
                if(stateShowRemovedAutoByManut > 0) {
                    stateCondition.append(State.REMOVED_AUTO_BYMANUT + ",");
                }
                if(stateShowRemovedAutoByPeriod > 0) {
                    stateCondition.append(State.REMOVED_AUTO_BYPERIOD + ",");
                }
                if (!stateCondition.toString().equals("-1,")) {
                    stateCondition.deleteCharAt(stateCondition.length()-1);
                    filter.append("AND PA_ID_STATUS IN (" + stateCondition.toString() + ") ");
                }
            } else {
                filter.append(" AND 1 = 2 ");
            }
        }

        if(getIdContactType() > 0 && getMaintenanceTypesByContactType.containsKey(new Integer(getIdContactType())))
            filter.append(" AND MRS_NEXT_REVISION IN (" + arrSelMaintenanceTypesToStringM() + ") ");

        if(! StringTasks.cleanString(getHasMaintenanceContract(), "").equals(""))
            filter.append(" AND MRS_FLAG_MAINTENANCE_CONTRACT = '" + getHasMaintenanceContract() + "' ");

        if(!getMissedCalls().equalsIgnoreCase("*todos*")) {
            if(getMissedCalls().equalsIgnoreCase("*nenhuma*"))
                filter.append(" AND PA_NR_MISSED_CALLS = 0 ");
            else if(getMissedCalls().equalsIgnoreCase("*1*"))
                filter.append(" AND PA_NR_MISSED_CALLS = 1 ");
            else if(getMissedCalls().equalsIgnoreCase("*2*"))
                filter.append(" AND PA_NR_MISSED_CALLS = 2 ");
            else if(getMissedCalls().equalsIgnoreCase("*3ouMais*"))
                filter.append(" AND PA_NR_MISSED_CALLS >= 3 ");
        }
        if(! StringTasks.cleanString(getPlate(), "").equals(""))
            filter.append(" AND PA_LICENSE_PLATE = '" +  StringTasks.ReplaceStr(getPlate(), "-", "").toUpperCase().trim() + "' ");

        //---------------------  4� Linha
        if(! StringTasks.cleanString(getFlag5Plus(), "").equals(""))
            filter.append(" AND MRS_FLAG_5_PLUS = '" + getFlag5Plus() + "' ");

        if(getIdClientType() >0)
            filter.append(" AND PA_ID_CLIENT_TYPE = " + getIdClientType() + " ");

        // Search the type of consent
        if(!StringUtils.isEmpty(getOwner()))
            filter.append(" AND PA_DATA_INFO.PA_OWNER = '" + getOwner() + "' ");

        //---------------------
        return filter.toString();
    }


    private String arrSelDealerToStringM() {
        if (arrSelDealerToString==null) {
            StringBuffer strOIDs = new StringBuffer("'DUMMY'");
            if(arrSelDealer!= null) {
                for (int i = 0; i < arrSelDealer.length; i++) {
                    strOIDs.append(",'" + arrSelDealer[i] +"' ");
                }
            }
            arrSelDealerToString = strOIDs.toString();
        }
        return arrSelDealerToString;
    }

    private String arrSelMaintenanceTypesToStringM() {
        if (arrSelMaintenanceTypesToString==null) {
            StringBuffer strMaintenanceTypes = new StringBuffer("' '");
            if(arrSelMaintenanceTypes!= null) {
                for (int i = 0; i < arrSelMaintenanceTypes.length; i++) {
                    strMaintenanceTypes.append(",'" + arrSelMaintenanceTypes[i] +"' ");
                }
            }
            arrSelMaintenanceTypesToString = strMaintenanceTypes.toString();
        }
        return arrSelMaintenanceTypesToString;
    }

    public String getOrderBy() {
        String orderBy = "";
        if (StringTasks.cleanString(getOrderColumn(), "").equals("")) {
            //orderBy = "ORDER BY CASE WHEN PA_DT_SCHEDULE_CONTACT > CURRENT DATE THEN VARCHAR(PA_DT_SCHEDULE_CONTACT) ELSE CASE WHEN PA_DT_SCHEDULE_CONTACT <= CURRENT DATE THEN PA_DT_SCHEDULE_CONTACT || PA_HR_SCHEDULE_CONTACT ELSE VARCHAR(CURRENT DATE) || '23_59' END END, MRS_FLAG_5_PLUS DESC";
            // S221222_0035
            orderBy = "ORDER BY CASE WHEN PA_DT_SCHEDULE_CONTACT > CURRENT DATE THEN VARCHAR(PA_DT_SCHEDULE_CONTACT) ELSE CASE WHEN PA_DT_SCHEDULE_CONTACT <= CURRENT DATE THEN PA_DT_SCHEDULE_CONTACT || PA_HR_SCHEDULE_CONTACT ELSE VARCHAR(CURRENT DATE) || '23_59' END END, MRS_FLAG_5_PLUS DESC";
            //orderBy = "ORDER BY PA_DT_CREATED";
        } else {
            if (getOrderColumn().equalsIgnoreCase("HHC.CONTRACT_END_DATE")) {
                if (getOrderOrientation().equals("ASC"))
                    orderBy = "ORDER BY COALESCE(HHC.CONTRACT_END_DATE, '9999-12-31') ASC";
                else
                    orderBy = "ORDER BY COALESCE(HHC.CONTRACT_END_DATE, '1900-01-01') DESC";
            } else
                orderBy = "ORDER BY " + getOrderColumn() + " " + getOrderOrientation();
        }
        return orderBy;
    }




}
