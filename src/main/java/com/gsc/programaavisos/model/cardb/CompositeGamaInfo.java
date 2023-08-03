package com.gsc.programaavisos.model.cardb;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CompositeGamaInfo implements Serializable {

    private Integer idMarca;
    private Integer idModelo;
    private Integer idCategoria;
    private Integer idGeracao;
    private Integer idCombustivel;
}
