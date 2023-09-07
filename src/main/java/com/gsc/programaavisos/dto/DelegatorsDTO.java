package com.gsc.programaavisos.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DelegatorsDTO {

    private List<DelegatorsValues> delegators;
    private List<DelegatorsValues> changedBy;
}
