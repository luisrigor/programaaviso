
package com.gsc.programaavisos.repository.crm;


import com.gsc.programaavisos.model.crm.entity.PATotals;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PABeanRepository extends JpaRepository<ProgramaAvisosBean,Integer> {

    @Query(value = "SELECT * FROM PA_DATA_INFO p " +
            //"LEFT JOIN VEHICLE_HHC h ON h.contractVin = p.vin AND h.contractStatus = 'Active' " +
            //"WHERE ((:checkExpiredContracts = true AND mcDtFinishContract >= :dtStart AND mcDtFinishContract < :dtEnd) OR " +
            //"(:checkExpiredContracts = false AND :checkConnectivityContracts = false)) " +
            "ORDER BY PA_ID ASC LIMIT 10",nativeQuery = true)
    List<ProgramaAvisosBean> getPAList(
            //@Param("checkExpiredContracts") boolean checkExpiredContracts,
                                  // @Param("checkConnectivityContracts") boolean checkConnectivityContracts,
                                  // @Param("dtStart") Date dtStart,
                                   // @Param("dtEnd") Date dtEnd)
    );
/*
    @Query("SELECT NEW com.gsc.programaavisos.model.crm.entity.PATotals(" +
            "COUNT(p) AS COUNT_TOTAL, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 1 THEN 1 ELSE 0 END), 0) AS COUNT_NOT_DONE, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus IN (3, 4, 5, 6, 7) THEN 1 ELSE 0 END), 0) AS COUNT_DONE, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 3 THEN 1 ELSE 0 END), 0) AS WITH_APPOINTMENT, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 2 THEN 1 ELSE 0 END), 0) AS COUNT_SCHEDULE, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 8 THEN 1 ELSE 0 END), 0) AS COUNT_REMOVED_MANUALLY, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 9 THEN 1 ELSE 0 END), 0) AS COUNT_REMOVED_AUTO_BY_MANUT, " +
            "COALESCE(SUM(CASE WHEN p.paIdStatus = 10 THEN 1 ELSE 0 END), 0) AS COUNT_REMOVED_AUTO_BY_PERIOD) " +
            "FROM PA_DATA_INFO p " +
            "LEFT JOIN HHC h ON h.contractVin = p.paVin AND h.contractStatus = 'Active' " +
            "WHERE p.date BETWEEN :startDate AND :endDate " +
            "AND (:checkExpiredContracts = false OR (p.mcDtFinishContract >= :startDate AND p.mcDtFinishContract < :endDate))")
    PATotals calculateTotals(@Param("startDate") Date startDate,
                             @Param("endDate") Date endDate,
                             @Param("checkExpiredContracts") boolean checkExpiredContracts);

 */

}


