package com.gsc.programaavisos.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDelegatorsDTO {

    String fromYear;
    String toYear;
    String fromMonth;
    String toMonth;
    String[] arrayOidDealer;
}
