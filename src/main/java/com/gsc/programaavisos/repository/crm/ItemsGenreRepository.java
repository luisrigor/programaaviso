package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsGenreRepository extends JpaRepository<ItemsGenre,Integer> {
    List<ItemsGenre> findByIdParameterizationItems(Integer id);
}
