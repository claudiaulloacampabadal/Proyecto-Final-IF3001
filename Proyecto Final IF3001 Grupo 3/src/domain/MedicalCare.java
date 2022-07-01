/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDateTime;
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
    private LocalDateTime dateTime;
    private int sicknessID;
    private String annotations;
    private static int autoID;

    public MedicalCare( int doctorID, int patientID, LocalDateTime dateTime, int sicknessID, String annotations) {
        this.identity = ++autoID;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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

    @Override
    public String toString() {
        return  doctorID + ";" + patientID + ";" + util.Utility.formatDateTime(dateTime) + ";" + sicknessID + ";" + annotations ;
    }

    
    
    
    
}//END CLASS
