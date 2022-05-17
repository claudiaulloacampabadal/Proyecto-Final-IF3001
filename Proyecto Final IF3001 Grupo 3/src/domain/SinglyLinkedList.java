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

    //metodo de encriptacion
    public String encrypt(String pasword){
        try{
            java.security.MessageDigest md= java.security.MessageDigest.getInstance("MD5");
            //separamos la contraseña en un arreglo
            byte []array=md.digest(pasword.getBytes());
            //se crea un stringBuffer para guardar el cambio ya encriptado
            StringBuffer sb= new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                //append concatena a la cadena original
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();            
            //posible error de que el algoritmo no esté en el entorno
        }catch(java.security.NoSuchAlgorithmException e){            
        }
        return null;
    }
}
