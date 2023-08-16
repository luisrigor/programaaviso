package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsFuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsFuelRepository extends JpaRepository<ItemsFuel,Integer> {
    List<ItemsFuel> findByIdParameterizationItems(Integer id);
}
