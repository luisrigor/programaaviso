package com.gsc.programaavisos.service.pa;

import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.Calls;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.CallsRepository;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.service.impl.pa.ProgramaAvisosUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
public class ProgramaAvisosUtilTest {

    @Mock
    private EnvironmentConfig environmentConfig;
    @Mock
    private PARepository paRepository;
    @Mock
    private CallsRepository callsRepository;
    @InjectMocks
    private ProgramaAvisosUtil programaAvisosUtil;
    private SecurityData securityData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityData = new SecurityData();
    }

    @Test
    void whenSearchItemsListThenReturnSuccessfully() {
        // Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        Calls calls = ProgramaAvisosData.getCalls();
        when(callsRepository.save(any())).thenReturn(calls);
        when(paRepository.save(any())).thenReturn(pa);
        // Act
        programaAvisosUtil.save(pa.getCreatedBy(),true,pa);
        // Assert
        verify(paRepository,times(1)).save(any());
        verify(callsRepository,times(1)).save(any());
    }

    @Test
    void whenSearchItemsListThenThrowProgramaAvisosException() {
        // Arrange
        ProgramaAvisos pa = ProgramaAvisosData.getCompletePA();
        when(callsRepository.save(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,()->
                programaAvisosUtil.save(pa.getCreatedBy(),true,pa));
    }

    @Test
    void whenGetSystemUserThenReturnSuccessfully() {
        // Arrange
        String brand = "T";
        // Act
        String actualBrand = ProgramaAvisosUtil.getSystemUser(brand);
        // Assert
        Assertions.assertEquals("rigor.master@tpo",actualBrand);
    }

    @Test
    void whenGetSystemUserThenReturnEmpty() {
        // Arrange
        String brand = "ANY";
        // Act
        String actualBrand = ProgramaAvisosUtil.getSystemUser(brand);
        // Assert
        Assertions.assertEquals("",actualBrand);
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeManThenReturnSuccessfully() {
        // Arrange
        String brand = "T";
        int concatType = ContactTypeB.MAN;
        String requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;fontSize;price;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name;footernextrevisiondesc";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeITVThenReturnSuccessfully() {
        // Arrange
        String brand = "T";
        int concatType = ContactTypeB.ITV;
        String requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;nextitvdate;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name;footernextrevisiondesc";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeManITVThenReturnSuccessfully() {
        // Arrange
        String brand = "T";
        int concatType = ContactTypeB.MAN_ITV;
        String requiredFields = "urlimages;imghighlight1;imgservice2;linkheader1;imgheader1;imgservice3;imgaccessory1;imgaccessory2;nameservice1;nameservice2;nameservice3;linkgeneratedfromosb;model;fontSize;price;nextitvdate;nameaccessory1;nameaccessory2;descservice1;descservice2;descservice3;descaccessory1;descaccessory2;plate;nextrevisiondate;lastrevisiondate;imgservice1;linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1;isToShowLastRevisionDate;isToShowNextRevisionDate;name";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeManWithLexusThenReturnSuccessfully() {
        // Arrange
        String brand = "L";
        int concatType = ContactTypeB.MAN;
        String requiredFields = "urlimages;linkheader1;imgheader1;model;plate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeITVWithLexusThenReturnSuccessfully() {
        // Arrange
        String brand = "L";
        int concatType = ContactTypeB.ITV;
        String requiredFields = "urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }

    @Test
    void whenGetNewslettersFieldsAndContactTypeManITVWithLexusThenReturnSuccessfully() {
        // Arrange
        String brand = "L";
        int concatType = ContactTypeB.MAN_ITV;
        String requiredFields = "urlimages;linkheader1;imgheader1;model;plate;nextitvdate;name;linkgeneratedfromosb;imgservice1;nameservice1;descservice1;imgservice2;nameservice2;descservice2;imgservice3;nameservice3;descservice3;nameaccessory1;descaccessory1;imgaccessory1;nameaccessory2;descaccessory2;imgaccessory2;imghighlight1,linkservice1;linkservice2;linkservice3;linkaccessory1;linkaccessory2;linkhighlight1";
        // Act
        String[] newsLetter = ProgramaAvisosUtil.getNewslettersFields(concatType,brand);
        // Assert
        Assertions.assertEquals(2,newsLetter.length);
        Assertions.assertEquals(requiredFields,java.util.Arrays.stream(newsLetter).findFirst().get());
    }


}
