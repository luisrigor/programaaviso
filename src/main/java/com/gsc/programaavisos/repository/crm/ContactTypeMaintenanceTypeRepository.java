package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.dto.ContactMaintenanceTypes;
import com.gsc.programaavisos.model.crm.entity.ContactTypeMaintenanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactTypeMaintenanceTypeRepository extends JpaRepository<ContactTypeMaintenanceType, Integer> {

    @Query(" SELECT new com.gsc.programaavisos.dto.ContactMaintenanceTypes(PAC.id, PAC.name, PAM.name) " +
           " FROM ContactTypeMaintenanceType PACM " +
           " LEFT JOIN ContactType PAC ON PAC.id = PACM.idContactType AND PAC.status = 'S' " +
           " LEFT JOIN MrsMaintenanceType PAM ON PAM.id = PACM.idMrsMaintenanceType " +
           " ORDER BY PAC.id, PAM.priority, PAM.name ")
    List<ContactMaintenanceTypes> getMaintenanceByContactType();

}
