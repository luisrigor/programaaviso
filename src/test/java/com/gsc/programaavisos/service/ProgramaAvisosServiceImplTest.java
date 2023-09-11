package com.gsc.programaavisos.service;

import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.gsc.programaavisos.repository.crm.QuarantineRepository;
import com.gsc.programaavisos.repository.crm.VehicleRepository;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.ProgramaAvisosServiceImpl;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import com.gsc.programaavisos.util.TPAInvokerSimulator;
import com.gsc.ws.core.*;
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
import java.time.LocalDate;
import java.util.*;

import static com.gsc.programaavisos.constants.AppProfile.ROLE_VIEW_CALL_CENTER_DEALERS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class ProgramaAvisosServiceImplTest {

    public final SimpleDateFormat timeZoFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    @Mock
    private PARepository paRepository;
    @Mock
    private PABeanRepository paBeanRepository;
    @Mock
    private ProgramaAvisosUtil programaAvisosUtil;
    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private CallsRepository callsRepository;
    @Mock
    private TPAInvokerSimulator tpaInvokerSimulator;
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

    @Test
    void whenSavePAAndIdDoNotFoundThenThrowProgramaAvisosException() {
        // Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        when(paRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> programaAvisosService.savePA(SecurityData.getUserDefaultStatic(),padto));
    }

    @Test
    void whenRemovePAIdDoNotFoundThenThrowProgramaAvisosException() {
        // Arrange
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

    @Test
    void unlockPARegisterThenThrowSuccessfullyCase() {
        // Arrange
        doThrow(ProgramaAvisosException.class).when(paRepository).updateBlockedByById(anyString(),anyInt());
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,()->programaAvisosService.unlockPARegister(anyInt()));
    }

    @Test
    void activatePAThenThrowSuccessfullyCase() {
        // Arrange
        when(paRepository.findById(anyInt())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,()->programaAvisosService.activatePA(any(),anyInt()));
    }

    @Test
    void getPaDetailSuccessfullyCase(){
        //Arrange
        UserPrincipal user = SecurityData.getUserDefaultStatic();
        List<Channel> channels = new ArrayList<>();
        List<Calls> calls = new ArrayList<>();

        when(paBeanRepository.getProgramaAvisosBeanById(anyInt()))
                .thenReturn(ProgramaAvisosData.getProgramaAvisosBean());
        doNothing().when(paRepository).updateBlockedByById(anyString(),anyInt());
        when(channelRepository.findAll()).thenReturn(channels);
        when(callsRepository.findByIdPaData(anyInt())).thenReturn(calls);
        //Act
        DetailsPADTO detailsPADTO = programaAvisosService.getPaDetail(user,1,2);
        //Assert
        Assertions.assertNull(detailsPADTO.getCalls());
        Assertions.assertTrue(detailsPADTO.getChannels().isEmpty());
    }

    @Test
    void getPaDetailThrowProgramaAvisosException(){
        //Arrange
        when(paBeanRepository.getProgramaAvisosBeanById(anyInt()))
                .thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                ()->programaAvisosService.getPaDetail(SecurityData.getUserDefaultStatic(),1,2));
    }

    @Test
    void getRevisionsSuccessfullyCase(){
        List<Revision> revisions = programaAvisosService.getRevisions(StringUtils.EMPTY);
        Assertions.assertTrue(revisions.isEmpty());
    }

    @Test
    void getWarrantiesSuccessfullyCase(){
        List<Warranty> warranties = programaAvisosService.getWarranties(StringUtils.EMPTY);
        Assertions.assertTrue(warranties.isEmpty());
    }

    @Test
    void getClaimsSuccessfullyCase(){
        List<Claim> claims = programaAvisosService.getClaims(StringUtils.EMPTY);
        Assertions.assertTrue(claims.isEmpty());
    }

    @Test
    void getRptsSuccessfullyCase(){
        List<Rpt> rpts = programaAvisosService.getRpts(StringUtils.EMPTY);
        Assertions.assertTrue(rpts.isEmpty());
    }

    @Test
    void getCampaignsSuccessfullyCase(){
        List<Campaign> campaigns = programaAvisosService.getCampaigns(StringUtils.EMPTY);
        Assertions.assertTrue(campaigns.isEmpty());
    }

    @Test
    void sortCampaignsSuccessfullyCase(){
        List<Campaign> campaigns = programaAvisosService.getCampaigns(StringUtils.EMPTY);
        programaAvisosService.sortCampaigns(campaigns);
        Assertions.assertTrue(campaigns.isEmpty());
    }

    @Test
    void fillPAWsDataPlateNullCase() throws Exception {
        //Arrange
        ProgramaAvisosBean oPABean = ProgramaAvisosData.getProgramaAvisosBean();
        oPABean.setLicensePlate(StringUtils.EMPTY);
        //Act
        ProgramaAvisosBean actualOPABean = programaAvisosService.fillPAWsData(oPABean,true);
        //Assert
        Assertions.assertEquals(StringUtils.EMPTY,actualOPABean.getLicensePlate());
    }

    @Test
    void whenGetPaInfoThenReturnSuccessfully(){
        //Arrange
        String oldAddress = "oldAddress";
        String oldNif = "111111111";
        ProgramaAvisosBean oPaBean = ProgramaAvisosData.getProgramaAvisosBean();
        ProgramaAvisosBean oldPaBean = ProgramaAvisosData.getProgramaAvisosBean();
        oldPaBean.setNewAddress(oldAddress);
        oldPaBean.setNewNif(oldNif);
        oldPaBean.setDataIsCorrect("N");
        //Act
        ProgramaAvisosBean actualBean = programaAvisosService.getPaInfo(oPaBean,oldPaBean);
        //Assert
        Assertions.assertEquals(oldAddress,actualBean.getNewAddress());
        Assertions.assertEquals(oldNif,actualBean.getNewNif());
    }

    @Test
    void whenActivatePASuccessfully(){
        //Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        pa.setId(1);
        when(paRepository.findById(anyInt())).thenReturn(Optional.of(pa));
        doNothing().when(programaAvisosUtil).save(anyString(),anyBoolean(),any());
        //Act
        programaAvisosService.activatePA(SecurityData.getUserDefaultStatic(),1);
        //Assert
        Assertions.assertEquals(StringUtils.EMPTY,pa.getRemovedObs());
        Assertions.assertEquals("N",pa.getSuccessContact());
        Assertions.assertNull(pa.getHrScheduleContact());
        Assertions.assertNull(pa.getDtScheduleContact());
    }

    @Test
    void whenRemovePASuccessfully(){
        //Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        pa.setId(1);
        when(paRepository.findById(anyInt())).thenReturn(Optional.of(pa));
        doNothing().when(programaAvisosUtil).save(anyString(),anyBoolean(),any());
        //Act
        programaAvisosService.removePA(SecurityData.getUserDefaultStatic(),1,"removedOption",StringUtils.EMPTY);
        //Assert
        verify(programaAvisosUtil,times(1)).save(anyString(),anyBoolean(),any());
    }

    @Test
    void whenDataPAReturnThisSuccessfully(){
        //Arrange
        PADTO padto = ProgramaAvisosData.getPADTO();
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        when(paRepository.findById(anyInt())).thenReturn(Optional.of(pa));
        //Act
        ProgramaAvisos opa = programaAvisosService.dataPA(padto);
        //Assert
        Assertions.assertEquals(padto.getNewEmail(),opa.getNewEmail());
        Assertions.assertEquals(padto.getSuccessMotive(),opa.getSuccessMotive());
        Assertions.assertEquals(padto.getNewNif(),opa.getNewNif());
    }

    @Test
    void whenGetInfoPAReturnThisSuccessfully() {
        //Arrange
        PATotals paTotals = ProgramaAvisosData.getPATotals();
        List<ProgramaAvisosBean> paBeans = Collections.singletonList(ProgramaAvisosData.getProgramaAvisosBean());
        when(paBeanRepository.getPaTotals(any())).thenReturn(paTotals);
        when(paBeanRepository.getProgramaAvisosBean(any())).thenReturn(paBeans);
        //Act
        PAInfoDTO paInfoDTO = programaAvisosService.getInfoPA(SecurityData.getUserDefaultStatic());
        //Arrange
        Assertions.assertEquals(paTotals,paInfoDTO.getPaTotals());
        Assertions.assertEquals(paBeans,paInfoDTO.getPaInfoList());
    }

    @Test
    void whenGetInfoPAThenThrowProgramaAvisosException() {
        //Arrange
        when(paBeanRepository.getPaTotals(any())).thenThrow(ProgramaAvisosException.class);
        //Act & Arrange
        Assertions.assertThrows(ProgramaAvisosException.class,()->
                programaAvisosService.getInfoPA(SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenGetTpaSimulationReturnThisSuccessfully() throws SCErrorException {
        //Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        LocalDate localDate = LocalDate.now();
        TpaSimulation tpaExpected = TpaSimulation.builder().paData(pa).build();
        pa.setMRS(ProgramaAvisosData.getMrs());
        when(tpaInvokerSimulator.getTpaSimulation("nif","plate",localDate,false)).thenReturn(tpaExpected);
        //Act
        TpaSimulation tpa = programaAvisosService.getTpaSimulation(SecurityData.getUserDefaultStatic(), new TpaDTO("plate","nif", localDate));
        //Arrange
        Assertions.assertEquals(pa.getMRS().getAcessory1(),tpa.getAccessory1Name());
    }

    @Test
    void whenGetTpaSimulationThenThrowProgramaAvisosException() throws SCErrorException {
        //Arrange
        LocalDate localDate = LocalDate.now();
        when(tpaInvokerSimulator.getTpaSimulation("nif","plate",localDate,false))
                .thenThrow(ProgramaAvisosException.class);
        //Act & Arrange
        Assertions.assertThrows(ProgramaAvisosException.class,()->
                programaAvisosService.getTpaSimulation(SecurityData.getUserDefaultStatic(), new TpaDTO("plate","nif", localDate)));
    }

}
