package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.crm.entity.PATotals;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PAInfoDTO {

    private List<ProgramaAvisosBean> paInfoList;
    private PATotals paTotals;
    private FilterBean filterBean;

}
