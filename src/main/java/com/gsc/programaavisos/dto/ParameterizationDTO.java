package com.gsc.programaavisos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gsc.programaavisos.model.crm.entity.ParametrizationItems;
import lombok.*;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterizationDTO {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtStart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtEnd;
    private String comments;
    private Character published;
    private Character visible;
    private Character type;
    private List<ParametrizationItems> parametrizationItems;
}
