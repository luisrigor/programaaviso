package com.gsc.programaavisos.service;

import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.dto.PACampaing;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.PaDataInfoP;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.model.crm.entity.TechnicalCampaigns;
import com.gsc.programaavisos.repository.crm.PaDataInfoRepository;
import com.gsc.programaavisos.repository.crm.TechnicalCampaignsRepository;
import com.gsc.programaavisos.sample.data.provider.BackOfficeData;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.BackOfficeServiceImpl;
import com.gsc.programaavisos.service.impl.ProgramaAvisosServiceImpl;
import com.gsc.programaavisos.service.impl.pa.ExcelUtils;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import com.gsc.programaavisos.util.PAUtil;
import com.rg.dealer.Dealer;
import com.sc.commons.comunications.Mail;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
public class BackOfficeServiceImplTest {

    @Mock
    private PaDataInfoRepository dataInfoRepository;
    @Mock
    private ExcelUtils excelUtils;
    @Mock
    private ProgramaAvisosUtil paUtils;
    @Mock
    private TechnicalCampaignsRepository tcRepository;
    @Mock
    private EnvironmentConfig environmentConfig;
    @Mock
    private Environment env;
    @Mock
    private com.rg.toyota.dealer.DealerHelper toyotaHelper;
    @Mock
    private com.rg.lexus.dealer.DealerHelper lexusHelper;
    @InjectMocks
    private BackOfficeServiceImpl backOfficeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parseAndUpdateSuccessfully() throws Exception {
        XSSFSheet lstFields = mock(XSSFSheet.class);
        XSSFRow lstFieldsRow = mock(XSSFRow.class);
        MockMultipartFile file = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());
        UserPrincipal user = SecurityData.getUserDefaultStatic();
        List<Dealer> listAllAfterSalesDealers = new ArrayList<>();
        Vector<Dealer> vecAllAfterSalesDealers = new Vector<>();
        Map<String, Dealer> mapMainDealersToyota = new HashMap<>();
        Map<String, Dealer> mapMainDealersLexus = new HashMap<>();
        Map<String, Dealer> mapAfterSalesDealers = new HashMap<>();
        try (
                MockedStatic<Mail> mail = Mockito.mockStatic(Mail.class);
                MockedStatic<Dealer> utilities = Mockito.mockStatic(Dealer.class)){
            when(env.getActiveProfiles()).thenReturn(new String[] {"notProduction"});
            utilities.when(Dealer::getToyotaHelper).thenReturn(toyotaHelper);
            utilities.when(Dealer::getLexusHelper).thenReturn(lexusHelper);
            when(toyotaHelper.getAllActiveDealerAftersales()).thenReturn(vecAllAfterSalesDealers);
            when(lexusHelper.getAllActiveDealerAftersales()).thenReturn(vecAllAfterSalesDealers);
            mail.when(()->Mail.getFooterNoReply()).thenReturn("Footer No Reply");
            mail.when(()->Mail.SendMail(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),any(File.class))).thenReturn(0);
            //Act
            backOfficeService.parseAndUpdate(lstFields,user,file);

            mail.verify(()->Mail.SendMail(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),any(File.class)));
        }

    }


    @Test
    void whenResultToMapReturnSuccessfully(){
        LocalDate date = LocalDate.now();
        PaDataInfo paDataInfo = PaDataInfo.builder().paLicensePlate("licensePlate")
                .tcCampaign("tcCampaing").tcGenerationDate(date).build();
        PaDataInfoP paDataInfoP = new PaDataInfoP();
        String keyPaTechincalCampign = "key";
        try (
                MockedStatic<PAUtil> paUtilMockedStatic = Mockito.mockStatic(PAUtil.class);
                MockedStatic<ProgramaAvisosServiceImpl> utilities = Mockito.mockStatic(ProgramaAvisosServiceImpl.class);
                ){
            utilities.when(()->ProgramaAvisosServiceImpl.fillPABean(any())).thenReturn(paDataInfoP);
            paUtilMockedStatic.when(()->PAUtil.getKeyByListElements(anyList())).thenReturn(keyPaTechincalCampign);
            Map<String, PaDataInfo> actualMapPA = backOfficeService.resultToMap(Collections.singletonList(paDataInfo));
            Assertions.assertTrue(actualMapPA.containsValue(paDataInfo));
        }
    }


    @Test
    void convertToFileSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());
        File convFile = backOfficeService.convertToFile(file);
        Assertions.assertNotNull(convFile);
    }

    @Test
    void confirmTechnicalCampaignSuccessfullyCase() {
        //Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        TechnicalCampaigns tc = BackOfficeData.getTechnicalCampaign();
        PACampaing paCampaing = BackOfficeData.getPACampaign();
        when(paUtils.insert(any(),anyBoolean(),anyString())).thenReturn(pa);
        when(tcRepository.save(any())).thenReturn(tc);
        //Act
        int numberOfRegistersImported = backOfficeService.confirmTechnicalCampaing(Collections.singletonList(paCampaing),"userStamp");
        //Assert
        Assertions.assertEquals(1,numberOfRegistersImported);
        Assertions.assertEquals("userStamp",paCampaing.getTc().getCreatedBy());
    }

    @Test
    void confirmTechnicalCampaignThenThrowProgramaAvisosException() {
        //Arrange
        PACampaing paCampaing = BackOfficeData.getPACampaign();
        when(paUtils.insert(any(),anyBoolean(),anyString())).thenThrow(ProgramaAvisosException.class);
        //Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,()->
                backOfficeService.confirmTechnicalCampaing(Collections.singletonList(paCampaing),"userStamp"));
    }



}
