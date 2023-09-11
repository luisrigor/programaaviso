package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.crm.entity.TechnicalCampaigns;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PACampaing {

    private ProgramaAvisos pa;
    private TechnicalCampaigns tc;
}
