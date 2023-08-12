package com.gsc.programaavisos.dto;


import com.rg.dealer.Dealer;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterBean {

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


}
