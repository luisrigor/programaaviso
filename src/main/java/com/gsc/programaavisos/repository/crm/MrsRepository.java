package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Mrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MrsRepository extends JpaRepository<Mrs, Integer> {
    //Optional<Mrs> getByIdPaData(Integer idPaData);

    @Query("SELECT M FROM Mrs M WHERE M.idPaData = :idPaData")
    Mrs getByIdPaData(@Param("idPaData") Integer idPaData);
}
