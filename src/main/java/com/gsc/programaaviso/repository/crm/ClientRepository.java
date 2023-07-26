package com.gsc.programaaviso.repository.crm;

import com.gsc.programaaviso.model.crm.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByApplicationId(Long applicationId);
}
