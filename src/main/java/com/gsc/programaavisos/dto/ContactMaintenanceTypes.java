package com.gsc.programaavisos.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMaintenanceTypes {

    private Integer id;
    private String contractType;
    private String maintenanceType;

}
