package com.gsc.programaaviso.repository;

import com.gsc.programaaviso.model.entity.LoginKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginKeyRepository extends JpaRepository<LoginKey, Long> {

    Optional<LoginKey> findFirstByEnabledIsTrue();
}

