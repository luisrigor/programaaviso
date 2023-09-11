package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import java.sql.Date;
import java.time.LocalDate;

public class ParametrizationData {

    public static PaParameterization getPaParameterization(){
        return PaParameterization.builder()
                .id(OtherFlowData.RANDOM_ID)
                .dtStart(Date.valueOf(LocalDate.MIN))
                .dtEnd(Date.valueOf(LocalDate.MAX))
                .comments("comments")
                .build();
    }
}
