package com.gsc.programaavisos.repository.crm;


import com.gsc.programaavisos.model.crm.entity.Kilometers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KilometersRepository extends JpaRepository<Kilometers,Integer> {
    @Query( "SELECT K FROM Kilometers K ORDER BY K.id ASC")
    List<Kilometers> getAllKilometers();
}
