package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentUnitRepository extends JpaRepository<DocumentUnit,Integer> , DocumentUnitCustomRepository{
}
