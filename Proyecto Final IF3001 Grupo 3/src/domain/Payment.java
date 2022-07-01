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
    
  
    private int id;
    private String paymentMode;
    private double serviceCharge;
    private Date billingDate;
    private double totalCharge;//(serviceCharge+30%)

    public Payment(int id, String paymentMode, double serviceCharge, Date billingDate, double totalCharge) {        
        this.id = id;
        this.paymentMode = paymentMode;
        this.serviceCharge = serviceCharge;
        this.billingDate = billingDate;
        this.totalCharge = totalCharge;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int patientID) {
        this.id = patientID;
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
    
  
    
}
