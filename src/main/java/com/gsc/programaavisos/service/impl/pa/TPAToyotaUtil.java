package com.gsc.programaavisos.service.impl.pa;

import com.gsc.programaavisos.dto.TpaSimulation;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.ContactTypeB;
import com.gsc.programaavisos.model.crm.entity.Mrs;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.MrsRepository;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.CarTasks;
import com.sc.commons.utils.PdfTasks;
import com.sc.commons.utils.StringTasks;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Log4j
@Component
public class TPAToyotaUtil {

    public static final NumberFormat formatter2Decimals					= new DecimalFormat("0.00");

    private final static String FILE_NAME_TEST = "D:/Toyota_Programa_Avisos.pdf";
    private final static String FILE_NAME = "Toyota_Programa_Avisos";
    private final static String LOGO_TOYOTA = "logo_toyota.png";

    private static URL LOGO_URL = null;
    private static String NOME = "";
    private static String MORADA = "";
    private static String CODIGO_POSTAL = "";
    private static String PAIS = "";
    private static String HEADER = "";
    private static String CONTACT_REASON = "";
    private static String ACCESSORY_1 = "";
    private static String ACCESSORY_1_DESC = "";
    private static String ACCESSORY_1_IMG = "";
    private static String ACCESSORY_2 = "";
    private static String ACCESSORY_2_DESC = "";
    private static String ACCESSORY_2_IMG = "";
    private static String SERVICE_1 = "";
    private static String SERVICE_1_DESC = "";
    private static String SERVICE_1_IMG = "";
    private static String SERVICE_2 = "";
    private static String SERVICE_2_DESC = "";
    private static String SERVICE_2_IMG = "";
    private static String SERVICE_3 = "";
    private static String SERVICE_3_DESC = "";
    private static String SERVICE_3_IMG = "";
    private static String HIGHLIGHT_1 = "";
    private static String MAINTENANCE_PRICE= "";
    private static Character MAINTENANCE_CONTRACT= Character.MIN_VALUE;
    private static String TOYOTA5PLUS = "";

    private static String MODEL = "";
    private static String PLATE = "";
    private static String LAST_REVISION_KM ="";
    private static LocalDate LAST_REVISION_DATE;
    private static LocalDate NEXT_REVISION_DATE;
    private static LocalDate NEXT_ITV_DATE;
    private static String FOOTER_NEXT_REVISION_DESC;


    private static boolean TEST_PAGE = false;

    private static BaseFont bfToyotaType_Book;
    private static BaseFont bfToyotaType_Light;
    private static BaseFont bfToyotaType_Regular;
    private static BaseFont bfToyotaType_Semibold;

    private static int CONTACT_REASON_ID;

    private static boolean isMaintnanceContract = false;

    //COLORS
    private static final BaseColor CINZENTO_TOYOTA = new BaseColor(40, 40, 48);
    private static final BaseColor BRANCO = new BaseColor(255, 255, 255);
    private static final BaseColor VERMELHO_TOYOTA = new BaseColor(255, 0, 34);
    private static final BaseColor TME_GREY_1 = new BaseColor(73, 73, 80);
    private static final BaseColor TME_GREY_5 = new BaseColor(206, 207, 208);

    public static final String OWNER_IMPORTADOR = "IMPORTADOR";
    public static final String OWNER_DEALER = "DEALER";

    public static final int ID_CONTACT_TYPE_MAN = 1;
    public static final int ID_CONTACT_TYPE_ITV = 2;
    public static final int ID_CONTACT_TYPE_MAN_ITV = 3;
    public static final int ID_CONTACT_TYPE_BUS = 4;

    public static HashMap<Integer,String> CONTACT_REASON_MAP = new HashMap<Integer,String> ();

    private final MrsRepository mrsRepository;
    private final ProgramaAvisosUtil programaAvisosUtil;
    private final PARepository paRepository;


    @Autowired
    public TPAToyotaUtil(MrsRepository mrsRepository, ProgramaAvisosUtil programaAvisosUtil, PARepository paRepository) {
        this.mrsRepository = mrsRepository;
        this.programaAvisosUtil = programaAvisosUtil;
        this.paRepository = paRepository;
    }


