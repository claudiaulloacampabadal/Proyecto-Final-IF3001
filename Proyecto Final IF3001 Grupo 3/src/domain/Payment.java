/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Date;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*; 


/**
 *
 * @author Fiorella
 */
public class Payment {
    
  
    private int patientID;
    private String paymentMode;
    private double serviceCharge;
    private Date billingDate;
    private double totalCharge;//(serviceCharge+30%)

    public Payment(int patientID, String paymentMode, double serviceCharge, Date billingDate, double totalCharge) {        
        this.patientID = patientID;
        this.paymentMode = paymentMode;
        this.serviceCharge = serviceCharge;
        this.billingDate = billingDate;
        this.totalCharge = totalCharge;
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

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date bilingualDate) {
        this.billingDate = bilingualDate;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
    
    //pdf
    public static void generateReport(String nameFile, String pdfTitle, String objContent) throws FileNotFoundException, DocumentException, BadElementException, IOException {

        FileOutputStream archive = new FileOutputStream("C:\\Users\\Saúl Machado\\Desktop\\Algoritmos y estructuras de datos\\PruebasCorreo" + "\\" + nameFile + ".pdf");
        Document doc = new Document();

        PdfWriter.getInstance(doc, archive);
        doc.open();

        Paragraph title = new Paragraph(pdfTitle + "\n\n", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));

        title.setAlignment(Paragraph.ALIGN_CENTER);

////        Image img = Image.getInstance("C:\\Users");
////        img.setAlignment(Image.ALIGN_CENTER);
//        img.setBorder(Image.BOX);
//        img.setBorderWidth(0);
//        img.setBorderColor(BaseColor.WHITE);
//        img.scaleToFit(1100, 100); // tamaño 
//        doc.add(img);
        doc.add(title);

        Paragraph dateReport = new Paragraph(objContent, FontFactory.getFont("arial", 18, Font.NORMAL, BaseColor.BLACK));
        dateReport.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(dateReport);

        doc.close();

    }
    
}
