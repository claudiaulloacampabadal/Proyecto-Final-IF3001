/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.Archives;

import domain.Appointment;
import domain.Configuration;
import domain.Doctor;
import domain.MedicalCare;
import domain.Patient;
import domain.Payment;
import domain.Report;
import domain.Schedule;
import domain.Security;
import domain.Sickness;
import domain.TDA.AVL;
import domain.TDA.BST;
import domain.TDA.BTree;
import domain.TDA.CircularDoublyLinkedList;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.ListException;
import domain.TDA.QueueException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import main.FXMLAddIllnesAndDiseaseController;
import main.FXMLIllnessAndDiseaseController;
import main.FXMLPatientsController;

/**
 *
 * @author Maria Celeste
 */
public class ArchiveTXT {
     //Metodo general que nos habilita el printStream para hacer escritura de archivo
    public PrintStream getPrintStream(boolean saveData , String path){
        
         File numbersData = new File(path+".txt");
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
    public BufferedReader getBufferedReader(String path){
        
        File numberData = new File(path+".txt");
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
    
    public void addArchive(Object o, String path) {
        BST configurations = util.Utility.getBST();
        //Añade lo ques e encesite a los archivos correspoendiente spor medico del path
        //Le dice que lea el archivo
        BufferedReader br = getBufferedReader(path);
        //Le dice que escriba
        PrintStream ps = getPrintStream(true, path);
        try {
            String lineRegister = "";
            while (lineRegister != null) {
                
                lineRegister = br.readLine();
                //Cunado esta nulo escribe
                if (lineRegister == null) {
                    ps.println(o);
                } 
            }
            ps.close();
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void removeArchive(String lineToRemove, String path) {
      try {
 
        File file = new File(path+".txt");
 
        if (!file.isFile()) {
            return;
        }
 
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = getBufferedReader(path);
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
 
        String line = "";
        //Read from the original file and write to the new
        //unless content matches data to be removed.
        while (line != null) {
            //El token es;
            line = br.readLine();
            if(line != null){
                StringTokenizer sT = new StringTokenizer(line,";");
                //Para separar los tokens
                String id = sT.nextToken();
                      //Reviso que id no sea el mismo que el "line to remove"
                if (!id.equalsIgnoreCase(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            }
        pw.close();
        br.close();
         pw.close();
        br.close();
         pw.close();
        br.close();
        //Delete the original file
        if (!file.delete()) {
            System.out.println("Could not delete file");
            return;
        }
 
        //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(new File(path+".txt"))){
            System.out.println("Could not rename file");
 
        }
    } catch (FileNotFoundException ex) {
    } catch (IOException ex) {
    }
        
   }
     //int id, String paymentMode, double serviceCharge, Date billingDate, double totalCharg
      public HeaderLinkedQueue getPayment(String path) throws QueueException {
    
        HeaderLinkedQueue list = util.Utility.getHeaderLinkedQueue();
        BufferedReader br = getBufferedReader(path);
        
        //creamos el archivo de pago
        File file = new File(path+".txt");
        
       try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    int idPatient = 0;
                    String paymentMode="";
                    double serviceCharges=0;
                    double totalCharge=0;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date billingDate = null;
                    
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                id = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Separa el id
                                break;
                            case 2:
                                idPatient = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Separa el id
                                break;
                            case 3:
                                paymentMode = sT.nextToken();
                                controlTokens++;//modo de pago
                                break;
                            case 4:
                                //convertir a date
                                serviceCharges = Double.parseDouble(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                //convertir a date
                                billingDate = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                            case 6:
                                //convertir a date
                                totalCharge = Double.parseDouble(sT.nextToken());
                                controlTokens++;
                                break;
                        }
                    }
                    
                  Payment pay = new Payment(id,idPatient,paymentMode, serviceCharges, billingDate, totalCharge);
                    //Evita repeticion de pagos
                    if(lineRegister != null){
                        list.enQueue(pay);
                    }else{
                       Payment.setAutoID(id);
                    }
                    lineRegister = br.readLine();
                }
                br.close();
                //Se pone aqui para que se carge en la lista encolada
                util.Utility.setHeaderLinkedQueue(list);
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     
     
     
     //Para configuraciones
    // clinicName + ";" + imagesPath + ";" +schedule+";" + clinicTel + ";" + clinicEmail + ";" + clinicPassword + ";" + pathAppointment + ";" +
     //pathPatients + ";" + pathPayment + ";" + pathUsers + ";" + pathMedical + ";" + pathIllnes ;
     
     public BST getConfigurations(String path) {
        BST list = util.Utility.getBST();
        BufferedReader br = getBufferedReader(path);
                 
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String clinicName ="";
                    String imagesPath="";
                    Schedule schedule = null;
                    String clinicTel="";
                    String clinicEmail="";
                    String clinicPassword="";
                    String patth="";
                    
                    
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                clinicName = sT.nextToken();
                                controlTokens++;
                                break;
                            case 2:
                                imagesPath = sT.nextToken();
                                controlTokens++;
                                break;
                            case 3:
                                schedule = getSche(sT.nextToken());
                                controlTokens++;
                                break;
                            case 4:
                                clinicTel = sT.nextToken();
                                controlTokens++;
                                break;
                            case 5:
                                clinicEmail = sT.nextToken();
                                controlTokens++;
                                break;
                            case 6:
                                clinicPassword = sT.nextToken();
                                controlTokens++;
                                break;
                            case 7:
                                patth = sT.nextToken();
                                controlTokens++;
                                break;
                        }
                    }//End while   
                    
                    
                    
                    Configuration confi = new Configuration(clinicName, imagesPath, schedule, clinicTel, clinicEmail, clinicPassword, 
                            patth);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    
                   if(lineRegister != null){
                    list.add(confi);
                   }
                    
                    lineRegister = br.readLine();
                    
                }
                //Se pone aqui para que se carge en el addList
                  util.Utility.setBST(list);
                  br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     
     
     //Para shcedule de configuraciones
     // monday + "-" + monStart + "-" + monEnd + "-" + tuesday + "-" + tueStart + "-" + tueEnd + "-" + wednesday + "-" + wedStart + "-" + wedEnd 
     //+ "-" + thursday + "-" + thuStart + "-" + thuEnd + "-" + friday + "-" + friStart + "-" + friEnd ;
  
     private Schedule getSche(String line){
            int monday = 0;
            LocalTime monStart = null;
            LocalTime monEnd = null;   
            int tuesday = 0;
            LocalTime tueStart = null;
            LocalTime tueEnd = null;  
            int wednesday = 0;
            LocalTime wedStart = null;
            LocalTime wedEnd = null; 
            int thursday = 0;
            LocalTime thuStart = null;
            LocalTime thuEnd = null;  
            int friday = 0;
            LocalTime friStart = null;
            LocalTime friEnd = null;
            int saturday = 0;
            LocalTime satStart = null;
            LocalTime satEnd = null;
            int sunday = 0;
            LocalTime sunStart = null;
            LocalTime sunEnd = null;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
            
            
             StringTokenizer sT = new StringTokenizer(line, "-");
                    int controlTokens = 1;
            while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                monday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 2:
                                monStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 3:
                                monEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                             case 4:
                                tuesday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                tueStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 6:
                                tueEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 7:
                                wednesday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 8:
                                wedStart =LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 9:
                                wedEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 10:
                                thursday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 11:
                                thuStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 12:
                                thuEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 13:
                                friday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 14:
                                friStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 15:
                                friEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 16:
                                saturday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 17:
                                satStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 18:
                                satEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                             case 19:
                                sunday = Integer.parseInt(sT.nextToken());
                                controlTokens++;
                                break;
                            case 20:
                                sunStart = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 21:
                                sunEnd = LocalTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            
                        }
                    }//End while 
            Schedule s = new Schedule(monday, monStart, monEnd, tuesday, tueStart, tueEnd, wednesday, wedStart, wedEnd, thursday, 
                    thuStart, thuEnd, friday, friStart, friEnd, saturday, satStart, satEnd, sunday, sunStart, sunEnd);
            
            return s;
     }
     
     
     
      public SinglyLinkedList getUsers(String path) {
        SinglyLinkedList list = util.Utility.getSinglyLinkedListPassword();
        BufferedReader br = getBufferedReader(path);
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String user = "";
                    String password = "";
                    String type = "";

                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;
                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                user = sT.nextToken();
                                controlTokens++;
                                break;
                            case 2:
                                password = sT.nextToken();
                                controlTokens++;
                                break;
                            case 3:
                                type = sT.nextToken();
                                controlTokens++;
                                break;
                            default:
                                break;
                        }
                    }//End while   
      
