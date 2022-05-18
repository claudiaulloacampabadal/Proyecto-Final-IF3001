/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Date;

/**
 *
 * @author Claudia
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
    
    
}//END CLASS
