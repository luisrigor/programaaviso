package com.gsc.programaavisos.model.crm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import com.sc.commons.utils.StringTasks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaDataInfoP extends PaDataInfo {

    private String description;
    private String expectedDate;

    public String getNextRevision() {
        return StringTasks.ReplaceStr(this.getMrsNextRevision(), "isão Programada dos", ".");
    }


}
