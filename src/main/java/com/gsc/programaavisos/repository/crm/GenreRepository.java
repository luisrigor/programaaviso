package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> getGenderByName(String name);
}
