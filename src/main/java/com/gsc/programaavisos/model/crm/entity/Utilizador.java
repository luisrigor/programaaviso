package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USRLOGON_UTILIZADOR")
public class Utilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UTILIZADOR")
    private Integer idUtilizador;

    @Column(name = "LOGIN_UTILIZADOR")
    private String loginUtilizador;

    @Column(name = "PASS_UTILIZADOR")
    private String passwordUtilizador;

    @Column(name = "NOME_UTILIZADOR")
    private String nomeUtilizador;

    @Column(name = "EMAIL_UTILIZADOR")
    private String emailUtilizador;

    @Column(name = "TEL_UTILIZADOR")
    private String telefoneUtilizador;

    @Column(name = "ACTIVO_UTILIZADOR")
    private String ativoUtilizador;

    @Column(name = "OBS_UTILIZADOR")
    private String obsUtilizador;

    @Column(name = "ID_ENTIDADE")
    private Integer idEntidade;

    @Column(name = "ID_VENDEDOR_TOYOTA")
    private Integer idVendedorToyota;

    @Column(name = "SEXO_UTILIZADOR")
    private Character sexoUtilizador;

    @Column(name = "NIF_UTILIZADOR")
    private String nifUtilizador;

    @Column(name = "TELEMOVEL_UTILIZADOR")
    private String telemovelUtilizador;

    @Column(name = "FAX_UTILIZADOR")
    private String faxUtilizador;

    @Column(name = "ENDERECO_UTILIZADOR")
    private String enderecoUtilizador;

    @Column(name = "CP4_UTILIZADOR")
    private String cp4Utilizador;

    @Column(name = "CP3_UTILIZADOR")
    private String cp3Utilizador;

    @Column(name = "CPEXT_UTILIZADOR")
    private String cpextUtilizador;

    @Column(name = "SERVICO_TOYOTA_UTILIZADOR")
    private String servicoToyotaUtilizador;

    @Column(name = "APPROVED_BY_UTILIZADOR")
    private Integer approvedByUtilizador;

    @Column(name = "DT_APPROVED_BY_UTILIZADOR")
    private LocalDate dtApprovedByUtilizador;

    @Column(name = "DT_LAST_LOGIN")
    private LocalDate dtLastLogin;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "NISS")
    private String niss;

    @Column(name = "INICIO_ACTIVIDADE")
    private LocalDate inicioAtividade;

    @Column(name = "PROFISSAO")
    private String profissao;

    @Column(name = "TARS_UUID")
    private String tarsUuid;

    @Column(name = "NOME_COMERCIAL")
    private String nomeComercial;

    @Column(name = "COD_VENDEDOR")
    private String codVendedor;

    @Column(name = "LOGIN_DMS")
    private String loginDms;

    @Column(name = "FOTO")
    private String foto;

    @Column(name = "COD_UTIL_DMS")
    private String codUtilDms;

}
