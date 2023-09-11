package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.CompositeDataInfo;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaDataInfoRepository extends JpaRepository<PaDataInfo, CompositeDataInfo> {

    @Query("SELECT DI FROM PaDataInfo DI WHERE DI.paYear = :paYear " +
            " AND DI.paOidDealer = :paOidDealer AND DI.paVisible = 'S'  AND DI.paDtVisible <= current_date() ")
    PaDataInfo getProgramaAvisosByYearMonthPlateDealer(@Param("paYear") Integer paYear,@Param("paOidDealer") String paOidDealer);

    @Query("SELECT DI FROM PaDataInfo DI WHERE DI.paIdContactType = :paIdContactType AND DI.paIdStatus = :paIdStatus ")
    List<PaDataInfo> geProgramaAvisosByContactTypeAndStatus(@Param("paIdContactType") Integer paIdContactType, @Param("paIdStatus") Integer paIdStatus);

//    @Query("SELECT NEW com.gsc.programaavisos.dto.DataInfoFieldsDTO(" +
//            "DI.paChangedBy, " +
//            "CASE WHEN u.nomeComercial <> '' THEN u.nomeComercial ELSE u.nomeUtilizador END, " +
//            "FUNCTION('COLLATION_KEY_BIT', " +
//            "CASE WHEN u.nomeComercial <> '' THEN u.nomeComercial ELSE u.nomeUtilizador END, 'UCA500R1_S1')" +
//            ") " +
//            "FROM PaDataInfo DI " +
//            "LEFT JOIN Utilizador u ON DI.paChangedBy = CHAR(u.idUtilizador) " +
//            " WHERE ( " +
//            " (DI.paYear >  :fromYear OR (DI.paYear = :fromYear  AND DI.paMonth >= :fromMonth )) " +
//            " AND ( " +
//            " (DI.paYear < :toYear OR (DI.paYear =  :toYear AND DI.paMonth <= :toMonth)) " +
//            " )) "+
//            " AND DI.paOidDealer in (" + (oidDealer.equals("") ? "''" : oidDealer) + ") "+
//            " AND VALUE(PA_CHANGED_BY,'') <> '' " +
//            " AND U.nomeUtilizador IS NOT NULL "+
//            " ORDER BY ORDER_FIELD "
//    )
//    List<DataInfoFieldsDTO> getDistinctChangedByNames();

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT PA_CHANGED_BY, " +
                    "       CASE WHEN U.NOME_COMERCIAL <> '' THEN U.NOME_COMERCIAL ELSE U.NOME_UTILIZADOR END AS NOME, " +
                    "       COLLATION_KEY_BIT(CASE WHEN U.NOME_COMERCIAL <> '' THEN U.NOME_COMERCIAL ELSE U.NOME_UTILIZADOR END, 'UCA500R1_S1') AS ORDER_FIELD " +
                    "FROM PA_DATA_INFO " +
                    "LEFT JOIN USRLOGON_UTILIZADOR U ON PA_CHANGED_BY = CHAR(U.ID_UTILIZADOR) " +
                    "WHERE " +
                    "      (PA_YEAR > :fromYear OR (PA_YEAR = :fromYear AND PA_MONTH >= :fromMonth)) " +
                    "  AND (PA_YEAR < :toYear OR (PA_YEAR = :toYear AND PA_MONTH <= :toMonth)) " +
                    "  AND PA_OID_DEALER IN :oidDealerList " +
                    "  AND VALUE(PA_CHANGED_BY, '') <> '' " +
                    "  AND U.NOME_UTILIZADOR IS NOT NULL " +
                    "ORDER BY ORDER_FIELD")
    List<Object[]> getDistinctChangedByNames(
            @Param("fromYear") int fromYear,
            @Param("fromMonth") int fromMonth,
            @Param("toYear") int toYear,
            @Param("toMonth") int toMonth,
            @Param("oidDealerList") List<String> oidDealerList);




    @Query( "SELECT DI FROM PaDataInfo DI " +
            "WHERE DI.paLicensePlate = :plate AND DI.paIdContactType IN (1, 2, 3) " +
            "AND DI.paVisible = 'S' " +
            "ORDER BY DI.paId DESC ")
    PaDataInfo getLastPaDataForPlate(@Param("plate") String plate);


}
