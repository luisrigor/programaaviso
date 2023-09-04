package com.gsc.programaavisos.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
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
