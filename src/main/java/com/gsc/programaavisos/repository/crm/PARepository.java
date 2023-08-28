package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface PARepository extends JpaRepository<ProgramaAvisos, Integer>, PACustomRepository {

    @Modifying
    @Transactional
    @Query("UPDATE ProgramaAvisos P SET P.blockedBy = :blockedBy WHERE P.id = :id")
    void updateBlockedByById(@Param("blockedBy") String blockedBy,@Param("id") Integer id);


}
