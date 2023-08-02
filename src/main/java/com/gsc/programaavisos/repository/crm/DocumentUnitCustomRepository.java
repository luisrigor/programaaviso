package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;

import java.util.List;

public interface DocumentUnitCustomRepository {

    List<DocumentUnit> getByFilter(ItemFilter itemFilter);
}
