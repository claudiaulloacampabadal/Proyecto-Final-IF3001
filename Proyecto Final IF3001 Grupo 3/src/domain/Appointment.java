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
public class Appointment {
    
    
    private int identity;
    private int patientID;
    private int doctorID;
    private Date dateTime;
    private String remarks;

    //constructor
    public Appointment(int identity, int patientID, int doctorID, Date dateTime, String remarks) {
        this.identity = identity;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.remarks = remarks;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
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
        return identity + ";" + patientID + ";" + doctorID + ";" + dateTime + ";" + remarks;
    }
    
    
    
    
}
