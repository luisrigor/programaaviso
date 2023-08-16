package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel,Integer> {

    @Query("SELECT C FROM Channel C WHERE UPPER(C.status) = :status ORDER BY C.name")
    List<Channel> getByStatus(@Param("status") Character status);
}
