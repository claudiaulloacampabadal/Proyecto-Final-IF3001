/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDateTime;

/**
 *
 * @author Maria Celeste
 */
public class Report {
    
    private LocalDateTime date;
    private String user;
    private String description;

    public Report(LocalDateTime date, String user, String description) {
        this.date = date;
        this.user = user;
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  util.Utility.formatDateTime(date)+ ";" + user + ";" + description ;
    }
    
     
}