    public File writePdf(java.util.List<TpaSimulation> simulations, boolean testPage, String saticFiles, String postalAccessoryUrl, String postalServiceUrl,
                                String postalHighlightUrl, String postalHeaderUrl, int year, int month, boolean isToSendGesDoc, String gesDocLocation) {

        log.info("Start writePdf");

        TEST_PAGE = testPage;
        /** This is the a4 format */
        // new Rectangle(595,842);
        List<File> files = new ArrayList<File>();


        try {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            String tempDir = "";

            bfToyotaType_Book = BaseFont.createFont(
                    saticFiles+ "/ToyotaType-Book.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfToyotaType_Light = BaseFont.createFont(
                    saticFiles + "/ToyotaType-Light.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfToyotaType_Regular = BaseFont.createFont(
                    saticFiles + "/ToyotaType-Regular.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfToyotaType_Semibold = BaseFont.createFont(
                    saticFiles + "/ToyotaType-Semibold.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            if (testPage) {
                Document document = new Document(new Rectangle(635, 882));
                PdfWriter pdfw = PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME_TEST));
                document.open();

                LOGO_URL = new URL(saticFiles+"/"+LOGO_TOYOTA);
                NOME = "Florisul 2 - Prod.Comercializa��o de Flores e Produtos Agricolas, Lda.";
                MORADA = "Rua do Penas n� 18, Aptd 44";
                CODIGO_POSTAL = "2870-392 Montijo";
                PAIS = "Portugal";
                HEADER = "WebContent/media/common/images/header_test.jpg";
                CONTACT_REASON = "revis�o e inspe��o";
                CONTACT_REASON_ID =3;
                ACCESSORY_1 = "F�MEAS DE SEGURAN�A";
                ACCESSORY_1_DESC = "Dite porisqu atianim illanducim repedit atiatia doluptu ribusaperior saeratur? Quidendam, earis con explam repelendit, utatist, te alitatqui cone plaut latur aditecus reped experat ionsequi accaborendi re none quam ime vollanis experspient eat et ex et et essimi, ommodi cum dolorerion et molorest odicae intinctat doluptaecum.";
                ACCESSORY_1_IMG = "WebContent/media/common/images/acessorio_1.jpg";
                ACCESSORY_2 = "FILTRO HABIT�CULO COM CARV�O ATIVO";
                ACCESSORY_2_DESC = "Dite porisqu atianim illanducim repedit atiatia doluptu ribusaperior saeratur? Quidendam, earis con explam repelendit, utatist, te alitatqui cone plaut latur aditecus reped experat ionsequi accaborendi re none quam ime vollanis experspient eat et ex et et essimi, ommodi cum dolorerion et molorest odicae intinctat doluptaecum.";
                ACCESSORY_2_IMG = "WebContent/media/common/images/acessorio_2.jpg";
                SERVICE_1 = "REPARA��O DE PEQUENOS DANOS";
                SERVICE_1_DESC = "Laboribus dit offic tem hil ium expelectiunt, to bla qui offic tendigni vel militi conserem as et odit moluptat. Quiaspercit, od mos eum, cullandit, eaquae consed.";
                SERVICE_1_IMG ="WebContent/media/common/images/service_1.png";
                SERVICE_2 = "SERVI�O DE PNEUS";
                SERVICE_2_DESC = "Laboribus dit offic tem hil ium expelectiunt, to bla qui offic tendigni vel militi conserem as et odit moluptat. Quiaspercit, od mos eum, cullandit, eaquae consed.";
                SERVICE_2_IMG ="WebContent/media/common/images/service_2.png";
                SERVICE_3 = "MARCA��O DE SERVI�OS ONLINE";
                SERVICE_3_DESC = "Laboribus dit offic tem hil ium expelectiunt, to bla qui offic tendigni vel militi conserem as et odit moluptat. Quiaspercit, od mos eum, cullandit, eaquae consed.";
                SERVICE_3_IMG ="WebContent/media/common/images/service_3.png";
                HIGHLIGHT_1 = "WebContent/media/common/images/destaque.jpg";

                MODEL = "COROLA";
                PLATE = "00-OO-00";
                LAST_REVISION_DATE = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
                NEXT_REVISION_DATE = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
                NEXT_ITV_DATE = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
                MAINTENANCE_PRICE = "349";
                MAINTENANCE_CONTRACT = 'N';
                FOOTER_NEXT_REVISION_DESC = "Valor previsto para a Revis�o Programada dos 15000 Km. A revis�o inclui todas as opera��es recomendadas pela Toyota. Pre�os em vigor na oficina onde realizou o ultimo servi�o";
                write(pdfw, document, null);
                document.close();
                File oFile = new File(FILE_NAME_TEST);
                files.add(oFile);
            } else {

                tempDir = System.getProperty("java.io.tmpdir");
                Document document = null;
                PdfWriter pdfw = null;
                try{
                    for (TpaSimulation simulation : simulations) {

                        document = new Document(new Rectangle(635, 882));

                        pdfw = PdfWriter.getInstance(document,
                                new FileOutputStream(tempDir + File.separator + FILE_NAME+"_"+year+"_"+month+"_"+simulation.getPaData().getLicensePlate()+".pdf"));

                        document.open();

                        LOGO_URL = new URL(saticFiles+"/"+LOGO_TOYOTA);
                        if (simulation.getMrs() == null && (simulation.getPaData().getIdContactType() == ContactTypeB.MAN || simulation.getPaData().getIdContactType() == ContactTypeB.MAN_ITV || simulation.getPaData().getIdContactType()  == ContactTypeB.ITV)) {
                            Mrs byIdPaData = mrsRepository.getByIdPaData(simulation.getPaData().getId());
                            simulation.setMrs(byIdPaData);
                        }

                        if (simulation.getPaData() != null &&
                                simulation.getMrs() != null) {

                            ProgramaAvisos paData = simulation.getPaData();
                            NOME = StringTasks.cleanString(StringTasks.CorrectUTF8(paData.getName()), "");
                            MORADA = StringTasks.cleanString( StringTasks.CorrectUTF8(paData.getAddress()), "");
                            CODIGO_POSTAL = StringTasks.cleanString(StringTasks.CorrectUTF8(paData.getCp4()), "") + "-"
                                    + StringTasks.cleanString(StringTasks.CorrectUTF8(paData.getCp3()), "") + " "
                                    + StringTasks.cleanString(StringTasks.CorrectUTF8(paData.getCpext()), "");

                            PAIS = "Portugal";
                            HEADER = simulation.getHeader1ImgPostal() != null
                                    ? postalHeaderUrl + "/" + simulation.getHeader1ImgPostal() : "";

                            CONTACT_REASON = StringTasks.cleanString(StringTasks.CorrectUTF8(CONTACT_REASON_MAP.get(simulation.getCarInfo().getIdContactReason())),
                                    "");
                            CONTACT_REASON_ID = simulation.getCarInfo().getIdContactReason();


                            ACCESSORY_1 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getMrs().getAcessory1()), "");
                            ACCESSORY_1_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getMrs().getAcessory1Desc()),"");
                            ACCESSORY_1_IMG = simulation.getMrs().getAcessory1ImgPostal() != null ? postalAccessoryUrl + "/" + simulation.getMrs().getAcessory1ImgPostal() : "";

