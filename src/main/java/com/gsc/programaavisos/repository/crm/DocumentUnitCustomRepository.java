package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import java.util.List;

public interface DocumentUnitCustomRepository {

    List<DocumentUnitDTO> getByFilter(ItemFilter itemFilter);
}
