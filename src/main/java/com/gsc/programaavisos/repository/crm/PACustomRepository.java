package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.FilterBean;
import com.gsc.programaavisos.dto.MaintenanceTypeDTO;
import com.gsc.programaavisos.dto.ProgramaAvisosBean;

import java.util.List;
import java.util.Map;

public interface PACustomRepository {

    List<String> getDelegators(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer);

    Map<String, String> getLastChangedBy(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer);

    List<MaintenanceTypeDTO> getMaintenanceTypes();

    List<ProgramaAvisosBean> getOpenContactsforClient(FilterBean oFilter, String nif, String licencePlate,Map<Integer, List<String>> getMaintenanceTypesByContactType);
    ProgramaAvisosBean getProgramaAvisosById(Integer idPA);
}
