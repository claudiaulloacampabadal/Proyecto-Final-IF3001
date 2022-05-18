/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Claudia
 */
public class Sickness {
    
    //atributos
    private int identity;
    private String description;

    
    //constructor
    public Sickness(int identity, String description) {
        this.identity = identity;
        this.description = description;
    }

    //getters and setters
    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
