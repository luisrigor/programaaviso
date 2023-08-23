package com.gsc.programaavisos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientPropDTO {
    private Integer id;
    private String nextRevision;
    private String licencePlate;
}
