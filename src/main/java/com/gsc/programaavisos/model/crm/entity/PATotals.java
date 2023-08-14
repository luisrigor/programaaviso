package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PATotals {

    private int countTotal;
    private int countNotDone;
    private int countDone;
    private int withAppointment;
    private int countSchedule;
    private int countRemovedMannually;
    private int countRemovedAutoByManut;
    private int countRemovedAutoByPeriod;

}
