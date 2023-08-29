package com.gsc.programaavisos.model.cardb.entity;

import com.gsc.scwscardb.sc.model.Acessories;
import com.sc.commons.objects.ScObject;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcessorioGrupo extends ScObject {
    private int id;
    private String descricao;
    private String descricaoMarketing;
    private String tipo;
    private String referencia;
    private double preco;
    private double precoConcessao;
    private String estado;
    private String observacoes;
    private Date dataInicio;
    private int idAcessorioFamilia;
    private String salesViewFeatured;
    private int prioridade;
    private String defaultImage;
    private String imgPostalLexus;
    private String imgPostalToyota;
    private String imgEPostalLexus;
    private String imgEPostalToyota;
    private String createdBy;
    private Timestamp dtCreated;
    private String changedBy;
    private Timestamp dtChanged;
    private Hashtable<Acessories, Integer> hstAcessorio;

}
