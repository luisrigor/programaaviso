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

    /*@Query("SELECT PA FROM ProgramaAvisos PA WHERE nif = :nif " +
            "AND idClientType = :idClientType AND month = :month AND year = :year AND idContactType IN (:contactList)")
    ProgramaAvisos getPADataByNifData(@Param("nif") String nif, @Param("idClientType") Integer idClientType,
                                      @Param("month") Integer month, @Param("year") Integer year, @Param("contactList") List<Integer> concactList);
     */
    @Query(value = "SELECT * FROM PA_DATA PA  WHERE ID = 10132",nativeQuery = true)
    ProgramaAvisos getPADataByNifData();
}
