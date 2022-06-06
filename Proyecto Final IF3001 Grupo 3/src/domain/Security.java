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

    public Security() {
    }
     
    //Constructor
    public Security(String user, String password) {
        this.user = user;
        this.password = password;
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
    
    
    
}//END CLASS