                            ACCESSORY_2 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getMrs().getAcessory2()), "");
                            ACCESSORY_2_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getMrs().getAcessory2Desc()),"");
                            ACCESSORY_2_IMG = simulation.getMrs().getAcessory2ImgPostal() != null ? postalAccessoryUrl + "/" + simulation.getMrs().getAcessory2ImgPostal() : "";

                            SERVICE_1 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService1Name()), "");
                            SERVICE_1_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService1Desc()), "");
                            SERVICE_1_IMG =simulation.getService1ImgPostal() != null? postalServiceUrl + "/" + simulation.getService1ImgPostal() : "";
                            SERVICE_2 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService2Name()), "");
                            SERVICE_2_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService2Desc()), "");
                            SERVICE_2_IMG =simulation.getService2ImgPostal() != null? postalServiceUrl + "/" + simulation.getService2ImgPostal() : "";
                            SERVICE_3 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService3Name()), "");
                            SERVICE_3_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService3Desc()), "");
                            SERVICE_3_IMG =simulation.getService3ImgPostal() != null? postalServiceUrl + "/" + simulation.getService3ImgPostal() : "";
                            HIGHLIGHT_1 = simulation.getHighlight1ImgPostal() != null
                                    ? postalHighlightUrl + "/" + simulation.getHighlight1ImgPostal() : "";

                            MODEL = "--";
                            if(simulation.getCarInfo() != null && simulation.getCarInfo().getCar()!=null && simulation.getCarInfo().getCar().getModel()!=null && simulation.getCarInfo().getCar().getModel().getDesig()!= null && !simulation.getCarInfo().getCar().getModel().getDesig().equals("")){
                                MODEL 		       = simulation.getCarInfo().getCar().getModel().getDesig();
                            }else if( simulation.getPaData()!=null && simulation.getPaData().getModel()!=null && !simulation.getPaData().getModel().equals("")){
                                MODEL = simulation.getPaData().getModel();
                            }

                            PLATE = CarTasks.formatPlate(simulation.getPaData().getLicensePlate()) ;

                            LAST_REVISION_DATE = null;
                            LAST_REVISION_KM = "";
                            if(simulation.getCarInfo()!=null && simulation.getCarInfo().getDtLastRevision() != null){
                                LAST_REVISION_DATE = simulation.getCarInfo().getDtLastRevision();
                            }else if(simulation.getMrs() != null && simulation.getMrs().getDtLastRevision() != null){
                                LAST_REVISION_DATE =simulation.getMrs().getDtLastRevision();
                            }else if(simulation.getCarInfo()!=null && simulation.getCarInfo().getDtLastRevisionString() != null && !simulation.getCarInfo().getDtLastRevisionString().equals("")){
                                LAST_REVISION_KM =simulation.getCarInfo().getDtLastRevisionString();
                            }

                            NEXT_REVISION_DATE = null;
                            if(simulation.getCarInfo()!=null && simulation.getCarInfo().getDtNextRevision() != null ){
                                NEXT_REVISION_DATE = simulation.getCarInfo().getDtNextRevision();
                            }else if(simulation.getMrs() != null && simulation.getMrs().getDtNextRevision() != null ){
                                NEXT_REVISION_DATE =simulation.getMrs().getDtNextRevision();
                            }

                            NEXT_ITV_DATE = null;
                            if(simulation.getCarInfo()!=null && simulation.getCarInfo().getDtItv() != null ){
                                NEXT_ITV_DATE = simulation.getCarInfo().getDtItv();
                            }else if(simulation.getMrs() != null && simulation.getMrs().getDtItv() != null){
                                NEXT_ITV_DATE = simulation.getMrs().getDtItv();
                            }

                            MAINTENANCE_PRICE = (simulation.getMrs().getMaintenancePrice() == 0) ? "" : formatPriceToPdf(simulation.getMrs().getMaintenancePrice());
                            MAINTENANCE_CONTRACT = simulation.getMrs().getFlagMaintenanceContract();

                            double priceDiscountPerc = simulation.getMrs().getMaintenancePriceDiscountPerc() == null ? 0 : simulation.getMrs().getMaintenancePriceDiscountPerc().doubleValue();
                            double priceDiscount = simulation.getMrs().getMaintenancePriceDiscountValue() == null ? 0 : simulation.getMrs().getMaintenancePriceDiscountValue().doubleValue();

                            TOYOTA5PLUS = (!StringTasks.cleanString(String.valueOf(simulation.getMrs().getFlag5Plus()), "N").equalsIgnoreCase("S") || priceDiscountPerc <= 0) ? "" : priceDiscountPerc >= 0.1 ? " (j� inclui o desconto de " + formatPriceToPdf(priceDiscount) + " relativo ao servi�o Toyota 5+)." : ", o qual j� inclui o desconto relativo ao servi�o Toyota 5+.";


                            if (simulation.getMrs().getSkinDoPostal() != null && simulation.getMrs().getSkinDoPostal().toUpperCase().indexOf("CM") != -1) {
                                isMaintnanceContract = true;
                                MAINTENANCE_PRICE = formatPriceToPdf(simulation.getMrs().getMaintenancePrice());
                                TOYOTA5PLUS = " "+formatPriceToPdf(0) + " (ao abrigo do seu Contrato de Manuten��o)";
                            }

                            String footerNextRevisionDesc = "";
                            if(!simulation.getMrs().getNextRevision().equals("")){
                                footerNextRevisionDesc = "Valor previsto para a " + simulation.getMrs().getNextRevision() +
                                        ". A revis�o inclui todas as opera��es recomendadas pela "
                                        +((simulation.getPaData().getBrand().equalsIgnoreCase("T"))?"Toyota":"Lexus")+". Pre�o em vigor na oficina onde realizou o �ltimo servi�o.";
                                Character isFirstRevision = simulation.getMrs().getIsFirstRevision();
                                if (String.valueOf(isFirstRevision).equals("S")) {
                                    footerNextRevisionDesc = "Valor previsto para a " + simulation.getMrs().getNextRevision() + ". A revis�o inclui todas as opera��es recomendadas pela "+((simulation.getPaData().getBrand().equalsIgnoreCase("T"))?"Toyota":"Lexus")+".";
                                }
                                if (simulation.getMrs().getMaintenancePrice() == 0) {
                                    //								footerNextRevisionDesc = ((simulation.getPaData().getBrand().equalsIgnoreCase("T"))?"":"") + simulation.getPaData().getMRS().getNextRevision() + ". A revis�o inclui todas as opera��es recomendadas pela "+((simulation.getPaData().getBrand().equalsIgnoreCase("T"))?"Toyota":"Lexus")+".";
                                    footerNextRevisionDesc = simulation.getMrs().getNextRevision() + ". A revis�o inclui todas as opera��es recomendadas pela "+((simulation.getPaData().getBrand().equalsIgnoreCase("T"))?"Toyota":"Lexus")+".";
                                }
                            }

                            FOOTER_NEXT_REVISION_DESC = footerNextRevisionDesc;

                            write(pdfw, document, paData);
                            //document.newPage();
                            isMaintnanceContract = false;
                        }

                        document.close();
                        pdfw.close();
                        File oFile = new File(tempDir + File.separator + FILE_NAME+"_"+year+"_"+month+"_"+simulation.getPaData().getLicensePlate()+".pdf");
                        if(isToSendGesDoc){
                            String documentId = programaAvisosUtil.uploadFileGesDoc("T", simulation.getPaData().getLicensePlate(), simulation.getPaData().getVin(), oFile, gesDocLocation,"TPA/LPA");
                            if(documentId!=null && !documentId.equals("")){
                                ProgramaAvisos paData = simulation.getPaData();
                                paData.setIdDocument(documentId);
                                Timestamp dtChan = new Timestamp(System.currentTimeMillis());
                                paRepository.saveDocumentId(paData.getIdDocument(), "PDFCreatorToyota", dtChan, paData.getId());
                            }
                        }
                        files.add(oFile);
                    }
                }finally{
                    if(document!=null && document.isOpen()){
                        document.close();
                    }
                    if(pdfw!=null){
                        pdfw.close();
                    }
                }

            }

            log.info("End writePdf");

            if(files.size()>1){
                File[] filesArray = files.toArray(new File[files.size()]);
                return PdfTasks.mergePdfFiles(filesArray, FILE_NAME+"_"+year+"_"+month+".pdf");
            }else{
                return files.get(0);
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error write pdf ", e);
        } finally {
            if(files!=null && files.size()>1){
                for(File file:files){
                    if(file!=null && file.isFile()){
                        file.delete();
                    }
                }
            }
        }
    }

    private static void write(PdfWriter pdfw, Document document, ProgramaAvisos paData) {
        try {
            if((paData!=null && paData.getOwner()!=null && paData.getOwner().equalsIgnoreCase(OWNER_IMPORTADOR))||(paData == null)){
                log.trace("addTopPage");
                addTopPage(pdfw);
                log.trace("addAddress");
                addAddress(pdfw);
                log.trace("addHeader");
                addHeader(pdfw);
                log.trace("addContactReason");
                addContactReason(pdfw);
                log.trace("miras 1");
                miras(pdfw);
                log.trace("addNewPage");
                addNewPage(document, pdfw);
                log.trace("addAccessories");
                addAccessories(pdfw);
                log.trace("addServices");
                addServices(pdfw);
                log.trace("addHighlights");
                addHighlights(pdfw);
                log.trace("addBottomPage");
                addBottomPage(pdfw, null);
                log.trace("miras 2");
                miras(pdfw);
            }else if(paData.getOwner()!=null && paData.getOwner().equalsIgnoreCase(OWNER_DEALER)) {
                Dealer dealer = Dealer.getHelper().getByObjectId(Dealer.OID_NET_TOYOTA, paData.getOidDealer());
                log.trace("addTopPage");
                addTopPage(pdfw);
                log.trace("addAddress");
                addAddress(pdfw);
                log.trace("addHeader");
                addHeader(pdfw);
                log.trace("addContactReason");
                addContactReason(pdfw);
                log.trace("addBottomPage");
                addBottomPage(pdfw, dealer);
                log.trace("miras 1");
                miras(pdfw);
                document.newPage();

                PdfContentByte canvas = pdfw.getDirectContent();
                canvas.rectangle(615, 867, 1, 5);
                canvas.setColorFill(BaseColor.WHITE);
                canvas.fill();

            }

        } catch (Exception e) {
            throw new ProgramaAvisosException("Error write ", e);
        }
    }

    private static void miras(PdfWriter pdfw){
        PdfContentByte canvas = pdfw.getDirectContent();
        //canto inferior esquerdo
        canvas.rectangle(10, 20, 5, 1);
        canvas.rectangle(20, 10, 1, 5);

        //canto inferior direito
        canvas.rectangle(620, 20, 5, 1);
        canvas.rectangle(615, 10, 1, 5);

        //canto superior esquerdo
        canvas.rectangle(10, 862, 5, 1);
        canvas.rectangle(20, 867, 1, 5);

        //canto superior direito
        canvas.rectangle(620, 862, 5, 1);
        canvas.rectangle(615, 867, 1, 5);

        canvas.setColorFill(BaseColor.BLACK);
        canvas.fill();
    }

    private static void addBottomPage(PdfWriter pdfw, Dealer dealer) throws MalformedURLException, IOException, DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();

        String dealerDesig = "";
        String dealerEnd = "";
        String dealerCp4 = "";
        String dealerCp3 = "";
        String dealerCpExt = "";
        if (dealer != null) {
            dealerDesig = dealer.getDesig();
            dealerEnd = dealer.getEnd();
            dealerCp4 = dealer.getCp4();
            dealerCp3 = dealer.getCp3();
            dealerCpExt = dealer.getCpExt();

            canvas.setFontAndSize(bfToyotaType_Semibold, 8);
            canvas.setColorFill(CINZENTO_TOYOTA);
            canvas.showTextAligned(Element.ALIGN_LEFT, dealerDesig, 70, 60, 0);
            canvas.setFontAndSize(bfToyotaType_Book, 8);
            canvas.showTextAligned(Element.ALIGN_LEFT, dealerEnd + ", " + dealerCp4 + "-" + dealerCp3 + " " + dealerCpExt,
                    70, 50, 0);
        } else {
            dealerDesig = "Toyota Caetano Portugal";
            dealerEnd = "Av. Vasco da Gama n� 1410";
            dealerCp4 = "4431";
            dealerCp3 = "956";
            dealerCpExt = "Vila Nova de Gaia";
            canvas.setFontAndSize(bfToyotaType_Semibold, 8);
            canvas.setColorFill(CINZENTO_TOYOTA);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Toyota Caetano Portugal", 70, 60, 0);
            canvas.setFontAndSize(bfToyotaType_Book, 8);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Linha Azul   808 248 248",
                    70, 50, 0);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Linha Azul - Atendimento personalizado entre as 9h00 e as 20h00, de 2� a 6�, exceto feriados. ", 70, 40, 0);
        }

        canvas.endText();
        canvas.stroke();

        Image img = Image.getInstance(LOGO_URL);

        img.scaleAbsoluteWidth(60);
        img.scaleAbsoluteHeight(40);
        img.setAbsolutePosition(505, 45);
        canvas.addImage(img);
    }

    private static void addHighlights(PdfWriter pdfw) throws DocumentException, MalformedURLException, IOException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.rectangle(15, 100, 605, 150);
        canvas.setColorFill(TME_GREY_5);
        canvas.fill();
        if (!HIGHLIGHT_1.equals("")) {
            Image img;
            if (TEST_PAGE) {
                img = Image.getInstance(HIGHLIGHT_1);
            } else {
                img = Image.getInstance(new URL(HIGHLIGHT_1));
            }
            img.scaleAbsoluteWidth(605);
            img.scaleAbsoluteHeight(150);
            img.setAbsolutePosition(15, 100);
            canvas.addImage(img);
        }
    }

    private static void addServices(PdfWriter pdfw) throws DocumentException, IOException {

        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();
        canvas.setFontAndSize(bfToyotaType_Book, 30);
        canvas.setColorFill(CINZENTO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, "SERVI�OS", 70, 490, 0);
        canvas.endText();
        canvas.stroke();

        canvas.rectangle(15, 250, 605, 200);
        canvas.setColorFill(TME_GREY_1);
        canvas.fill();

        Image imgService1 = null;
        Image imgService2  = null;;
        Image imgService3  = null;;

        if (!SERVICE_1_IMG.equals("")) {
            if (TEST_PAGE) {
                imgService1 = Image.getInstance(SERVICE_1_IMG);
            } else {
                imgService1 = Image.getInstance(new URL(SERVICE_1_IMG));
            }
            imgService1.scaleAbsoluteWidth(40);
            imgService1.scaleAbsoluteHeight(40);
        }
        if (!SERVICE_2_IMG.equals("")) {
            if (TEST_PAGE) {
                imgService2 = Image.getInstance(SERVICE_2_IMG);
            } else {
                imgService2 = Image.getInstance(new URL(SERVICE_2_IMG));
            }
            imgService2.scaleAbsoluteWidth(40);
            imgService2.scaleAbsoluteHeight(40);
        }

        if (!SERVICE_3_IMG.equals("")) {
            if (TEST_PAGE) {
                imgService3 = Image.getInstance(SERVICE_3_IMG);
            } else {
                imgService3 = Image.getInstance(new URL(SERVICE_3_IMG));
            }
            imgService3.scaleAbsoluteWidth(40);
            imgService3.scaleAbsoluteHeight(40);
        }

        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{1,1,1});
        table.setTotalWidth(550);

        Font redFont = new Font(bfToyotaType_Regular, 10, Font.NORMAL, VERMELHO_TOYOTA);

        PdfPCell cell  = null;
        if(imgService1!=null){
            cell = new PdfPCell(imgService1);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
        if(imgService2!=null){
            cell = new PdfPCell(imgService2);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
        if(imgService3!=null){
            cell = new PdfPCell(imgService3);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(25);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase(SERVICE_1.toUpperCase(), redFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(15);
        cell.setPaddingRight(20);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(SERVICE_2.toUpperCase(), redFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(15);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(SERVICE_3.toUpperCase(),redFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(25);
        cell.setPaddingTop(15);
        table.addCell(cell);

        Font ttb9 = new Font(bfToyotaType_Book, 8, Font.NORMAL, BRANCO);

        cell = new PdfPCell(new Phrase(SERVICE_1_DESC, ttb9));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingTop(10);
        cell.setPaddingRight(25);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(SERVICE_2_DESC, ttb9));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(SERVICE_3_DESC,ttb9));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(25);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        table.addCell(cell);

        table.writeSelectedRows(0,3,0,3,45,435,canvas);
    }

    private static void addAccessories(PdfWriter pdfw) throws DocumentException, IOException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();

        canvas.setFontAndSize(bfToyotaType_Book, 30);
        canvas.setColorFill(CINZENTO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, "ACESS�RIOS", 70, 800, 0);

        canvas.setFontAndSize(bfToyotaType_Regular, 10);
        canvas.setColorFill(VERMELHO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, ACCESSORY_1.toUpperCase(), 70, 750, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ACCESSORY_2.toUpperCase(), 70, 630, 0);

        canvas.endText();
        canvas.stroke();

        Font ttb9 = new Font(bfToyotaType_Book, 9, Font.NORMAL, CINZENTO_TOYOTA);

        canvas.setColorFill(CINZENTO_TOYOTA);
        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(new Rectangle(70, 0, 300, 740));
        ct.addElement(new Paragraph(ACCESSORY_1_DESC, ttb9));
        ct.go();

        ct.setSimpleColumn(new Rectangle(70, 0, 300, 620));
        ct.addElement(new Paragraph(ACCESSORY_2_DESC, ttb9));
        ct.go();
        Image img;
        if (!ACCESSORY_1_IMG.equals("")) {
            if (TEST_PAGE) {
                img = Image.getInstance(ACCESSORY_1_IMG);
            } else {
                img = Image.getInstance(new URL(ACCESSORY_1_IMG));
            }
            img.scaleAbsoluteWidth(210);
            img.scaleAbsoluteHeight(110);
            img.setAbsolutePosition(410, 660);
            canvas.addImage(img);
        }
        if (!ACCESSORY_2_IMG.equals("")) {
            if (TEST_PAGE) {
                img = Image.getInstance(ACCESSORY_2_IMG);
            } else {
                img = Image.getInstance(new URL(ACCESSORY_2_IMG));
            }
            img.scaleAbsoluteWidth(210);
            img.scaleAbsoluteHeight(110);
            img.setAbsolutePosition(410, 540);
            canvas.addImage(img);
        }
    }

    private static void addNewPage(Document document, PdfWriter pdfw) {
        document.newPage();
        PdfContentByte canvas = pdfw.getDirectContent();
        canvas.rectangle(15, 854, 300, 15);
        canvas.setColorFill(VERMELHO_TOYOTA);
        canvas.fill();
    }

    private static void addContactReason(PdfWriter pdfw) throws DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();

        canvas.setFontAndSize(bfToyotaType_Light, 45);
        canvas.setColorFill(CINZENTO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, "CHEGOU A HORA", 70, 320, 0);
        canvas.setFontAndSize(bfToyotaType_Regular, 16);
        canvas.setColorFill(VERMELHO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, CONTACT_REASON.toUpperCase(), 70, 290, 0);

        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID||ID_CONTACT_TYPE_ITV == CONTACT_REASON_ID){
            canvas.setFontAndSize(bfToyotaType_Book, 6);
            canvas.setColorFill(CINZENTO_TOYOTA);
            canvas.showTextAligned(Element.ALIGN_LEFT, "* Servi�o v�lido nas oficinas Toyota aderentes.", 40, 80, 90);
        }
        canvas.setColorFill(CINZENTO_TOYOTA);

        canvas.endText();
        canvas.stroke();

        Paragraph paragraph0 =  new Paragraph();
        paragraph0.add(new Chunk("Caro/a Cliente "+NOME+",", new Font(bfToyotaType_Book, 10)));

        Paragraph paragraph1 =  new Paragraph();
        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID){
            FOOTER_NEXT_REVISION_DESC = "**"+FOOTER_NEXT_REVISION_DESC;
            TOYOTA5PLUS = TOYOTA5PLUS + "**";
            paragraph1.add(new Chunk("A nossa defini��o de compromisso � estarmos consigo. Incluindo agora, que estamos perto de dois momentos importantes para o seu Toyota: ", new Font(bfToyotaType_Book, 10)));
        }else if(ID_CONTACT_TYPE_MAN == CONTACT_REASON_ID){
            FOOTER_NEXT_REVISION_DESC = "*"+FOOTER_NEXT_REVISION_DESC;
            TOYOTA5PLUS = TOYOTA5PLUS + "*";
            paragraph1.add(new Chunk("A nossa defini��o de compromisso � estarmos consigo. Incluindo agora, que estamos perto de um momento t�o importante para o seu Toyota: ", new Font(bfToyotaType_Book, 10)));
        }else if(ID_CONTACT_TYPE_ITV == CONTACT_REASON_ID){
            FOOTER_NEXT_REVISION_DESC = "";
            paragraph1.add(new Chunk("A nossa defini��o de compromisso � estarmos consigo. Incluindo agora, que estamos perto de um momento t�o importante para o seu Toyota: ", new Font(bfToyotaType_Book, 10)));
        }
        paragraph1.add(new Chunk("a "+CONTACT_REASON, new Font(bfToyotaType_Book, 10,1,VERMELHO_TOYOTA)));

        Paragraph paragraph2 =  new Paragraph();
        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID){
            paragraph2.add(new Chunk("Na sua oficina Toyota, trabalha uma equipa de profissionais preparados para garantir os melhores cuidados e servi�os. Tudo, porque sabemos o que significa para si ter um servi�o de manuten��o completo, com solu��es pensadas � sua medida. Por isso, marque a revis�o e a pr�-inspe��o. E para que n�o tenha que se preocupar com nada, caso pretenda, tamb�m levamos o seu Toyota ao Centro de Inspe��es.* ", new Font(bfToyotaType_Book, 10)));
        }else if(ID_CONTACT_TYPE_MAN == CONTACT_REASON_ID){
            paragraph2.add(new Chunk("Na sua oficina Toyota, trabalha uma equipa de profissionais preparados para garantir os melhores cuidados e servi�os. Tudo, porque sabemos o que significa para si ter um servi�o de manuten��o completo, com solu��es pensadas � sua medida. Por isso, marque j� a sua revis�o, por telefone ou online. Para lhe garantir o m�ximo conforto e seguran�a, temos todas as normas de higieniza��o da DGS asseguradas.", new Font(bfToyotaType_Book, 10)));
        }else if(ID_CONTACT_TYPE_ITV == CONTACT_REASON_ID){
            paragraph2.add(new Chunk("Na sua oficina Toyota, trabalha uma equipa de profissionais preparados para garantir os melhores cuidados e servi�os. Incluindo a pr�-inspe��o que pode agendar j�, por telefone ou online. E para que n�o tenha que se preocupar com nada, caso pretenda, tamb�m levamos o seu Toyota ao Centro de Inspe��es.* Tudo, com as normas de higieniza��o da DGS asseguradas. ", new Font(bfToyotaType_Book, 10)));
        }
        Paragraph paragraph3 =  new Paragraph();
        paragraph3.add(new Chunk("Estaremos � sua espera.", new Font(bfToyotaType_Book, 10, 1,VERMELHO_TOYOTA)));

        Paragraph paragraph4 =  new Paragraph();
        paragraph4.add(new Chunk("Modelo: ", new Font(bfToyotaType_Semibold, 10)));
        paragraph4.add(new Chunk(MODEL!=null?MODEL:"--", new Font(bfToyotaType_Book, 10)));

        Paragraph paragraph5 =  new Paragraph();
        paragraph5.add(new Chunk("Matr�cula: ", new Font(bfToyotaType_Semibold, 10)));
        paragraph5.add(new Chunk( PLATE!=null?PLATE:"--", new Font(bfToyotaType_Book, 10)));


        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatterLD = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        boolean showLastRevisionDate = true;
        Paragraph paragraph6 =  new Paragraph();
        paragraph6.add(new Chunk("�ltima Revis�o: " , new Font(bfToyotaType_Semibold, 10)));
        if(LAST_REVISION_DATE!=null){
            date = LAST_REVISION_DATE.format(formatterLD);
            paragraph6.add(new Chunk( date, new Font(bfToyotaType_Book, 10)));
        }else if(!LAST_REVISION_KM.equals("")){
            paragraph6.add(new Chunk(LAST_REVISION_KM , new Font(bfToyotaType_Book, 10)));
        }else{
            showLastRevisionDate = false;
        }

        boolean showNextRevisionDate = true;
        Paragraph paragraph7 =  new Paragraph();
        paragraph7.add(new Chunk("Data da pr�xima revis�o: ", new Font(bfToyotaType_Semibold, 10)));

        if(NEXT_REVISION_DATE!=null){
            date = NEXT_REVISION_DATE.format(formatterLD);
            paragraph7.add(new Chunk( date, new Font(bfToyotaType_Book, 10)));
        }else{
            showNextRevisionDate = false;
        }

        Paragraph paragraph8 =  new Paragraph();
        paragraph8.add(new Chunk("Data limite inspe��o: ", new Font(bfToyotaType_Semibold, 10)));
        if(NEXT_ITV_DATE!=null){
            date = NEXT_ITV_DATE.format(formatterLD);
            paragraph8.add(new Chunk(date, new Font(bfToyotaType_Book, 10)));
        }else{
            paragraph8.add(new Chunk("--" , new Font(bfToyotaType_Book, 10)));
        }

