package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
