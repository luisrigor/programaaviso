package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.crm.entity.ParametrizationItems;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterizationDTO {

    private int id;
    private Date dtStart;
    private Date dtEnd;
    private String comments;
    private Character published;
    private Character visible;
    private Character type;
    private List<ParametrizationItems> parametrizationItems;
}
