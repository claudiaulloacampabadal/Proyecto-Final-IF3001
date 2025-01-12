/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import domain.Payment;
import domain.Security;
import domain.Sickness;
import domain.TDA.BTree;
import domain.TDA.CircularDoublyLinkedList;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.SinglyLinkedList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Usuario
 */
public class Utility {
    private static SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
    private static SinglyLinkedList singlyLinkedListPassWord = new SinglyLinkedList();
    private static DoublyLinkedList doublyLinkedList = new DoublyLinkedList();
    private static DoublyLinkedList doublyLinkedListAppointment = new DoublyLinkedList();
    private static CircularLinkedList circularLinkedList = new CircularLinkedList();
    private static CircularDoublyLinkedList circularDoublyLinkedList = new CircularDoublyLinkedList();
    private static HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
    private static BTree btree = new BTree();
    private static BorderPane bpIllness = new BorderPane();
    private static BorderPane bpAppointment = new BorderPane();
    private static BorderPane bpDoctor = new BorderPane();
    private static BorderPane bpPatient = new BorderPane();
    private static BorderPane bpPayment = new BorderPane();
    private static int user = 0;
    private static String patientId ="";
    
    public static BorderPane getBorderPanePatient() {
        return bpPatient;
    }
    public static void setBorderPanePatient(BorderPane bpPatient) {
        Utility.bpPatient =  bpPatient;
    }
    
    public static BorderPane getBorderPanePayment() {
        return bpPayment;
    }
    public static void setBorderPanePayment(BorderPane bpPayment) {
        Utility.bpPayment =  bpPayment;
    }
    
     public static BorderPane getBorderPaneDoctor() {
        return bpDoctor;
    }
    public static void setBorderPaneDoctor(BorderPane bpDoctor) {
        Utility.bpDoctor =  bpDoctor;
    }
    
    public static BorderPane getBorderPaneAppointment() {
        return bpAppointment;
    }
    public static void setBorderPaneAppointment(BorderPane bpAppointment) {
        Utility.bpAppointment =  bpAppointment;
    }  
    public static BorderPane getBorderPaneIllness() {
        return bpIllness;
    }

    public static void setBorderPaneIllness(BorderPane bpIllness) {
        Utility.bpIllness =  bpIllness;
    }

    public static CircularLinkedList getCircularLinkedList() {
        return circularLinkedList;
    }

    public static void setCircularLinkedList(CircularLinkedList circularLinkedList) {
        Utility.circularLinkedList =  circularLinkedList;
    }
    
     public static CircularDoublyLinkedList getCircularDoublyLinkedList() {
        return circularDoublyLinkedList;
    }

    public static void setCircularDoublyLinkedList(CircularDoublyLinkedList circularDoublyLinkedList) {
        Utility.circularDoublyLinkedList =  circularDoublyLinkedList;
    }
   
   
    public static SinglyLinkedList getSinglyLinkedList() {
        return singlyLinkedList;
    }

    public static void setSinglyLinkedList(SinglyLinkedList singlyLinkedList) {
        Utility.singlyLinkedList = singlyLinkedList;
    }
     public static SinglyLinkedList getSinglyLinkedListPassword() {
        return singlyLinkedListPassWord;
    }

    public static void setSinglyLinkedListPassword(SinglyLinkedList singlyLinkedList) {
        Utility.singlyLinkedListPassWord = singlyLinkedList;
    }

    public static DoublyLinkedList getDoublyLinkedList() {
        return doublyLinkedList;
    }

    public static void setDoublyLinkedList(DoublyLinkedList doublyLinkedList) {
        Utility.doublyLinkedList = doublyLinkedList;
    }
    public static DoublyLinkedList getDoublyLinkedListAppointment() {
        return doublyLinkedListAppointment;
    }

    public static void setDoublyLinkedListAppointment(DoublyLinkedList doublyLinkedListAppointment) {
        Utility.doublyLinkedListAppointment = doublyLinkedListAppointment;
    }

    public static HeaderLinkedQueue getHeaderLinkedQueue() {
        return headerLinkedQueue;
    }