//		if (isMaintenanceContract) {
//			fontSize = "18px";
//			price = "0�";
//		} else if (isFree) {
//			price = "Sob Consulta*";
//		}

        Paragraph paragraph9 =  new Paragraph();
        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID || ID_CONTACT_TYPE_MAN == CONTACT_REASON_ID){
            paragraph9.add(new Chunk("Valor da Revis�o: ", new Font(bfToyotaType_Semibold, 10)));
            if (!MAINTENANCE_CONTRACT.equals("") && String.valueOf(MAINTENANCE_CONTRACT).equalsIgnoreCase("S")){
                paragraph9.add(new Chunk("0�" , new Font(bfToyotaType_Book, 10)));
            } else if(!MAINTENANCE_PRICE.equals("")){
                if(isMaintnanceContract){
                    paragraph9.add(new Chunk(MAINTENANCE_PRICE, new Font(bfToyotaType_Book, 10,Font.STRIKETHRU)));
                    paragraph9.add(new Chunk(TOYOTA5PLUS, new Font(bfToyotaType_Book, 10)));
                }else{
                    paragraph9.add(new Chunk(MAINTENANCE_PRICE+TOYOTA5PLUS, new Font(bfToyotaType_Book, 10)));
                }
            }else{
                paragraph9.add(new Chunk("Sob Consulta*" , new Font(bfToyotaType_Book, 10)));
            }
        }

        PdfPCell cell;
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(300);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1});

        cell = new PdfPCell(paragraph0);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingBottom(10);
        table.addCell(cell);

        cell = new PdfPCell(paragraph1);
        cell.setLeading(1.5f, 1.5f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        table.addCell(cell);

        table.writeSelectedRows(0,2, 70, 270, canvas);

        /*---INFORMA��ES DO VEICULO---*/
        table = new PdfPTable(1);
        table.setTotalWidth(200);
        table.setWidthPercentage(100);

        cell = new PdfPCell(paragraph4);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(15);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(paragraph5);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(15);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        if(showLastRevisionDate){
            cell = new PdfPCell(paragraph6);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }

        if(showNextRevisionDate){
            cell = new PdfPCell(paragraph7);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }

        cell = new PdfPCell(paragraph8);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(15);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID || ID_CONTACT_TYPE_MAN == CONTACT_REASON_ID){
            cell = new PdfPCell(paragraph9);
            cell.setLeading(1.1f, 1.1f);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }

        table.writeSelectedRows(0, -1, 370, 295, canvas);

        /*------*/
        table = new PdfPTable(1);
        table.setTotalWidth(500);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1});

        cell = new PdfPCell(paragraph2);
        cell.setLeading(1.5f, 1.5f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(paragraph3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(15);
        table.addCell(cell);

        table.writeSelectedRows(0,15, 70, 195, canvas);


        canvas.beginText();
        canvas.setFontAndSize(bfToyotaType_Book, 6);
        canvas.setColorFill(CINZENTO_TOYOTA);
        canvas.showTextAligned(Element.ALIGN_LEFT, FOOTER_NEXT_REVISION_DESC, 70, 30, 0);
        canvas.endText();
        canvas.stroke();


    }

    private static void addHeader(PdfWriter pdfw) throws MalformedURLException, IOException, DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();
        if (!HEADER.equals("")) {

            Image img;
            if (TEST_PAGE) {
                log.trace(TEST_PAGE+" getting image HEADER >> "+HEADER);
                img = Image.getInstance(HEADER);
            } else {
                log.trace(TEST_PAGE+" getting image HEADER >> "+HEADER);
                img = Image.getInstance(new URL(HEADER));
            }
            img.scaleAbsoluteWidth(550);
            img.scaleAbsoluteHeight(230);
            img.setAbsolutePosition(70, 370);
            canvas.addImage(img);
        }
    }

    private static void addAddress(PdfWriter pdfw) throws DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.setColorFill(CINZENTO_TOYOTA);
        Font addressFont = new Font(bfToyotaType_Book, 10);

        PdfPCell cell;
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(250);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1});

        Paragraph par1 = new Paragraph();
        par1.add(new Chunk(NOME,addressFont));

        cell = new PdfPCell(par1);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        Paragraph par2 = new Paragraph();
        par2.add(new Chunk(MORADA,addressFont));

        cell = new PdfPCell(par2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        Paragraph par3 = new Paragraph();
        par3.add(new Chunk(CODIGO_POSTAL,addressFont));

        cell = new PdfPCell(par3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        Paragraph par4 = new Paragraph();
        par4.add(new Chunk(PAIS,addressFont));

        cell = new PdfPCell(par4);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        table.writeSelectedRows(0,-1, 320, 735, canvas);
    }

    private static void addTopPage(PdfWriter pdfw) throws DocumentException, MalformedURLException, IOException {
        PdfContentByte canvas = pdfw.getDirectContent();
        canvas.rectangle(320, 854, 300, 15);
        canvas.setColorFill(VERMELHO_TOYOTA);
        canvas.fill();

        log.trace("getting image LOGO_URL >> "+LOGO_URL);

        Image img = Image.getInstance(LOGO_URL);

        img.scaleAbsoluteWidth(60);
        img.scaleAbsoluteHeight(40);
        img.setAbsolutePosition(50, 795);
        canvas.addImage(img);
    }

    public static String formatPriceToPdf(double valDouble) {
        String value = formatter2Decimals.format(valDouble);
        value = StringTasks.ReplaceStr(value, ".", ",") + "�";
        return StringTasks.cleanString(value, "");
    }

    static{
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_MAN, "Revisão");
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_ITV, "Inspeção");
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_MAN_ITV, "Revisão e Inspeção");

    }


}
