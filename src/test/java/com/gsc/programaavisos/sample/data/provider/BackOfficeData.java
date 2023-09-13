package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.dto.PACampaing;
import com.gsc.programaavisos.model.crm.entity.TechnicalCampaigns;
import org.apache.commons.lang3.StringUtils;

public class BackOfficeData {

    public static TechnicalCampaigns getTechnicalCampaign() {
        return TechnicalCampaigns.builder()
                .stErrorImport(new StringBuilder(StringUtils.EMPTY))
                .build();
    }

    public static PACampaing getPACampaign(){
        return PACampaing.builder()
                .pa(ProgramaAvisosData.getCompletePA())
                .tc(getTechnicalCampaign())
                .build();
    }
}
