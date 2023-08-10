package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Age;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AgeRepository extends JpaRepository<Age,Integer> {
    @Query( "SELECT A FROM Age A ORDER BY A.id ASC")
    List<Age> getAllAge();
}