                    Security sec  = new Security(user, password, type);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(lineRegister != null){
                        list.add(sec);
                    }
                    lineRegister = br.readLine();
                   
                }
                //Se pone aqui para que se carge en el addList
                //pone la lista en el set de utility
                br.close();
                util.Utility.setSinglyLinkedListPassword(list);
                
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    //Añadir doctores
    public CircularDoublyLinkedList getDoctors(String path) {
    
        CircularDoublyLinkedList list = util.Utility.getCircularDoublyLinkedList();
        BufferedReader br = getBufferedReader(path);
                 
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    String fName = "";
                    String lstName = "";
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthday = null;
                    String phone ="";
                    String email = "";
                    String address = "";
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                id = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Separa el id
                                break;
                            case 2:
                                lstName = sT.nextToken();
                                controlTokens++;//El Apellido
                                break;
                            case 3:
                                fName = sT.nextToken();
                                controlTokens++;//EL nombre
                                break;
                            case 4:
                                //COnvierte el brithday de formato a un DATE
                                birthday = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                phone = sT.nextToken();
                                controlTokens++;//EL numero de telefono
                                break;
                            case 6:
                                email = sT.nextToken();
                                controlTokens++;///El email
                                break;
                            case 7:
                                address = sT.nextToken();
                                controlTokens++;//La direccion
                                break;
                        }
                    }//End while   
                     Doctor d = new Doctor(id, lstName, fName, birthday, phone,email, address);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    
                    if(lineRegister != null){
                        list.add(d);
                    }
                    lineRegister = br.readLine();
                    
                }
                //Se pone aqui para que se carge en el addList
                  util.Utility.setCircularDoublyLinkedList(list);
                     br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    //Para obetner los datos dela rchivo y cargarlos alas listas
    //Añadiendo appointments
    public DoublyLinkedList getAppointments(String path) {
        DoublyLinkedList list = util.Utility.getDoublyLinkedList();
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
                BufferedReader br = getBufferedReader(path);
                String lineRegister = br.readLine();
                while (lineRegister != null) {
             
                    int idPatient = 0;
                    int idDoctor = 0;
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime dateTime = null; 
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;
                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                idPatient = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del Paciente
                                break;
                            case 2:
                                idDoctor = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del doctor
                                break;
                            case 3:
                                //Convierte de String a local date time
                                dateTime = LocalDateTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                        }
                    }//End while   
                     Appointment ap = new Appointment(idPatient, idDoctor, dateTime);
                    
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                      if(lineRegister != null){
                         list.add(ap);
                      }
                    
                    lineRegister = br.readLine();
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setDoublyLinkedList(list);
                   br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
   
    
      //Para obetner los datos dela rchivo y cargarlos alas listas
    //Añadiendo citas medicas
    public BTree getMedical(String path) {
        BTree list = util.Utility.getBTree();
        BufferedReader br = getBufferedReader(path);
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    int idDoctor = 0;
                    int idPatient = 0;
                    int idSickness = 0;
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime dateTime = null; 
                    String remarks ="";
 
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                idDoctor = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del Paciente
                                break;
                            case 2:
                                idPatient = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del doctor
                                break;
                            case 3:
                                //Convierte de String a local date time
                                dateTime = LocalDateTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 4:
                                idSickness = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Remarks 
                                break;
                            case 5:
                                remarks = sT.nextToken();
                                controlTokens++;//Remarks 
                                break;    
                        }
                    }//End while   
                    MedicalCare mc = new MedicalCare(idDoctor, idPatient, dateTime, idSickness, remarks);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    
                    if(lineRegister != null){
                        list.add(mc);
                    }
                        lineRegister = br.readLine();
                 
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setBTree(list);
                   br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
   
    
    //Añadir pacientes
     public CircularLinkedList getPatients(String path) {
    
        CircularLinkedList list = util.Utility.getCircularLinkedList();
        BufferedReader br = getBufferedReader(path);
                 
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    String fName = "";
                    String lstName = "";
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthday = null;
                    String email = "";
                    String address = "";
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                id = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Separa el id
                                break;
                            case 2:
                                lstName = sT.nextToken();
                                controlTokens++;//El Apellido
                                break;
                            case 3:
                                fName = sT.nextToken();
                                controlTokens++;//EL nombre
                                break;
                            case 4:
                                //COnvierte el brithday de formato a un DATE
                                birthday = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                email = sT.nextToken();
                                controlTokens++;///El email
                                break;
                            case 6:
                                address = sT.nextToken();
                                controlTokens++;//La direccion
                                break;
                        }
                    }//End while   
                     Patient p = new Patient(id, lstName, fName, birthday,email, address);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(lineRegister != null){
                        list.add(p);
                    }
                    lineRegister = br.readLine();
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setCircularLinkedList(list);
                   br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    //Añadir enfermedades
    public SinglyLinkedList getIllness(String path) {
        SinglyLinkedList list = util.Utility.getSinglyLinkedList();
        BufferedReader br = getBufferedReader(path);
        File file = new File(path+".txt");
       
        try {
            //Revisa si el archivo existe
           if(file.exists()){
             String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String sick ="";
                    int id = 0;
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister,";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        if(controlTokens == 1){
                            id = Integer.parseInt(sT.nextToken());
                        }else if(controlTokens == 2){
                            sick = sT.nextToken();
                        }
                        controlTokens++;
                    }//End while   
                    
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(list.isEmpty()){
                        list.add(new Sickness(id, sick));
                        lineRegister = br.readLine();
                    }else if(!list.contains(new Sickness(id, sick))){
                        list.add(new Sickness(id, sick));
                        lineRegister = br.readLine();
                    }
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setSinglyLinkedList(list);
                   br.close();
           }else{
               //Sino existe se crea uno
               file.createNewFile();
           }
   
        }catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return list;
        }

    public AVL getReportG(String path) {
        AVL list = util.Utility.getAVL();
        BufferedReader br = getBufferedReader(path);
        File file = new File(path+".txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String user = "";
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime dateTime = null; 
                    String description ="";
 
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                 dateTime = LocalDateTime.parse(sT.nextToken(),format);
                                controlTokens++;//El id del Paciente
                                break;
                            case 2:
                                user = sT.nextToken();
                                controlTokens++;//El id del doctor
                                break;
                            case 3:
                                //Convierte de String a local date time
                                description = sT.nextToken();
                                controlTokens++;
                                break;   
                        }
                    }//End while   
                    Report r  = new Report(dateTime , user, description);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    
                    if(lineRegister != null){
                        list.add(r);
                    }
                      lineRegister = br.readLine();
                 
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setAVL(list);
                 br.close();
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
    }
    
    
    
    
}
