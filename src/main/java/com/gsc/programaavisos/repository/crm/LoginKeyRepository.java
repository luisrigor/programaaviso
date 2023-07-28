package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.LoginKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginKeyRepository extends JpaRepository<LoginKey, Long> {

    Optional<LoginKey> findFirstByEnabledIsTrue();
}