    public static void setHeaderLinkedQueue(HeaderLinkedQueue headerLinkedQueue) {
        Utility.headerLinkedQueue = headerLinkedQueue;
    }
    
    public static BTree getBTree() {
        return btree;
    }

    public static void setBTree(BTree btree) {
        Utility.btree = btree;
    }
    
    public static void setUser(int user){
        Utility.user = user;
    }
    
    public static int getUser(){
        return user;
    }
    
    public static void setPatientId(List<String> patientId) {
       String idp = String.valueOf(patientId).replace("[","").replace("]", "");
        Utility.patientId = idp;
    }
    
    public static String getPatientId(){
        return patientId;
    }
        

    public static int random(){
        return 1+(int) Math.floor(Math.random()*99); 
    }
    
    public static int random(int bound){
        //return 1+random.nextInt(bound);
        return 1+(int) Math.floor(Math.random()*bound); 
    }
    
    public static String format(double value){
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }
    
    public static String $format(double value){
        return new DecimalFormat("$###,###,###,###.##")
                .format(value);
    }
     public static String perFormat(double value){
         //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }
     
    public static String format(Date value){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
        return format.format(value);
    }
    
     public static String formatDateTime(LocalDateTime value){
        // DateTimeFormatter format = new DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm"); 
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return format.format(value);
    }
     
     
     public static String passwordGenerator(){
        //Generador del passoword
        String resultado ="";
        String lettersMin ="abcdefghijklmopqrstvwxyz";String lettersMay = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String characters = "+-,/?¡¿!><[](){}.:=%*";String numbers = "0123456789";
 
        resultado = aleatorio(resultado, lettersMay, 2);
        resultado = aleatorio(resultado, lettersMin, 4);
        resultado = aleatorio(resultado, characters, 1);
        resultado = aleatorio(resultado, numbers, 2);
     
       return resultado;
   }
     
     
     //Para generar el paswword de forma aleatoria
    public static String aleatorio(String resultado,String palabras, int numero){
        String[] arrayPalabra = palabras.split("");
        int numeroAleatorio;
        for (int i = 0; i < numero; i++) {
            numeroAleatorio = (int) (Math.random()*(arrayPalabra.length-1)+0);
            resultado += arrayPalabra[numeroAleatorio];
        }
        return resultado;
    }  
     
   
     
    public static void fill(int a[]) {
        Random random = new Random();
        for (int i = 0; i < a.length; i++)
            a[i] = random.nextInt(99)+1;
    }
    
     public static void bubbleSort(int a[]){
    	for(int i=0;i<a.length;i++){
    	     for(int j=i+1;j<a.length;j++){
    		if(a[j]<a[i]){
    		   int aux=a[i];
                    a[i]=a[j];
    		    a[j]=aux;
    		}//if
            }//for j
        }//for i
    }
     public static String show(int[] a) {
        String result="";
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            if(counter++>=50){
                result+="\n";
                counter = 0;
            }
            result+=a[i]+" ";
        }
        return result;
    }
     
    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        if(a instanceof Sickness && b instanceof Sickness) return "Sickness";
        if(a instanceof Security && b instanceof Security) return "Security";
        if(a instanceof Doctor && b instanceof Doctor) return "Doctor";
        if(a instanceof Patient && b instanceof Patient) return "Patient";
        if(a instanceof Appointment && b instanceof Appointment) return "Appointment";
        if(a instanceof Payment && b instanceof Payment) return "Payment";
        return "unknown";
    }

    public static boolean equals(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "Integer":
                Integer a1=(Integer)a; Integer b1=(Integer)b;
                //return x==y;
                return a1.equals(b1);
            case "String":
                String a2=(String)a; String b2=(String)b;
                return a2.equalsIgnoreCase(b2);
            case "Character":
                Character a3=(Character)a; Character b3=(Character)b;
                return a3.compareTo(b3)==0;
             case "Sickness":
                Sickness s1 =(Sickness)a; Sickness s2 =(Sickness)b;
                return s1.getIdentity() == s2.getIdentity() 
                || s1.getDescription().equalsIgnoreCase(s2.getDescription());
            case "Security":
                Security sec1 =(Security)a; Security sec2 =(Security)b;
                return sec1.getUser().equals(sec2.getUser()) &&  sec1.getPassword().equals(sec2.getPassword()) 
                      && sec1.getType().equals(sec2.getType());
            case "Doctor":
                Doctor d1 =(Doctor)a; Doctor d2 =(Doctor)b;
                return d1.getId() == d2.getId();
            case "Patient":
                Patient p1 =(Patient)a; Patient p2 =(Patient)b;
                return p1.getId()== p2.getId() ;
            case "Appointment":
                 Appointment ap1 = (Appointment) a; Appointment ap2 = (Appointment) b;
                 return ap1.getDoctorID() == ap2.getDoctorID() && ap1.getDateTime().getHour() == ap2.getDateTime().getHour() &&
                        ap1.getDateTime().getMonthValue() == ap2.getDateTime().getMonthValue() ;
            case "Payment":
                Payment pay1 =(Payment)a; Payment pay2 =(Payment)b;
                return pay1.getId()== pay2.getId() ;   
        }
        return false;
    }
   
    //less than (menorQ)
    public static boolean lessT(Object a, Object b){
        switch(instanceOf(a, b)){
            case "Integer":
                Integer a1=(Integer) a; Integer b1=(Integer) b;
                return a1<b1;
            case "String":
                String a2=(String) a; String b2=(String) b;
                return a2.compareToIgnoreCase(b2)<0;
            case "Character":
                Character a3=(Character)a; Character b3=(Character)b;
                return a3.compareTo(b3)<0;
            case "Appointment":
                Appointment ap1 = (Appointment) a; Appointment ap2 = (Appointment) b;
                return( ap1.getDateTime().getDayOfMonth() < ap2.getDateTime().getDayOfMonth() &&
                        ap1.getDateTime().getHour() < ap2.getDateTime().getHour()) || 
                        ap1.getDateTime().getMonthValue() < ap2.getDateTime().getMonthValue() ;
            }
        return false; 
    }
    
    //greater than (mayorQ)
    public static boolean greaterT(Object a, Object b){
        switch(instanceOf(a, b)){
            case "Integer":
                Integer a1=(Integer) a; Integer b1=(Integer) b;
                return a1>b1;
            case "String":
                String a2=(String) a; String b2=(String) b;
                return a2.compareToIgnoreCase(b2)>0;
            case "Character":
                Character a3=(Character)a; Character b3=(Character)b;
                return a3.compareTo(b3)>0;
            case "Appointment":
                Appointment ap1 = (Appointment) a; Appointment ap2 = (Appointment) b;
                return (ap1.getDateTime().getDayOfMonth() > ap2.getDateTime().getDayOfMonth() 
                     && ap1.getDateTime().getHour() > ap2.getDateTime().getHour())
                     || ap1.getDateTime().getMonthValue() > ap2.getDateTime().getMonthValue() ;
            
         
        }
        return false;
    }
    
    public static String message(String type, String user, String pass, String date, String doctor){
        switch(type){
            case "Patient":
               return "\nHi, here is your password and user for your account: "+
                      "\n\n  User: "+user+"\n  Password: "+pass+
                    "\n\n Remember to use this password that is both strong and unique to your account."+
                    "\n\n\nDon't reply this message.";
            case "Appointment":
                return "Hi, remember your appointment:"+
                       "\n\n Date: "+date+"\nDoctor: "+doctor
                        +"\n\n\nDon't reply this message.";
                        
              
        }
        return null;
    }
    
    public static boolean emailValidation(String email){
        String regx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regx); 
        
        Matcher matcher = pattern.matcher(email);  
        return matcher.matches();
    }
    
    
    public static int countDigits(int id){
        int digitos = 0;
        while(id != 0){
            
            id = id /10;
            digitos++;
        }
        return digitos;
    }

    
    
}
