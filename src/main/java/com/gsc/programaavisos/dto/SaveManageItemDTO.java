package com.gsc.programaavisos.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveManageItemDTO {

    private Integer idItemType;
    private Integer idTpaItem;
    private String serviceName;
    private Integer category;
    private String description;
    private String serviceLink;
    private LocalDate endDateInput;
    private String serviceCode;
}
