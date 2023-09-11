package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.CcRigorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CcRigorServiceRepository extends JpaRepository<CcRigorService, Long> {

    @Query("SELECT e FROM CcRigorService e " +
            "WHERE " +
            "(e.yearFrom < :year OR (e.yearFrom = :year AND e.monthFrom <= :month)) " +
            "AND (FUNCTION('VALUE', e.yearTo, 9999) > :year OR (FUNCTION('VALUE', e.yearTo, 9999) = :year AND FUNCTION('VALUE', e.monthTo, 12) >= :month)) " +
            "AND e.idContactType = :contactTypes")
    public List<CcRigorService> getDealersWorkedByContactCenterRigor(
            @Param("year") int year,
            @Param("month") int month,
            @Param("contactTypes") Integer contactTypes);
}
