package com.gsc.programaavisos.model.cardb.entity;


import com.gsc.programaavisos.model.cardb.CompositeGamaInfo;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GAMA_INFO")
@IdClass(CompositeGamaInfo.class)

public class GamaInfo {

    @Id
    @Column(name = "ID_MARCA")
    private Integer idMarca;
    @Id
    @Column(name = "ID_MODELO")
    private Integer idModelo;
    @Id
    @Column(name = "ID_CATEGORIA")
    private Integer idCategoria;
    @Id
    @Column(name = "ID_GERACAO")
    private Integer idGeracao;
    @Id
    @Column(name = "ID_COMBUSTIVEL")
    private Integer idCombustivel;

}
