/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Maria Celeste
 */
public class Schedule {
    
    private int monday;
    private LocalTime monStart;
    private LocalTime monEnd;   
    private int tuesday;
    private LocalTime tueStart;
    private LocalTime tueEnd;  
    private int wednesday;
    private LocalTime wedStart;
    private LocalTime wedEnd; 
    private int thursday;
    private LocalTime thuStart;
    private LocalTime thuEnd;  
    private int friday;
    private LocalTime friStart;
    private LocalTime friEnd;  
    private int saturday;
    private LocalTime satStart;
    private LocalTime satEnd;
    private int sunday;
    private LocalTime sunStart;
    private LocalTime sunEnd;

    public Schedule(int monday, LocalTime monStart, LocalTime monEnd, int tuesday, LocalTime tueStart, LocalTime tueEnd, int wednesday, LocalTime wedStart, LocalTime wedEnd, int thursday, LocalTime thuStart, LocalTime thuEnd, int friday, LocalTime friStart, LocalTime friEnd, int saturday, LocalTime satStart, LocalTime satEnd, int sunday, LocalTime sunStart, LocalTime sunEnd) {
        this.monday = monday;
        this.monStart = monStart;
        this.monEnd = monEnd;
        this.tuesday = tuesday;
        this.tueStart = tueStart;
        this.tueEnd = tueEnd;
        this.wednesday = wednesday;
        this.wedStart = wedStart;
        this.wedEnd = wedEnd;
        this.thursday = thursday;
        this.thuStart = thuStart;
        this.thuEnd = thuEnd;
        this.friday = friday;
        this.friStart = friStart;
        this.friEnd = friEnd;
        this.saturday = saturday;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.sunday = sunday;
        this.sunStart = sunStart;
        this.sunEnd = sunEnd;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public LocalTime getMonStart() {
        return monStart;
    }

    public void setMonStart(LocalTime monStart) {
        this.monStart = monStart;
    }

    public LocalTime getMonEnd() {
        return monEnd;
    }

    public void setMonEnd(LocalTime monEnd) {
        this.monEnd = monEnd;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public LocalTime getTueStart() {
        return tueStart;
    }

    public void setTueStart(LocalTime tueStart) {
        this.tueStart = tueStart;
    }

    public LocalTime getTueEnd() {
        return tueEnd;
    }

    public void setTueEnd(LocalTime tueEnd) {
        this.tueEnd = tueEnd;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public LocalTime getWedStart() {
        return wedStart;
    }

    public void setWedStart(LocalTime wedStart) {
        this.wedStart = wedStart;
    }

    public LocalTime getWedEnd() {
        return wedEnd;
    }

    public void setWedEnd(LocalTime wedEnd) {
        this.wedEnd = wedEnd;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public LocalTime getThuStart() {
        return thuStart;
    }

    public void setThuStart(LocalTime thuStart) {
        this.thuStart = thuStart;
    }

    public LocalTime getThuEnd() {
        return thuEnd;
    }

    public void setThuEnd(LocalTime thuEnd) {
        this.thuEnd = thuEnd;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public LocalTime getFriStart() {
        return friStart;
    }

    public void setFriStart(LocalTime friStart) {
        this.friStart = friStart;
    }

    public LocalTime getFriEnd() {
        return friEnd;
    }

    public void setFriEnd(LocalTime friEnd) {
        this.friEnd = friEnd;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public LocalTime getSatStart() {
        return satStart;
    }

    public void setSatStart(LocalTime satStart) {
        this.satStart = satStart;
    }

    public LocalTime getSatEnd() {
        return satEnd;
    }

    public void setSatEnd(LocalTime satEnd) {
        this.satEnd = satEnd;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public LocalTime getSunStart() {
        return sunStart;
    }

    public void setSunStart(LocalTime sunStart) {
        this.sunStart = sunStart;
    }

    public LocalTime getSunEnd() {
        return sunEnd;
    }

    public void setSunEnd(LocalTime sunEnd) {
        this.sunEnd = sunEnd;
    }

   

    @Override
    public String toString() {
         DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        return  monday + "-" + format.format(monStart) + "-" + format.format(monEnd)+ "-" + tuesday + "-" + tueStart + "-" + tueEnd + "-" + wednesday + "-" + 
         wedStart + "-" + wedEnd + "-" + thursday + "-" + format.format(thuStart)+ "-" + format.format(thuEnd)+ "-" + friday + "-" + friStart + "-" + friEnd +"-"+saturday+"-"+format.format(satStart)+
         "-"+format.format(satEnd)+"-"+sunday+"-"+format.format(sunStart)+"-"+format.format(sunEnd);
    }
    
    
    
    
    
   
    
    
    
    
    
    
}
