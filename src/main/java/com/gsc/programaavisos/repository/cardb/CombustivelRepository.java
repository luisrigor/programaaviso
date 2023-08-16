package com.gsc.programaavisos.repository.cardb;

import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Combustivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CombustivelRepository extends JpaRepository<Combustivel, Integer> {


    @Query("SELECT DISTINCT NEW com.gsc.programaavisos.model.cardb.Fuel(GI.idCombustivel, C.desig, C.prioridade, C.dtStart,C.dtEnd)  " +
            "FROM  GamaInfo GI " +
            "INNER JOIN Combustivel C ON GI.idCombustivel = C.id WHERE GI.idMarca = :idBrand")
    List<Fuel> getFuelsByIdBrand(@Param("idBrand") int idBrand);

}
