/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Date;

/**
 *
 * @author Claudia
 *
 */
public class MedicalCare {

    private int identity;
    private int doctorID;
    private int patientID;
    private Date dateTime;
    private int sicknessID;
    private String annotations;

    public MedicalCare(int identity, int doctorID, int patientID, Date dateTime, int sicknessID, String annotations) {
        this.identity = identity;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.dateTime = dateTime;
        this.sicknessID = sicknessID;
        this.annotations = annotations;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getSicknessID() {
        return sicknessID;
    }

    public void setSicknessID(int sicknessID) {
        this.sicknessID = sicknessID;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    
    
    
    
}//END CLASS
