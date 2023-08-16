package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import java.util.List;

public interface DocumentUnitCustomRepository {

    List<DocumentUnitDTO> getByFilter(ItemFilter itemFilter);
}
