package com.gsc.programaaviso.repository.crm;

import com.gsc.programaaviso.model.crm.entity.ServiceLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceLoginRepository  extends JpaRepository<ServiceLogin, Long> {

    Optional<ServiceLogin> findByNameAndValue(String name, String value);
}
