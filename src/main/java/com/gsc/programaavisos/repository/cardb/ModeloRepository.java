package com.gsc.programaavisos.repository.cardb;

import com.gsc.programaavisos.model.cardb.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

    @Query("SELECT M FROM  Modelo M WHERE M.idMarca = :idBrand  ORDER BY UPPER(M.desig)")
    List<Modelo> getModels(@Param("idBrand") int idBrand);
}
