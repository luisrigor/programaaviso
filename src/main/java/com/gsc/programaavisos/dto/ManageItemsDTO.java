package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.DocumentUnitCategory;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageItemsDTO {

    private DocumentUnit item;
    private List<DocumentUnitCategory> categories;
    private Integer itemType;
    private Integer itemId;
}
