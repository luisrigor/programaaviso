package com.gsc.programaavisos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceTypeDTO {

    private Integer id;
    private String contractType;
    private String maintenanceType;
}
