package com.gsc.programaavisos.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TpaDTO {

    private String plate;
    private String nif;
    private LocalDate date;
}
