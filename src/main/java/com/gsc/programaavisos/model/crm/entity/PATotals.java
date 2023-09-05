package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PATotals {

    private Integer total;
    private Integer notDone;
    private Integer done;
    private Integer withAppointment;
    private Integer schedule;
    private Integer removedMannually;
    private Integer removedAutoByManut;
    private Integer removedAutoByPeriod;

}
