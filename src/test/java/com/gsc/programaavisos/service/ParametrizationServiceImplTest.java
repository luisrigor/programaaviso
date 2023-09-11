package com.gsc.programaavisos.service;

import com.gsc.claims.object.core.User;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.ParametrizationServiceImpl;
import com.gsc.programaavisos.util.PAUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class ParametrizationServiceImplTest {

    @Mock
    private PaParameterizationRepository paParameterizationRepository;
    @Mock
    private ItemsFuelRepository itemsFuelRepository;
    @Mock
    private ItemsGenreRepository itemsGenreRepository;
    @Mock
    private ItemsAgeRepository itemsAgeRepository;
    @Mock
    private ItemsEntityTypeRepository itemsEntityTypeRepository;
    @Mock
    private ItemsFidelitysRepository itemsFidelitysRepository;
    @Mock
    private ItemsKilometersRepository itemsKilometersRepository;
    @Mock
    private ItemsDealerRepository itemsDealerRepository;
    @Mock
    private ItemsModelRepository itemsModelRepository;
    @Mock
    private ParametrizationItemsRepository parametrizationItemsRepository;

    @InjectMocks
    private ParametrizationServiceImpl parametrizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSearchParameterizationThenReturnSuccessfully() {
        // Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "", SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenSearchParameterizationThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.getByFilter(any(ParameterizationFilter.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () -> parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "", SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenSearchParameterizationAndTypeParamIsNotEmptyThenReturnSuccessfully() {
        //Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "selectType", SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenDeleteParameterizationThenReturnSuccessfully() {
        // Arrange
        doNothing().when(paParameterizationRepository).deleteById(anyInt());
        // Act
        parametrizationService.deleteParametrization(SecurityData.getUserDefaultStatic(), 1);
        // Assert
        verify(paParameterizationRepository, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteParameterizationThenThrowProgramaAvisosException() {
        //Arrange
        doThrow(ProgramaAvisosException.class).when(paParameterizationRepository).deleteById(anyInt());
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> parametrizationService.deleteParametrization(SecurityData.getUserDefaultStatic(), 1));
    }

    @Test
    void whenGetParametrizationsListThenReturnSuccessfully() {
        //Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.getParametrizationsList(SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenGetParametrizationsListThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.getByFilter(any())).thenThrow(ProgramaAvisosException.class);
        // Act
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> parametrizationService.getParametrizationsList(SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenGetByIdThenReturnSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        when(paParameterizationRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedParameterization));
        when(parametrizationItemsRepository.getAllParametrizationItemOnlyActive(anyInt())).thenReturn(new ArrayList<>());
        // Act
        PaParameterization actualParameterization = parametrizationService.getById(1,true);
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenGetByIdThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> parametrizationService.getById(1,true));
    }

    @Test
    void whenInsertParametrizationThenReturnSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        expectedParameterization.setParametrizationItems(new ArrayList<>());
        doNothing().when(parametrizationItemsRepository).deleteById(anyInt());
        when(paParameterizationRepository.save(any())).thenReturn(expectedParameterization);
        // Act
        parametrizationService.insertParametrization(true,expectedParameterization,SecurityData.getUserDefaultStatic());
        // Assert
        verify(parametrizationItemsRepository, times(1)).deleteById(expectedParameterization.getId());
        verify(parametrizationItemsRepository, times(0)).save(any());
    }

    @Test
    void whenInsertParametrizationAndItemParamsIsNotNullThenReturnSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        ParametrizationItems parametrizationItems = ParametrizationItems.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idParameterization(expectedParameterization.getId())
                .itemAges(Collections.singletonList(ParametrizationData.getItemsAge()))
                .itemKilometers(Collections.singletonList(ParametrizationData.getItemsKilometers()))
                .itemFidelitys(Collections.singletonList(ParametrizationData.getItemFidelitys()))
                .itemFuels(Collections.singletonList(ParametrizationData.getItemFuels()))
                .itemDealers(Collections.singletonList(ParametrizationData.getItemDealers()))
                .itemGenres(Collections.singletonList(ParametrizationData.getItemGenres()))
                .itemModels(Collections.singletonList(ParametrizationData.getItemModels()))
                .itemEntityTypes(Collections.singletonList(ParametrizationData.getItemEntityTypes()))
                .build();
        expectedParameterization.setParametrizationItems(Collections.singletonList(parametrizationItems));
        when(paParameterizationRepository.save(any())).thenReturn(expectedParameterization);
        // Act
        parametrizationService.insertParametrization(false,expectedParameterization,SecurityData.getUserDefaultStatic());
        // Assert
        verify(parametrizationItemsRepository, times(0)).deleteById(expectedParameterization.getId());
        verify(parametrizationItemsRepository, times(1)).save(any());
        verify(itemsAgeRepository,times(1)).save(any());
        verify(itemsKilometersRepository,times(1)).save(any());
        verify(itemsFidelitysRepository,times(1)).save(any());
        verify(itemsFuelRepository,times(1)).save(any());
        verify(itemsDealerRepository,times(1)).save(any());
        verify(itemsGenreRepository,times(1)).save(any());
        verify(itemsModelRepository,times(1)).save(any());
        verify(itemsEntityTypeRepository,times(1)).save(any());
    }

    @Test
    void whenInsertParametrizationThenThrowProgramaAvisosException() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        expectedParameterization.setParametrizationItems(new ArrayList<>());
        when(paParameterizationRepository.save(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () ->  parametrizationService.insertParametrization(false,expectedParameterization,SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenSaveParameterizationThenReturnSuccessfully() {
        //Arrange
        ParameterizationDTO paramterizationDTO = ParametrizationData.getParameterizationDTO();
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        expectedParameterization.setParametrizationItems(new ArrayList<>());
        when(paParameterizationRepository.save(any())).thenReturn(expectedParameterization);
        // Act
        parametrizationService.saveParameterization(SecurityData.getUserDefaultStatic(),paramterizationDTO);
        // Assert
        verify(parametrizationItemsRepository, times(0)).save(any());
    }

    @Test
    void whenCloneItemListThenSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        ParametrizationItems parametrizationItems = ParametrizationData.getParametrizationItems();
        expectedParameterization.setParametrizationItems(Collections.singletonList(parametrizationItems));
        when(paParameterizationRepository.save(any())).thenReturn(expectedParameterization);
        when(parametrizationItemsRepository.save(any())).thenReturn(parametrizationItems);
        // Act
        parametrizationService.cloneItemList(expectedParameterization,SecurityData.getUserDefaultStatic());
        // Assert
        verify(parametrizationItemsRepository, times(0)).deleteById(expectedParameterization.getId());
        verify(parametrizationItemsRepository, times(1)).save(any());
        verify(itemsAgeRepository,times(1)).save(any());
        verify(itemsKilometersRepository,times(1)).save(any());
        verify(itemsFidelitysRepository,times(1)).save(any());
        verify(itemsFuelRepository,times(1)).save(any());
        verify(itemsDealerRepository,times(1)).save(any());
        verify(itemsGenreRepository,times(1)).save(any());
        verify(itemsModelRepository,times(1)).save(any());
        verify(itemsEntityTypeRepository,times(1)).save(any());
    }

    @Test
    void whenCloneItemListThenThrowProgramaAvisosException() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        when(paParameterizationRepository.save(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () ->          parametrizationService.cloneItemList(expectedParameterization,SecurityData.getUserDefaultStatic()));

    }

    @Test
    void whenCloneParameterizationThenSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        ParametrizationItems parametrizationItems = ParametrizationData.getParametrizationItems();
        doNothing().when(parametrizationItemsRepository).deleteById(anyInt());
        when(paParameterizationRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedParameterization));
        when(parametrizationItemsRepository.getAllParametrizationItemOnlyActive(anyInt())).thenReturn(Collections.singletonList(parametrizationItems));
        when(paParameterizationRepository.save(any())).thenReturn(expectedParameterization);
        when(parametrizationItemsRepository.save(any())).thenReturn(parametrizationItems);
        when(itemsAgeRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemsAge()));
        when(itemsKilometersRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemsKilometers()));
        when(itemsFidelitysRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemFidelitys()));
        when(itemsFuelRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemFuels()));
        when(itemsDealerRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemDealers()));
        when(itemsGenreRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemGenres()));
        when(itemsModelRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemModels()));
        when(itemsEntityTypeRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemEntityTypes()));

        // Act & Assert
        parametrizationService.cloneParameterization(userPrincipal,1);

        verify(parametrizationItemsRepository, times(1)).save(any());
        verify(itemsAgeRepository,times(1)).save(any());
        verify(itemsKilometersRepository,times(1)).save(any());
        verify(itemsFidelitysRepository,times(1)).save(any());
        verify(itemsFuelRepository,times(1)).save(any());
        verify(itemsDealerRepository,times(1)).save(any());
        verify(itemsGenreRepository,times(1)).save(any());
        verify(itemsModelRepository,times(1)).save(any());
        verify(itemsEntityTypeRepository,times(1)).save(any());
    }

    @Test
    void whenCloneParameterizationThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () ->                  parametrizationService.cloneParameterization(SecurityData.getUserDefaultStatic(),1));

    }

    @Test
    void whenGetMapByIdClientThenSuccessfully() {
        //Arrange
        ParametrizationItems parametrizationItems = ParametrizationData.getParametrizationItems();
        List<PaParameterization> expectedParameterization = Collections.singletonList(ParametrizationData.getPaParameterization());
        when(parametrizationItemsRepository.getAllParametrizationItem(anyInt())).thenReturn(Collections.singletonList(parametrizationItems));
        when(itemsAgeRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemsAge()));
        when(itemsKilometersRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemsKilometers()));
        when(itemsFidelitysRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemFidelitys()));
        when(itemsFuelRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemFuels()));
        when(itemsDealerRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemDealers()));
        when(itemsGenreRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemGenres()));
        when(itemsModelRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemModels()));
        when(itemsEntityTypeRepository.findByIdParameterizationItems(anyInt())).thenReturn(Collections.singletonList(ParametrizationData.getItemEntityTypes()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);

        HashMap<Integer,List<PaParameterization>> mapParameterizations = parametrizationService.getByIdClient(new ParameterizationFilter(),false);
        Assertions.assertTrue(mapParameterizations.containsKey(ApiConstants.ID_BRAND_TOYOTA));
        Assertions.assertTrue(mapParameterizations.containsKey(ApiConstants.ID_BRAND_LEXUS));
    }

    @Test
    void whengetNewParametrizationThenReturnSuccessfully() {
        //Arrange
        PaParameterization expectedParameterization = ParametrizationData.getPaParameterization();
        when(paParameterizationRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedParameterization));
        when(parametrizationItemsRepository.getAllParametrizationItem(anyInt())).thenReturn(new ArrayList<>());
        // Act
        PaParameterization actualParameterization = parametrizationService.getNewParametrization(SecurityData.getUserDefaultStatic(),1);
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }


}