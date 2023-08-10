package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.dto.FilterBean;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

public class ProgramaAvisosData {

    public static FilterBean getFilterBean(){
        return FilterBean.builder()
                .fromYear(Year.MIN_VALUE)
                .toYear(Year.MAX_VALUE)
                .fromMonth(Month.JANUARY.getValue())
                .toMonth(Month.DECEMBER.getValue())
                .vecDealers(new ArrayList<>())
                .arrSelDealerToString("Dealers")
                .build();
    }
}
