/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDateTime;

/**
 *
 * @author Claudia
 */
public class Appointment {
    
    
    private int identity;
    private int patientID;
    private int doctorID;
    private LocalDateTime dateTime;
    private String remarks;
    private static int autoID;

    //constructor
    public Appointment( int patientID, int doctorID, LocalDateTime dateTime, String remarks) {
        this.identity = ++autoID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.remarks = remarks;
    }

    public static int getAutoID() {
        return autoID;
    }

    public static void setAutoID(int autoID) {
        Appointment.autoID = autoID;
    }
    
    
    
    //getters and setters
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

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return patientID + ";" + doctorID + ";" + util.Utility.formatDateTime(dateTime)+ ";" + remarks;
    }
    
    
    
    
}
