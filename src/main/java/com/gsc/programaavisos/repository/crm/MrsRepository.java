package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Mrs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MrsRepository extends JpaRepository<Mrs, Integer> {
    Optional<Mrs> getByIdPaData(Integer idPaData);
}
