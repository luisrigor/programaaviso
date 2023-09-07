package com.gsc.programaavisos.service.impl.pa;

import com.gsc.programaavisos.dto.TpaSimulation;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.PARepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sc.commons.exceptions.SCErrorException;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Log4j
@Component
public class TPALexusUtil {

    private static final String FILE_NAME_TEST = "D:/Lexus_Programa_Avisos.pdf";
    private static final String FILE_NAME = "Lexus_Programa_Avisos";
    private static final String LOGO_LEXUS = "logo_lexus.png";
    private  final String BOOTOM_IMAGE = "7_anos_garantia_lexus.png";

    private static URL LOGO_URL = null;
    private static URL BOOTOM_IMAGE_URL = null;
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
    private static LocalDate NEXT_ITV_DATE = null;
    private static int CONTACT_REASON_ID;
    private static String MODEL = "";
    private static String PLATE = "";

    private static boolean TEST_PAGE = false;

    private static BaseFont bfNobelWGL_Book;
    private static BaseFont bfNobel_Bold;
    private static BaseFont bfNobel_Book;
    private static BaseFont bfNobelWGL_Bold;

    private static final BaseColor PRETO = new BaseColor(0, 0, 0);
    private static final BaseColor TME_GREY_6 = new BaseColor(239, 240, 240);

    public static final String OWNER_IMPORTADOR = "IMPORTADOR";
    public static final String OWNER_DEALER = "DEALER";

    public static final int ID_CONTACT_TYPE_MAN = 1;
    public static final int ID_CONTACT_TYPE_ITV = 2;
    public static final int ID_CONTACT_TYPE_MAN_ITV = 3;
    public static final int ID_CONTACT_TYPE_BUS = 4;

    public static HashMap<Integer,String> CONTACT_REASON_MAP = new HashMap<Integer,String> ();
    private final PARepository paRepository;
    private final ProgramaAvisosUtil programaAvisosUtil;


    @Autowired
    public TPALexusUtil(PARepository paRepository, ProgramaAvisosUtil programaAvisosUtil) {
        this.paRepository = paRepository;
        this.programaAvisosUtil = programaAvisosUtil;
    }


