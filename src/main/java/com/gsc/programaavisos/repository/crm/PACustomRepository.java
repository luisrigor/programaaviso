package com.gsc.programaavisos.repository.crm;

import java.util.List;
import java.util.Map;

public interface PACustomRepository {

    List<String> getDelegators(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer);

    Map<String, String> getLastChangedBy(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer);
}
