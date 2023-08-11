package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.cardb.CombustivelRepository;
import com.gsc.programaavisos.repository.cardb.ModeloRepository;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.OtherFlowServiceImpl;
import com.gsc.programaavisos.util.TPAInvokerSimulator;
import com.rg.dealer.Dealer;
import com.rg.dealer.DealerHelper;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.initialization.SCGlobalPreferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
public class OtherFlowServiceImplTest {

    @Mock
    private DocumentUnitRepository documentUnitRepository;
    @Mock
    private ContactReasonRepository contactReasonRepository;
    @Mock
    private ModeloRepository modeloRepository;
    @Mock
    private CombustivelRepository combustivelRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private EntityTypeRepository entityTypeRepository;
    @Mock
    private AgeRepository ageRepository;
    @Mock
    private KilometersRepository kilometersRepository;
    @Mock
    private FidelitysRepository fidelitysRepository;
    @InjectMocks
    private OtherFlowServiceImpl otherFlowServiceImpl;

    private SecurityData securityData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityData = new SecurityData();
    }

    @Test
    void getContactReasonsSuccessfullyCase() {
        // Arrange
        List<ContactReason> expectedContactList = new ArrayList<>(Collections.singletonList(OtherFlowData.getContactReason()));
        when(contactReasonRepository.findAll()).thenReturn(expectedContactList);
        // Act
        List<ContactReason> actualContactList = otherFlowServiceImpl.getContactReasons();
        // Assert
        Assertions.assertEquals(expectedContactList,actualContactList);
    }

    @Test
    void getContactReasonsThrowProgramaAvisosException() {
        //Arrange
        when(contactReasonRepository.findAll()).thenThrow(ProgramaAvisosException.class);

        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () -> otherFlowServiceImpl.getContactReasons());
    }

    @Test
    void getGenreSuccessfullyCase() {
        // Arrange
        List<Genre> expectedGenreList = new ArrayList<>(Collections.singletonList(OtherFlowData.getGenre()));
        when(genreRepository.findAll()).thenReturn(expectedGenreList);
        // Act
        List<Genre> actualGenreList = otherFlowServiceImpl.getGenre();
        // Assert
        Assertions.assertEquals(expectedGenreList,actualGenreList);
    }
    @Test
    void getGenreThrowProgramaAvisosException() {
        // Arrange
        when(genreRepository.findAll()).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()->otherFlowServiceImpl.getGenre());
    }

    @Test
    void getKilometersSuccessfullyCase() {
        //Arrange
        List<Kilometers> expectedKilometers = new ArrayList<>(Collections.singletonList(OtherFlowData.getKilometers()));
        when(kilometersRepository.getAllKilometers()).thenReturn(expectedKilometers);
        //Act
        List<Kilometers> actualKilometers = otherFlowServiceImpl.getKilometers();
        //Assert
        Assertions.assertEquals(expectedKilometers,actualKilometers);
    }

    @Test
    void getKilometersThrowProgramaAvisosException(){
        //Arrange
        when(kilometersRepository.getAllKilometers()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getKilometers());
    }

    @Test
    void getEntityTypeSuccessfullyCase() {
        //Arrange
        List<EntityType> expectedEntity = new ArrayList<>(Collections.singletonList(OtherFlowData.getEntity()));
        when(entityTypeRepository.findAll()).thenReturn(expectedEntity);
        //Act
        List<EntityType> actualEntity = otherFlowServiceImpl.getEntityType();
        //Assert
        Assertions.assertEquals(expectedEntity,actualEntity);
    }

    @Test
    void getEntityTypeThrowProgramaAvisosException(){
        //Arrange
        when(entityTypeRepository.findAll()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getEntityType());
    }

    @Test
    void getAgeSuccessfullyCase() {
        //Arrange
        List<Age> expectedAges = new ArrayList<>(Collections.singletonList(OtherFlowData.getAge()));
        when(ageRepository.getAllAge()).thenReturn(expectedAges);
        //Act
        List<Age> actualAges = otherFlowServiceImpl.getAge();
        //Assert
        Assertions.assertEquals(expectedAges,actualAges);
    }

    @Test
    void getAgeThrowProgramaAvisosException(){
        //Arrange
        when(ageRepository.getAllAge()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getAge());
    }

    @Test
    void getFidelitySuccessfullyCase() {
        //Arrange
        List<Fidelitys> expectedFidelity = new ArrayList<>(Collections.singletonList(OtherFlowData.getFidelity()));
        when(fidelitysRepository.findAll()).thenReturn(expectedFidelity);
        //Act
        List<Fidelitys> actualFidelity = otherFlowServiceImpl.getFidelitys();
        //Assert
        Assertions.assertEquals(expectedFidelity,actualFidelity);
    }

    @Test
    void getFidelityThrowProgramaAvisosException(){
        //Arrange
        when(fidelitysRepository.findAll()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getFidelitys());
    }

    @Test
    void getModelSuccessfullyCase() {
        //Arrange
        List<Modelo> expectedModels = new ArrayList<>(Collections.singletonList(OtherFlowData.getModelo()));
        when(modeloRepository.getModels(anyInt())).thenReturn(expectedModels);
        //Act
        List<Modelo> actualModels = otherFlowServiceImpl.getModels(SecurityData.getUserDefaultStatic());
        //Assert
        Assertions.assertEquals(expectedModels,actualModels);
    }

    @Test
    void getModelThrowProgramaAvisosException(){
        //Arrange
        when(modeloRepository.getModels(anyInt())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getModels(SecurityData.getUserDefaultStatic()));
    }

    @Test
    void getFuelsSuccessfullyCase() {
        //Arrange
        List<Fuel> expectedFuelList = new ArrayList<>(Collections.singletonList(OtherFlowData.getFuel()));
        when(combustivelRepository.getFuelsByIdBrand(anyInt())).thenReturn(expectedFuelList);
        //Act
        List<Fuel> actualFuelList = otherFlowServiceImpl.getFuels(SecurityData.getUserDefaultStatic());
        //Assert
        Assertions.assertEquals(expectedFuelList,actualFuelList);
    }

    @Test
    void getFuelsWhenTPAInvokerAlreadyExitsSuccessfullyCase() {
        //Arrange
        List<Fuel> expectedFuelList = new ArrayList<>(Collections.singletonList(OtherFlowData.getFuel()));
        expectedFuelList.add(new Fuel(
                TPAInvokerSimulator.CAR_DB_COMBUSTIVEL_SEM_INFO, "s/info", 0, null, null));
        when(combustivelRepository.getFuelsByIdBrand(anyInt())).thenReturn(expectedFuelList);
        //Act
        List<Fuel> actualFuelList = otherFlowServiceImpl.getFuels(SecurityData.getUserDefaultStatic());
        //Assert
        Assertions.assertEquals(expectedFuelList,actualFuelList);
    }

    @Test
    void getFuelsThrowProgramaAvisosException(){
        //Arrange
        when(combustivelRepository.getFuelsByIdBrand(anyInt())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, ()-> otherFlowServiceImpl.getFuels(SecurityData.getUserDefaultStatic()));
    }

    @Test
    void searchDocumentUnitSuccessfullyCase() {
        //Arrange
        ItemFilter itemFilterExpected = ItemData.getItemFilter();
        List<DocumentUnitDTO> documentListExpected = new ArrayList<>(Collections.singletonList(OtherFlowData.getDocumentUnit()));
        when(documentUnitRepository.getByFilter(any(ItemFilter.class))).thenReturn(documentListExpected);
        //Act
        List<DocumentUnitDTO> documentListActual = otherFlowServiceImpl.searchDocumentUnit(1,SecurityData.getUserDefaultStatic());
        //Assert
        Assertions.assertEquals(documentListExpected,documentListActual);
    }

    @Test
    void searchDocumentUnitThrowProgramaAvisosException(){
        //Arrange
        when(documentUnitRepository.getByFilter(any(ItemFilter.class))).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()-> otherFlowServiceImpl.searchDocumentUnit(1, SecurityData.getUserDefaultStatic()));
    }

    @Test
    void getDealersDefaultSuccessfullyCase() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        //Act
        List<Dealer> dealers = otherFlowServiceImpl.getDealers(userPrincipal);
        //Assert
        Assertions.assertEquals(1,dealers.size());
    }

    @Test
    void getDealersToyotaIdCaseSuccessfullyCase() throws SCErrorException {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        userPrincipal.setOidNet(Dealer.OID_NET_LEXUS);
        //Act
        List<Dealer> dealers = otherFlowServiceImpl.getDealers(userPrincipal);
        //Assert
        Assertions.assertEquals(1,dealers.size());
    }

}
