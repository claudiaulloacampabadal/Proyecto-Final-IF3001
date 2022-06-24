/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Date;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.*; 

/**
 *
 * @author Fiorella
 */
public class Payment {
    
    
    private int identity;
    private int patientID;
    private String paymentMode;
    private double serviceCharge;
    private Date bilingualDate;
    private double totalCharge;//(serviceCharge+30%)

    public Payment(int identity, int patientID, String paymentMode, double serviceCharge, Date bilingualDate, double totalCharge) {
        this.identity = identity;
        this.patientID = patientID;
        this.paymentMode = paymentMode;
        this.serviceCharge = serviceCharge;
        this.bilingualDate = bilingualDate;
        this.totalCharge = totalCharge;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getServiceCharge() {
        return serviceCharge + (serviceCharge*0.3);
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Date getBilingualDate() {
        return bilingualDate;
    }

    public void setBilingualDate(Date bilingualDate) {
        this.bilingualDate = bilingualDate;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
    
    //pdf
    //fuentes
     private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);         
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    //se inserta imagenes     
    private static final String iTextExampleImage = "/home/xules/codigoxules/iText-Example-image.png";
    
    //metodo para crear el pdf    
     public void createPDF(File pdfNewFile) {        
        try {
            Document document = new Document();
            try { 
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile)); 
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
           
            // Añadimos los metadatos del PDF
            document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
            document.addSubject("Using iText (usando iText)");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Código Xules");
            document.addCreator("Código Xules");             
           
            // Primera página 
            Chunk chunk = new Chunk("This is the title", chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            // Let's create de first Chapter (Creemos el primer capítulo)
//            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
//            chapter.setNumberDepth(0);
//            chapter.add(new Paragraph("This is the paragraph", paragraphFont));
            // We add an image (Añadimos una imagen)
//            Image image;
//            try {
//                image = Image.getInstance(iTextExampleImage);  
//                image.setAbsolutePosition(2, 150);
//                chapter.add(image);
//            } catch (BadElementException ex) {
//                System.out.println("Image BadElementException" +  ex);
//            } catch (IOException ex) {
//                System.out.println("Image IOException " +  ex);
//            }
//            document.add(chapter);
             
            // Second page - some elements
            // Segunda página - Algunos elementos
//            Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Some elements (Añadimos varios elementos)")), 1);
//            Paragraph paragraphS = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
             
            // Underline a paragraph by iText (subrayando un párrafo por iText)
//            Paragraph paragraphE = new Paragraph("This line will be underlined with a dotted line (Está línea será subrayada con una línea de puntos).");
//            DottedLineSeparator dottedline = new DottedLineSeparator();
//            dottedline.setOffset(-2);
//            dottedline.setGap(2f);
//            paragraphE.add(dottedline);
//            chapSecond.addSection(paragraphE);
//             
//            Section paragraphMoreS = chapSecond.addSection(paragraphS);
            // List by iText (listas por iText)
//            String text = "test 1 2 3 ";
//            for (int i = 0; i < 5; i++) {
//                text = text + text;
//            }
//            List list = new List(List.UNORDERED);
//            ListItem item = new ListItem(text);
//            item.setAlignment(Element.ALIGN_JUSTIFIED);
//            list.add(item);
//            text = "a b c align ";
//            for (int i = 0; i < 5; i++) {
//                text = text + text;
//            }
//            item = new ListItem(text);
//            item.setAlignment(Element.ALIGN_JUSTIFIED);
//            list.add(item);
//            text = "supercalifragilisticexpialidocious ";
//            for (int i = 0; i < 3; i++) {
//                text = text + text;
//            }
//            item = new ListItem(text);
//            item.setAlignment(Element.ALIGN_JUSTIFIED);
//            list.add(item);
//            paragraphMoreS.add(list);
//            document.add(chapSecond);
             
            // Utilización de PdfPTable
             
           
            // Usamos varios elementos para añadir título y subtítulo
            Anchor anchor = new Anchor("Table export to PDF (Exportamos la tabla a PDF)", categoryFont);
            anchor.setName("Table export to PDF (Exportamos la tabla a PDF)");            
            Chapter chapTitle = new Chapter(new Paragraph(anchor), 1);
            Paragraph paragraph = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
            Section paragraphMore = chapTitle.addSection(paragraph);
            paragraphMore.add(new Paragraph("This is a simple example (Este es un ejemplo sencillo)"));
            Integer numColumns = 6;
            Integer numRows = 120;
            //Creamos la tabla
            PdfPTable table = new PdfPTable(numColumns); 
          
            // Ahora llenamos la tabla del PDF
            PdfPCell columnHeader;
            // rellenamos las filas de la tabla               
            for (int column = 0; column < numColumns; column++) {
                columnHeader = new PdfPCell(new Phrase("COL " + column));
                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(columnHeader);
            }
            table.setHeaderRows(1);
            //rellenamos las filas de la tabla                
            for (int row = 0; row < numRows; row++) {
                for (int column = 0; column < numColumns; column++) {
                    table.addCell("Row " + row + " - Col" + column);
                }
            }
            //Añadimos la tabla
            paragraphMore.add(table);
            //Añadimos el elemento con la tabla
            document.add(chapTitle);
            document.close();
            System.out.println("¡Se ha generado tu hoja PDF!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: " + documentException);
        }
    }
    
}
