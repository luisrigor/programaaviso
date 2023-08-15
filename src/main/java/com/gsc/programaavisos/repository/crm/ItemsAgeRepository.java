package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsAgeRepository extends JpaRepository<ItemsAge,Integer> {
    List<ItemsAge> findByIdParameterizationItems(Integer id);
}
