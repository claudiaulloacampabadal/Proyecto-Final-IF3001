/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Usuario
 */
public class Security {
    
    //Atributos
    private String user;
    private String password;
    private String type;
    
    public Security() {
    }
     
    //Constructor
    public Security(String user, String password, String type) {
        this.user = user;
        this.password = password;
        this.type = type;
    }

    //Getters & Setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  user + ";" + password + ";" + type;
    }
    
 
    
}//END CLASS
