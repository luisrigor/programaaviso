package com.gsc.programaavisos.model.cardb.entity;

import com.gsc.scwscardb.sc.model.AcessoriesCapacityAfterSales;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Acessories {

    public static final int TYPE_ACESSORIE = 1;
    public static final int TYPE_PACK = 2;
    public static final int TYPE_KIT = 3;
    public static final String BRAND_W = "W";
    private int idVc;
    private int idType;
    private int idModelo;
    private String modelo;
    private int idCarrocaria;
    private String carrocaria;
    private int idMotor;
    private String motor;
    private int idCombustivel;
    private String combustivel;
    private String transmissão;
    private String origem;
    private int idGeracao;
    private int idVersao;
    private String versao;
    private String codLocal;
    private String sufixoLocal;
    private String desigComercial;
    private Date dtStart;
    private Date dtEnd;
    private int idAcessorio;
    private String referencia;
    private String descricao;
    private String descricaoMarketing;
    private int qtdNecessaria;
    private double maoObra;
    private double maoObraPintura;
    private String colour;
    private String defaultImage;
    private String tipoIva;
    private double preco;
    private double precoConcessao;
    private String codPreco;
    private String marca;
    private int qtdExistente;
    private double mad;
    private String codigoFornecedor;
    private String estado;
    private int prioridade;
    private String salesviewFeatured;
    private String longDescription;
    private double contribution;
    private int idAcessorioMercadoria;
    private String codigoMercadoria;
    private String descCodigoMercadoria;
    private int idAcessorioFamilia;
    private String acessorioFamilia;
    private int prioridadeAcessorioFamilia;
    private int acessorioGrupo;
    private String tipo;
    private String observacoes;
    private Date dataInicio;
    private double promoPrice;
    private Date promoStart;
    private Date promoEnd;
    private String promoImage;
    private String imgPostalToyota;
    private String imgEPostalToyota;
    private String imgPostalLexus;
    private String imgEPostalLexus;
    private String link;
    private AcessoriesCapacityAfterSales acessoriesCapacityAfterSales;
    private double transportDealerMargin;

}
