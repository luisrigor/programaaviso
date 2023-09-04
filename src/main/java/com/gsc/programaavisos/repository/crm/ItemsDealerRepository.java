package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsDealer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsDealerRepository extends JpaRepository<ItemsDealer,Integer> {
    List<ItemsDealer> findByIdParameterizationItems(Integer id);
}
