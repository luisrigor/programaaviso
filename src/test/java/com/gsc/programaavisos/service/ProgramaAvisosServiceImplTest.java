package com.gsc.programaavisos.service;

import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.FilterBean;
import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.dto.SearchPADTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.gsc.programaavisos.repository.crm.QuarantineRepository;
import com.gsc.programaavisos.repository.crm.VehicleRepository;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.ProgramaAvisosServiceImpl;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class ProgramaAvisosServiceImplTest {

    public final SimpleDateFormat timeZoFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    @Mock
    private PARepository paRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private QuarantineRepository quarantineRepository;
    @InjectMocks
    private ProgramaAvisosServiceImpl programaAvisosService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSavePAAndPAIdIsLowerThanZeroThenDoNotSaveAnythingCase() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        padto.setId(0);
        ProgramaAvisos oPA = new ProgramaAvisos();
        // Act
        programaAvisosService.savePA(SecurityData.getUserDefaultStatic(),padto);
        // Assert
        verify(paRepository,times(0)).save(oPA);
    }

   /* @Test
    void whenSavePASuccessfullyCase() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        ProgramaAvisos oPA = ProgramaAvisosData.getCompletePA();
        when(paRepository.findById(anyInt())).thenReturn(Optional.ofNullable(oPA));
        // Act
        programaAvisosService.savePA(SecurityData.getUserDefaultStatic(),padto);
        // Assert
        verify(paRepository,times(1)).save(oPA);
    }*/

    @Test
    void whenSavePAAndIdDoNotFoundThenThrowProgramaAvisosException() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        when(paRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> programaAvisosService.savePA(SecurityData.getUserDefaultStatic(),padto));
    }

   /* @Test
    void whenSavePAWithRevisionScheduleMotiveThenSavePASuccessfullyCase() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        ProgramaAvisos oPA = ProgramaAvisosData.getCompletePA();
        padto.setRevisionScheduleMotive(PaConstants.RSM_NOT_OWNER);
        padto.setRevisionSchedule(PaConstants.RSM_NOT_OWNER2);
        Vehicle vehicle = Vehicle.builder().idUser(1).build();
        when(paRepository.findById(anyInt())).thenReturn(Optional.ofNullable(oPA));
        when(vehicleRepository.getVehicle(any())).thenReturn(vehicle);
        // Act
        programaAvisosService.savePA(SecurityData.getUserDefaultStatic(),padto);
        // Assert
        verify(paRepository,times(1)).save(oPA);
    }*/

    @Test
    void whenRemovePAAndPAIdIsLowerThanZeroThenDoNotSaveAnythingCase() {
        ProgramaAvisos oPA = ProgramaAvisosData.getCompletePA();
        oPA.setId(0);
        when(paRepository.findById(anyInt())).thenReturn(Optional.ofNullable(oPA));
        // Act
        programaAvisosService.removePA(SecurityData.getUserDefaultStatic(),1,"Option","Obs");
        // Assert
        verify(paRepository,times(0)).save(oPA);
    }

  /*  @Test
    void whenRemovePASuccessfullyCase() {
        // Arrange
        ProgramaAvisos oPA = ProgramaAvisosData.getCompletePA();
        when(paRepository.findById(any())).thenReturn(Optional.ofNullable(oPA));
        // Act
        programaAvisosService.removePA(SecurityData.getUserDefaultStatic(),1,"Option","Obs");
        // Assert
        verify(paRepository,times(1)).save(any(ProgramaAvisos.class));
    }*/

    @Test
    void whenRemovePAIdDoNotFoundThenThrowProgramaAvisosException() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        when(paRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> programaAvisosService.removePA(SecurityData.getUserDefaultStatic(),1,"Option","Obs"));
    }

    @Test
    void whenGetFilterDefaultSuccessfullyCase() throws SCErrorException {
        // Arrange
        // Act
        FilterBean actualFilter= programaAvisosService.getFilter(SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(StringUtils.EMPTY,actualFilter.getPlate());
        Assertions.assertEquals(1,actualFilter.getStatePending());
        Assertions.assertEquals(1,actualFilter.getStateHasSchedule());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getChangedBy());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getDelegatedTo());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getMissedCalls());
        Assertions.assertEquals(1,actualFilter.getCurrPage());
        Assertions.assertEquals(1L,actualFilter.getFirstPage());
    }

    @Test
    void whenGetFilterWithOidNetLexusSuccessfullyCase() throws SCErrorException {
        // Arrange
        UserPrincipal oGSCUser = SecurityData.getUserDefaultStatic();
        oGSCUser.setOidNet(Dealer.OID_NET_LEXUS);
        // Act
        FilterBean actualFilter= programaAvisosService.getFilter(oGSCUser);
        // Assert
        Assertions.assertEquals(StringUtils.EMPTY,actualFilter.getPlate());
        Assertions.assertEquals(1,actualFilter.getStatePending());
        Assertions.assertEquals(1,actualFilter.getStateHasSchedule());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getChangedBy());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getDelegatedTo());
        Assertions.assertEquals(PaConstants.ALL,actualFilter.getMissedCalls());
        Assertions.assertEquals(1,actualFilter.getCurrPage());
        Assertions.assertEquals(1L,actualFilter.getFirstPage());
    }

    @Test
    void whenSearchPASuccessfullyCase() throws SCErrorException {
        // Arrange
        SearchPADTO searchPADTO = ProgramaAvisosData.getSearchPADTO();
        // Act
        FilterBean actualFilter= programaAvisosService.searchPA(SecurityData.getUserDefaultStatic(),searchPADTO);
        // Assert
        Assertions.assertEquals(searchPADTO.getPlate(),actualFilter.getPlate());
        Assertions.assertNotNull(actualFilter);
        Assertions.assertEquals(searchPADTO.getArrMaintenanceTypes(),actualFilter.getArrSelMaintenanceTypes());
        Assertions.assertEquals(searchPADTO.getFilterOwner(),actualFilter.getOwner());
    }
}
