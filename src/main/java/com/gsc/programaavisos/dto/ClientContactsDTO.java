package com.gsc.programaavisos.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientContactsDTO {

    List<ClientPropDTO> contactsForPlate;
    List<ClientPropDTO> contactsForClient;
}
