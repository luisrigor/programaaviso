package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.crm.entity.Calls;
import com.gsc.programaavisos.model.crm.entity.Channel;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsPADTO {

    private ProgramaAvisosBean programaAvisosBean;
    private List<Calls> calls;
    private List<Channel> channels;
    private boolean isBlocked;
}
