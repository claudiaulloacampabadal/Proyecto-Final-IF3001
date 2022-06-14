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
public class Patient {
    
    //atributos
    private int id;
    private String lastname;
    private String firstname;
    private Date birthday;
    //private String phoneNumber;
    private String email;
    private String adress;

    
    //constructor
    public Patient(int id, String lastname, String firstname, Date birthday, String email, String adress) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.email = email;
        this.adress = adress;
    }

    //getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    

    @Override
    public String toString() {
        return id + ";" + lastname + ";" + firstname + ";" + birthday + ";" +email + ";" +adress;
   }
    
    
    
    
    
}//END CLASS
