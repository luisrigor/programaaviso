package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PARepository extends JpaRepository<ProgramaAvisos, Integer>, PACustomRepository {

    @Modifying
    @Transactional
    @Query("UPDATE ProgramaAvisos P SET P.blockedBy = :blockedBy WHERE P.id = :id")
    void updateBlockedByById(@Param("blockedBy") String blockedBy,@Param("id") Integer id);



    @Query("SELECT PA FROM ProgramaAvisos PA WHERE PA.nif = :nif " +
            "AND PA.idClientType = :idClientType AND PA.month = :month AND PA.year = :year AND PA.idContactType IN (:contactList)")
    ProgramaAvisos getPADataByNifData(@Param("nif") String nif, @Param("idClientType") Integer idClientType,
                                      @Param("month") Integer month, @Param("year") Integer year, @Param("contactList") List<Integer> concactList);
    /*
    @Query(value = "SELECT * FROM PA_DATA PA WHERE PA.NIF = :nif " +
            "AND PA.ID_CLIENT_TYPE = :idClientType AND PA.MONTH = :month AND PA.YEAR = :year AND PA.ID_CONTACTTYPE IN (1,2,3)",nativeQuery = true)
    ProgramaAvisos getPADataByNifData(@Param("nif") String nif, @Param("idClientType") Integer idClientType,
                                      @Param("month") Integer month, @Param("year") Integer year);
     */

    @Query("SELECT DISTINCT(PA.licensePlate) FROM ProgramaAvisos PA WHERE PA.nif = :nif " +
            "AND PA.idClientType = :idClientType AND PA.month = :month AND PA.year = :year AND PA.idContactType IN (:contactList)")
    List<String> getPlateByNif(@Param("nif") String nif, @Param("idClientType") Integer idClientType,
                                      @Param("month") Integer month, @Param("year") Integer year, @Param("contactList") List<Integer> concactList);

    @Query("SELECT PA FROM ProgramaAvisos PA WHERE PA.licensePlate = :licensePlate " +
            "AND PA.idClientType = :idClientType AND PA.month = :month AND PA.year = :year AND PA.idContactType IN (:contactList)" +
            "ORDER BY dtCreated DESC")
    ProgramaAvisos getPADataByPlate(@Param("licensePlate") String licensePlate, @Param("idClientType") Integer idClientType,
                               @Param("month") Integer month, @Param("year") Integer year, @Param("contactList") List<Integer> concactList);

}
