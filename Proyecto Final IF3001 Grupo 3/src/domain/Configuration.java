/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Maria Celeste
 */
public class Configuration {
    
    private String clinicName;
    private String imagesPath;
    private Schedule schedule;
    private String clinicTel;
    private String clinicEmail;
    private String clinicPassword;
    private String path;

    public Configuration(String clinicName, String imagesPath, Schedule schedule, String clinicTel, String clinicEmail,
          String clinicPassword, String path) {
        this.clinicName = clinicName;
        this.imagesPath = imagesPath;
        this.schedule = schedule;
        this.clinicTel = clinicTel;
        this.clinicEmail = clinicEmail;
        this.clinicPassword = clinicPassword;
        this.path = path;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getClinicTel() {
        return clinicTel;
    }

    public void setClinicTel(String clinicTel) {
        this.clinicTel = clinicTel;
    }

    public String getClinicEmail() {
        return clinicEmail;
    }

    public void setClinicEmail(String clinicEmail) {
        this.clinicEmail = clinicEmail;
    }

    public String getClinicPassword() {
        return clinicPassword;
    }

    public void setClinicPassword(String clinicPassword) {
        this.clinicPassword = clinicPassword;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    

    @Override
    public String toString() {
        return clinicName + ";" + imagesPath + ";" +schedule.toString()+";" + clinicTel + ";" + clinicEmail + ";" + clinicPassword + ";" + path;
    }
    
    
    
    
    
    
    
    
}
