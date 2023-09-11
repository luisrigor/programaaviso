package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.dto.ProgramaAvisosBean;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.cardb.CombustivelRepository;
import com.gsc.programaavisos.repository.cardb.ModeloRepository;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.OtherFlowServiceImpl;
import com.gsc.programaavisos.util.TPAInvokerSimulator;
import com.rg.dealer.Dealer;
import com.sc.commons.exceptions.SCErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class OtherFlowServiceImplTest {

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
    @Mock
    private PARepository paRepository;
    @Mock
    private PaDataInfoRepository dataInfoRepository;
    @Mock
    private ContactTypeRepositoryCRM contactTypeRepositoryCRM;
    @Mock
    private ContactTypeMaintenanceTypeRepository maintenanceTypeRepository;
    @Mock
    private ClientTypeRepository clientTypeRepository;
    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private SourceRepository sourceRepository;
    @Mock
    private ContactTypeRepository contactTypeRepository;
    @Mock
    private Environment env;
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

    @Test
    void getDelegatorsSuccessfullyCase() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        userPrincipal.setOidNet(Dealer.OID_NET_LEXUS);
        List<String> listDelegators = new ArrayList<>();
        Map<String, String> mapLastChangedBy= new HashMap<>();
        when(paRepository.getDelegators(anyInt(),anyInt(),anyInt(),anyInt(),anyString())).thenReturn(listDelegators);
        when(paRepository.getLastChangedBy(anyInt(), anyInt(), anyInt(), anyInt(), anyString())).thenReturn(mapLastChangedBy);
        //Act
        DelegatorsDTO actualDto = otherFlowServiceImpl.getDelegators(userPrincipal,OtherFlowData.getGetDelegatorsDTO());
        //Assert
        Assertions.assertEquals(1,actualDto.getDelegators().size());
        Assertions.assertEquals(2,actualDto.getChangedBy().size());
    }

    @Test
    void getDelegatorsThrowProgramaAvisosException() {
        //Arrange
        when(paRepository.getDelegators(anyInt(),anyInt(),anyInt(),anyInt(),anyString())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()-> otherFlowServiceImpl.getDelegators(SecurityData.getUserDefaultStatic(),OtherFlowData.getGetDelegatorsDTO()));
    }

    @Test
    void getChangedListSuccessfullyCase() {
        //Arrange
        List<Object[]> changedList = new ArrayList<>();
        when(dataInfoRepository.getDistinctChangedByNames(anyInt(),anyInt(),anyInt(),anyInt(),anyList())).thenReturn(changedList);
        //Act
        List<Object[]> actualList = otherFlowServiceImpl.getChangedList(OtherFlowData.getGetDelegatorsDTO());
        //Assert
        Assertions.assertEquals(0,actualList.size());
    }

    @Test
    void getMaintenanceTypesByContactTypeSuccessfullyCase() {
        //Arrange
        List<ContactMaintenanceTypes> maintenanceByContactType = new ArrayList<>();
        when(maintenanceTypeRepository.getMaintenanceByContactType()).thenReturn(maintenanceByContactType);
        //Act
        Map<Integer, List<String>> maintenanceTypesByContactType = otherFlowServiceImpl.getMaintenanceTypesByContactType();
        //Assert
        Assertions.assertEquals(0,maintenanceTypesByContactType.size());
    }

    @Test
    void getPAClientContactsSuccessfullyCase() {
        //Arrange
        List<ContactMaintenanceTypes> maintenanceByContactType = new ArrayList<>();
        List<ProgramaAvisosBean> vecPABean = new ArrayList<>();
        when(maintenanceTypeRepository.getMaintenanceByContactType()).thenReturn(maintenanceByContactType);
        when(paRepository.getOpenContactsforClient(any(),anyString(), anyString(), any())).thenReturn(vecPABean);
        //Act
        ClientContactsDTO clientContactsDTO = otherFlowServiceImpl.getPAClientContacts("nif","plate",1, ProgramaAvisosData.getFilterBean());
        //Assert
        Assertions.assertEquals(0,clientContactsDTO.getContactsForClient().size());
        Assertions.assertEquals(0,clientContactsDTO.getContactsForPlate().size());
    }

    @Test
    void getPAClientContactsThrowProgramaAvisosException() {
        //Arrange
        List<ProgramaAvisosBean> vecPABean = new ArrayList<>();
        when(maintenanceTypeRepository.getMaintenanceByContactType()).thenThrow(ProgramaAvisosException.class);
        when(paRepository.getOpenContactsforClient(any(),anyString(), anyString(), any())).thenReturn(vecPABean);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getPAClientContacts("nif","plate",1, ProgramaAvisosData.getFilterBean()));
    }

    @Test
    void getClientTypesContactsSuccessfullyCase() {
        //Arrange
        List<ClientType> clientTypes = Collections.singletonList(OtherFlowData.getClientType());
        when(clientTypeRepository.getByStatus(anyChar())).thenReturn(clientTypes);
        //Act
        List<ClientType> actualClientTypes = otherFlowServiceImpl.getClientTypes();
        //Assert
        Assertions.assertEquals(1,actualClientTypes.size());
        Assertions.assertEquals(clientTypes,actualClientTypes);
    }

    @Test
    void getClientTypesContactsThrowProgramaAvisosException() {
        //Arrange
        when(clientTypeRepository.getByStatus(anyChar())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getClientTypes());
    }

    @Test
    void getContactAccesSuccessfullyCase() {
        //Arrange
        Map<Integer, String> map2 = new HashMap<>();
        String[] activeProfiles = {"Profile1", "Profile2", "Profile3"};
        when(env.getActiveProfiles()).thenReturn(activeProfiles);
        //Act
        Map<Integer, String> map = otherFlowServiceImpl.getContactAcces();
        //Assert
        Assertions.assertTrue(map.containsKey(ContactTypeB.CONNECTIVITY));
        Assertions.assertEquals("tcap1@tpo",map.get(ContactTypeB.CONNECTIVITY));
    }

    @Test
    void getContactAccesProductionSuccessfullyCase() {
        //Arrange
        Map<Integer, String> map2 = new HashMap<>();
        String[] activeProfiles = {"Profile1", "Profile2", "production"};
        when(env.getActiveProfiles()).thenReturn(activeProfiles);
        //Act
        Map<Integer, String> map = otherFlowServiceImpl.getContactAcces();
        //Assert
        Assertions.assertTrue(map.containsKey(ContactTypeB.CONNECTIVITY));
        Assertions.assertNotEquals("tcap1@tpo",map.get(ContactTypeB.CONNECTIVITY));
    }

    @Test
    void getChannelsSuccessfullyCase() {
        //Arrange
        List<Channel> channels = Collections.singletonList(OtherFlowData.getChannel());
        when(channelRepository.getByStatus('S')).thenReturn(channels);
        //Act
        List<Channel> actualChannels = otherFlowServiceImpl.getChannels();
        //Assert
        Assertions.assertEquals(channels,actualChannels);
        Assertions.assertEquals(1,actualChannels.size());
    }

    @Test
    void getChannelsThrowProgramaAvisosException() {
        //Arrange
        when(channelRepository.getByStatus(anyChar())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getChannels());
    }

    @Test
    void getSourcesSuccessfullyCase() {
        //Arrange
        List<Source> sources = Collections.singletonList(OtherFlowData.getSource());
        when(sourceRepository.getByStatus('S')).thenReturn(sources);
        //Act
        List<Source> actualSources = otherFlowServiceImpl.getSources();
        //Assert
        Assertions.assertEquals(sources,actualSources);
        Assertions.assertEquals(1,actualSources.size());
    }

    @Test
    void getSourcesThrowProgramaAvisosException() {
        //Arrange
        when(sourceRepository.getByStatus(anyChar())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getSources());
    }

    @Test
    void getAllContactTypesSuccessfullyCase() {
        //Arrange
        List<ContactType> contactTypes = Collections.singletonList(OtherFlowData.getContactType());
        when(contactTypeRepository.findAll()).thenReturn(contactTypes);
        //Act
        List<ContactType> actualContactTypes = otherFlowServiceImpl.getAllContactTypes();
        //Assert
        Assertions.assertEquals(contactTypes,actualContactTypes);
        Assertions.assertEquals(1,actualContactTypes.size());
    }

    @Test
    void getAllContactTypesThrowProgramaAvisosException() {
        //Arrange
        when(contactTypeRepository.findAll()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getAllContactTypes());
    }

    @Test
    void getMaintenanceTypesSuccessfullyCase() {
        //Arrange
        List<MaintenanceTypeDTO> listDTO = Collections.singletonList(new MaintenanceTypeDTO(1,OtherFlowData.RANDOM_NAME,"Maintenance Type"));
        when(paRepository.getMaintenanceTypes()).thenReturn(listDTO);
        //Act
        List<MaintenanceTypeDTO> actualDTO = otherFlowServiceImpl.getMaintenanceTypes();
        //Assert
        Assertions.assertEquals(listDTO,actualDTO);
        Assertions.assertEquals(1,actualDTO.size());
    }

    @Test
    void getMaintenanceTypeDTOThrowProgramaAvisosException() {
        //Arrange
        when(paRepository.getMaintenanceTypes()).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.getMaintenanceTypes());
    }

    @Test
    void sendNewsletterThrowProgramaAvisosException() {
        //Arrange
        //Act
        //Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->  otherFlowServiceImpl.sendNewsletter(0,"cabarriosb@gmail.com"));
    }
}
