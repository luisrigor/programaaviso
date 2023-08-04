package com.gsc.programaavisos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchPADTO {

    int fromYear;
    int fromMonth;
    int toYear;
    int toMonth;
    String[] arrOidDealer;
    String changedBy;
    int idSource;
    int idChannel;
    int idContactType;
    String flagHibrid;
    String delegatedTo;
    String[] filterOptions;
    String[] arrMaintenanceTypes;
    String hasMaintenanceContract;
    String mrsMissedCalls;
    String plate;
    String flag5Plus;
    int idClientType;
    String showImportByExcell;
    String filterOwner;
}
