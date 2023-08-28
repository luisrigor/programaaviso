package com.gsc.programaavisos.dto;

import io.swagger.models.auth.In;
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
    //img1: (binary)
    //img2: (binary)
}