    public File writePdf(java.util.List<TpaSimulation> simulations, boolean testPage, String saticFiles, String postalAccessoryUrl, String postalServiceUrl,
                                String postalHighlightUrl, String postalHeaderUrl, int year, int month, boolean isToSendGesDoc, String gesDocLocation) throws SCErrorException, IOException {

        log.info("Start writePdf");

        TEST_PAGE = testPage;


        List<File> files = new ArrayList<File>();

        try {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            String tempDir = "";

            bfNobelWGL_Book = BaseFont.createFont(saticFiles + "/NobelWGL-Book.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfNobel_Bold = BaseFont.createFont(saticFiles + "/Nobel-Bold.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfNobel_Book = BaseFont.createFont(saticFiles + "/Nobel-Book.otf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            bfNobelWGL_Bold = BaseFont.createFont(saticFiles + "/NobelWGL-Bold.ttf", BaseFont.CP1252,
                    BaseFont.EMBEDDED);
            if (testPage) {
                Document document = new Document(new Rectangle(635, 882));
                PdfWriter pdfw = PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME_TEST));
                document.open();

                LOGO_URL = new URL(saticFiles + "/" + LOGO_LEXUS);
                BOOTOM_IMAGE_URL = new URL(saticFiles + "/" + BOOTOM_IMAGE);
                NOME = "Nome";
                MORADA = "Morada - Rua";
                CODIGO_POSTAL = "Codigo-Postal";
                PAIS = "Portugal";
                HEADER = "WebContent/media/common/images/header_test_lexus.png";
                CONTACT_REASON = "revis�o e inspe��o";
                CONTACT_REASON_ID = 1;
                ACCESSORY_1 = "F�MEAS DE SEGURAN�A";
                ACCESSORY_1_DESC = "Dite porisqu atianim illanducim repedit atiatia doluptu ribusaperior saeratur? Quidendam, earis con explam repelendit, utatist, te alitatqui cone plaut latur aditecus reped experat ionsequi accaborendi re none quam ime vollanis experspient eat et ex et et essimi, ommodi cum dolorerion et molorest odicae intinctat doluptaecum.";
                ACCESSORY_1_IMG = "WebContent/media/common/images/acessorio_1.jpg";
                ACCESSORY_2 = "FILTRO HABIT�CULO COM CARV�O ATIVO";
                ACCESSORY_2_DESC = "Dite porisqu atianim illanducim repedit atiatia doluptu ribusaperior saeratur? Quidendam, earis con explam repelendit, utatist, te alitatqui cone plaut latur aditecus reped experat ionsequi accaborendi re none quam ime vollanis experspient eat et ex et et essimi, ommodi cum dolorerion et molorest odicae intinctat doluptaecum.";
                ACCESSORY_2_IMG = "WebContent/media/common/images/acessorio_2.jpg";
                SERVICE_1 = "VIATURA DE CORTESIA GRATUITA";
                SERVICE_1_DESC = "Sempre que efetuar uma marca��o de manuten��o para o seu Lexus, ter� ao seu dispor um ve�culo de substitui��o. Tudo para garantir que o seu dia a dia n�o � afetado devido � imobiliza��o do seu Lexus.";
                SERVICE_1_IMG = "WebContent/media/common/images/service_1.png";
                SERVICE_2 = "LEVANTAMENTO E ENTREGA";
                SERVICE_2_DESC = "Para garantir a sua mobilidade e bem-estar, disponibilizamos o servi�o de levantamento e entrega do seu Lexus. Vamos at� � sua casa ou ao seu trabalho para recolher e entregar a viatura, sempre que tenha de efetuar uma revis�o.";
                SERVICE_2_IMG = "WebContent/media/common/images/service_2.png";
                SERVICE_3 = "LEXUS FAST REPAIR";
                SERVICE_3_DESC = "Desenvolvemos um conjunto desolu��es r�pidas para a repara��o depequenas �reas do seu autom�vel.Este servi�o est� dispon�vel nasOficinas Lexus, n�o necessita demarca��o pr�via e permite-lhereparar pequenos danos no seuLexus, em menos de 1 hora.";
                SERVICE_3_IMG = "WebContent/media/common/images/service_3.png";
                NEXT_ITV_DATE = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
                HIGHLIGHT_1 = "WebContent/media/common/images/destaque.jpg";
                MODEL = "COROLA";
                PLATE = "00-OO-00";
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

                        LOGO_URL = new URL(saticFiles + "/" + LOGO_LEXUS);
                        BOOTOM_IMAGE_URL = new URL(saticFiles + "/" + BOOTOM_IMAGE);

                        if (simulation.getPaData() != null) {
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
                            SERVICE_1_IMG = simulation.getService1ImgPostal() != null
                                    ? postalServiceUrl + "/" + simulation.getService1ImgPostal() : "";
                            SERVICE_2 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService2Name()), "");
                            SERVICE_2_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService2Desc()), "");
                            SERVICE_2_IMG = simulation.getService2ImgPostal() != null
                                    ? postalServiceUrl + "/" + simulation.getService2ImgPostal() : "";
                            SERVICE_3 = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService3Name()), "");
                            SERVICE_3_DESC = StringTasks.cleanString(StringTasks.CorrectUTF8(simulation.getService3Desc()), "");
                            SERVICE_3_IMG = simulation.getService3ImgPostal() != null
                                    ? postalServiceUrl + "/" + simulation.getService3ImgPostal() : "";

                            MODEL = "--";
                            if(simulation.getCarInfo() != null && simulation.getCarInfo().getCar()!=null && simulation.getCarInfo().getCar().getModel()!=null && simulation.getCarInfo().getCar().getModel().getDesig()!= null && !simulation.getCarInfo().getCar().getModel().getDesig().equals("")){
                                MODEL 		       = simulation.getCarInfo().getCar().getModel().getDesig();
                            }else if( simulation.getPaData()!=null && simulation.getPaData().getModel()!=null && !simulation.getPaData().getModel().equals("")){
                                MODEL = simulation.getPaData().getModel();
                            }
                            PLATE = CarTasks.formatPlate(simulation.getPaData().getLicensePlate());
                            NEXT_ITV_DATE = null;
                            if(simulation.getCarInfo()!=null && simulation.getCarInfo().getDtItv() != null){
                                NEXT_ITV_DATE = simulation.getCarInfo().getDtItv();
                            }else if(simulation.getMrs() != null && simulation.getMrs().getDtItv() != null){
                                NEXT_ITV_DATE = simulation.getMrs().getDtItv();
                            }

                            HIGHLIGHT_1 = simulation.getHighlight1ImgPostal() != null
                                    ? postalHighlightUrl + "/" + simulation.getHighlight1ImgPostal() : "";
                            write(pdfw, document, paData);
                        }
                        document.close();
                        pdfw.close();
                        File oFile = new File(tempDir + File.separator + FILE_NAME+"_"+year+"_"+month+"_"+simulation.getPaData().getLicensePlate()+".pdf");
                        if(isToSendGesDoc){
                            String documentId = programaAvisosUtil.uploadFileGesDoc("L", simulation.getPaData().getLicensePlate(), simulation.getPaData().getVin(), oFile, gesDocLocation,"TPA/LPA");
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
            log.error(e);
            throw new ProgramaAvisosException("Error write pdf", e);
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
                addTopPage(pdfw);
                addAddress(pdfw);
                addHeader(pdfw);
                addContactReason(pdfw);
                addBottomPage(pdfw);
                miras(pdfw);
                addNewPage(document);
                addAccessories(pdfw);
                addServices(pdfw);
                addHighlights(pdfw);
                addBottomPage(pdfw);
                miras(pdfw);
            }else if(paData.getOwner()!=null && paData.getOwner().equalsIgnoreCase(OWNER_DEALER)) {
                addTopPage(pdfw);
                addAddress(pdfw);
                addHeader(pdfw);
                addContactReason(pdfw);
                addBottomPage(pdfw);
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

    private static void addBottomPage(PdfWriter pdfw) throws MalformedURLException, IOException, DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();
        canvas.setColorFill(BaseColor.BLACK);
        canvas.setFontAndSize(bfNobel_Book, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tem alguma quest�o ou necessita de esclarecimentos adicionais.", 140, 40, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Lexus Concierge", 360, 40, 0);
        canvas.setFontAndSize(bfNobel_Bold, 11);
        canvas.showTextAligned(Element.ALIGN_LEFT, "808 250 220", 425, 40, 0);
        canvas.setFontAndSize(bfNobel_Bold, 12);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Lexus.pt", 530, 40, 0);
        canvas.endText();
        canvas.stroke();

        Image img = Image.getInstance(BOOTOM_IMAGE_URL);
        img.scaleAbsoluteWidth(110);
        img.scaleAbsoluteHeight(80);
        img.setAbsolutePosition(20, 20);
        canvas.addImage(img);
    }

    private static void addHighlights(PdfWriter pdfw) throws DocumentException, MalformedURLException, IOException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.rectangle(15, 100, 605, 150);
        canvas.setColorFill(BaseColor.LIGHT_GRAY);
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
        canvas.setFontAndSize(bfNobel_Book, 30);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.showTextAligned(Element.ALIGN_LEFT, "SERVI�OS", 70, 490, 0);
        canvas.endText();
        canvas.stroke();

        canvas.rectangle(15, 250, 605, 200);
        canvas.setColorFill(TME_GREY_6);
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

        Font redFont = new Font(bfNobel_Bold, 10, Font.NORMAL, BaseColor.BLACK);

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

        Font ttb9 = new Font(bfNobel_Book, 9, Font.NORMAL, BaseColor.BLACK);

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



    private static void addNewPage(Document document) {
        document.newPage();
    }

    private static void addContactReason(PdfWriter pdfw) throws DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();


        Paragraph paragraph0 =  new Paragraph();
        paragraph0.add(new Chunk("Caro/a Cliente "+NOME+", ", new Font(bfNobel_Book, 10,0,PRETO)));

        Paragraph paragraph1 =  new Paragraph();
        Paragraph paragraph2 =  new Paragraph();
        if(ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID){
            paragraph1.add(new Chunk("Um dos pilares da Lexus � antecipar as necessidades dos seus clientes. Por isso, agora que chegou a altura de fazer a revis�o e inspe��o do seu autom�vel, convidamo-lo a visitar o seu centro Lexus.", new Font(bfNobel_Book, 10)));
            paragraph2.add(new Chunk("Marque j� a revis�o e pr�-inspe��o do seu Lexus atrav�s do n�mero 808 250 220, em Lexus.pt ou atrav�s da app Lexus Link, ", new Font(bfNobel_Bold, 10)));
        }else if(ID_CONTACT_TYPE_MAN == CONTACT_REASON_ID){
            paragraph1.add(new Chunk("Um dos pilares da Lexus � antecipar as necessidades dos seus clientes. Por isso, agora que chegou a altura de fazer a revis�o do seu autom�vel, convidamo-lo a visitar o seu centro Lexus.", new Font(bfNobel_Book, 10)));
            paragraph2.add(new Chunk("Marque j� a revis�o do seu Lexus atrav�s do n�mero 808 250 220, em Lexus.pt ou atrav�s da app Lexus Link, ", new Font(bfNobel_Bold, 10)));
        }else if(ID_CONTACT_TYPE_ITV == CONTACT_REASON_ID){
            paragraph1.add(new Chunk("Um dos pilares da Lexus � antecipar as necessidades dos seus clientes. Por isso, agora que chegou a altura de fazer a inspe��o do seu autom�vel, convidamo-lo a visitar o seu centro Lexus.", new Font(bfNobel_Book, 10)));
            paragraph2.add(new Chunk("Marque j� a pr�-inspe��o do seu Lexus atrav�s do n�mero 808 250 220, em Lexus.pt ou atrav�s da app Lexus Link, ", new Font(bfNobel_Bold, 10)));
        }

        paragraph2.add(new Chunk("sabendo que estar� em boas m�os. Desfrute de um atendimento personalizado e de um servi�o de confian�a, livre de preocupa��es.", new Font(bfNobel_Book, 10)));


        Paragraph paragraph3 =  new Paragraph();
        paragraph3.add(new Chunk("Esperamos por si.", new Font(bfNobel_Bold, 10)));


        PdfPCell cell;
        PdfPTable table = new PdfPTable(1);

        table.setTotalWidth(500);
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

        cell = new PdfPCell(paragraph2);
        cell.setLeading(1.5f, 1.5f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        table.addCell(cell);

        cell = new PdfPCell(paragraph3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(15);
        table.addCell(cell);

        table.writeSelectedRows(0,-1, 70, 260, canvas);

    }

    private static void addHeader(PdfWriter pdfw) throws MalformedURLException, IOException, DocumentException {
        PdfContentByte canvas = pdfw.getDirectContent();
        if (!HEADER.equals("")) {

            Image img;
            if (TEST_PAGE) {
                img = Image.getInstance(HEADER);
            } else {
                img = Image.getInstance(new URL(HEADER));
            }
            img.scaleAbsoluteWidth(550);
            img.scaleAbsoluteHeight(230);
            img.setAbsolutePosition(18, 290);
            canvas.addImage(img);
        }

        canvas.rectangle(70, 610, 300, 1);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.fill();

        canvas.rectangle(70, 560, 300, 1);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.fill();

        canvas.beginText();
        canvas.setFontAndSize(bfNobelWGL_Book, 17);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.showTextAligned(Element.ALIGN_LEFT, "A DATA DA ", 70, 590, 0);
        canvas.setFontAndSize(bfNobelWGL_Bold, 17);
        canvas.showTextAligned(Element.ALIGN_LEFT, CONTACT_REASON.toUpperCase(), 163, 590, 0);
        canvas.setFontAndSize(bfNobelWGL_Book, 17);
        canvas.showTextAligned(Element.ALIGN_LEFT, "DO SEU LEXUS EST� A CHEGAR.", 70, 570, 0);
        canvas.endText();
        canvas.stroke();

        if(ID_CONTACT_TYPE_ITV == CONTACT_REASON_ID || ID_CONTACT_TYPE_MAN_ITV == CONTACT_REASON_ID){
            canvas.rectangle(18, 278, 280, 25);
            canvas.setColorFill(BaseColor.BLACK);
            canvas.fill();

            canvas.rectangle(18, 290, 15, 1);
            canvas.setColorFill(BaseColor.WHITE);
            canvas.fill();

            canvas.beginText();
            canvas.setFontAndSize(bfNobelWGL_Book, 10);
            canvas.setColorFill(BaseColor.WHITE);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Data pr�xima inspe��o:", 40, 287, 0);
            canvas.setFontAndSize(bfNobelWGL_Bold, 10);

            if(NEXT_ITV_DATE!=null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String date = formatter.format(NEXT_ITV_DATE);
                canvas.showTextAligned(Element.ALIGN_LEFT, date,132, 287, 0);
            }else{
                canvas.showTextAligned(Element.ALIGN_LEFT, "--",132, 287, 0);
            }

            canvas.endText();
            canvas.stroke();
        }

        canvas.rectangle(260, 278, 308, 25);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.fill();

        if(ID_CONTACT_TYPE_MAN== CONTACT_REASON_ID ){
            canvas.rectangle(260, 290, 15, 1);
            canvas.setColorFill(BaseColor.WHITE);
            canvas.fill();
        }

        canvas.beginText();

        canvas.setFontAndSize(bfNobelWGL_Book, 10);
        canvas.setColorFill(BaseColor.WHITE);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Modelo: ", 280, 287, 0);

        canvas.setFontAndSize(bfNobelWGL_Bold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, MODEL, 315, 287, 0);

        canvas.setFontAndSize(bfNobelWGL_Book, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Matr�cula: ", 468, 287, 0);

        canvas.setFontAndSize(bfNobelWGL_Bold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, PLATE, 510, 287, 0);

        canvas.endText();
        canvas.stroke();


    }


    private static void addAddress(PdfWriter pdfw) throws DocumentException {

        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.setColorFill(BaseColor.BLACK);
        Font addressFont = new Font(bfNobel_Book, 10);

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
        Image img = Image.getInstance(LOGO_URL);
        img.scaleAbsoluteWidth(180);
        img.scaleAbsoluteHeight(50);
        img.setAbsolutePosition(50, 795);
        canvas.addImage(img);
    }

    private static void addAccessories(PdfWriter pdfw) throws DocumentException, IOException {
        PdfContentByte canvas = pdfw.getDirectContent();

        canvas.beginText();

        canvas.setFontAndSize(bfNobel_Book, 30);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.showTextAligned(Element.ALIGN_LEFT, "ACESS�RIOS", 70, 800, 0);

        canvas.setFontAndSize(bfNobel_Bold, 10);
        canvas.setColorFill(BaseColor.BLACK);
        canvas.showTextAligned(Element.ALIGN_LEFT, ACCESSORY_1.toUpperCase(), 70, 750, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ACCESSORY_2.toUpperCase(), 70, 630, 0);

        canvas.endText();
        canvas.stroke();

        Font ttb9 = new Font(bfNobel_Book, 9, Font.NORMAL, BaseColor.BLACK);

        canvas.setColorFill(BaseColor.BLACK);
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

    static{
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_MAN, "Revisão");
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_ITV, "Inspeção");
        CONTACT_REASON_MAP.put(ID_CONTACT_TYPE_MAN_ITV, "Revisão e Inspeção");

    }
}
