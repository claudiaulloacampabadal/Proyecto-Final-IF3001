/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.Archives;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Maria Celeste
 */
public class ArchiveTXT {
     //Metodo general que nos habilita el printStream para hacer escritura de archivo
    public PrintStream getPrintStream(boolean saveData){
        
         File numbersData = new File("passwords.txt");
         PrintStream ps = null;
        
        
        try{

            FileOutputStream fos = new FileOutputStream(numbersData,saveData);
            ps = new PrintStream(fos);
        
        }catch(FileNotFoundException foe){
            
            JOptionPane.showConfirmDialog(null, "Problem with the archive");
            
        }
       return ps; 
    }

    //Metodo que me permita estandarizar el metodo de lectura de el archivo
    public BufferedReader getBufferedReader(){
        
        File numberData = new File("passwords.txt");
        BufferedReader br = null;
        
        try{
            
            FileInputStream fis = new FileInputStream(numberData);
            InputStreamReader isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            
        }catch(FileNotFoundException fnfe){
            
            JOptionPane.showMessageDialog(null, "Problemas con el archivo");
        
        }
        return br;
    }
    
    
    
}
