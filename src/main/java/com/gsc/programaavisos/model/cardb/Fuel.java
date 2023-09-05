package com.gsc.programaavisos.model.cardb;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fuel {

    private Integer id;
    private String description;
    private Integer priority;
    private LocalDate dtStart;
    private LocalDate dtEnd;
}
