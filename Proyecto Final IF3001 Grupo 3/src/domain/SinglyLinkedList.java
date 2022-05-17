/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Fiorella
 */
public class SinglyLinkedList {

    
    public String encrypt(String pasword){
        try{
            java.security.MessageDigest md= java.security.MessageDigest.getInstance("MD5");
            byte []array=md.digest(pasword.getBytes());
            StringBuffer sb= new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        }catch(java.security.NoSuchAlgorithmException e){
            
        }
        return null;
    }
}
