/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Appointment;
import domain.Archives.ArchiveTXT;
import domain.MedicalCare;
import domain.Patient;
import domain.Security;
import domain.Sickness;
import domain.TDA.BTree;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLModifyPatientController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
//Este es por doctor
public class FXMLMedicalCareController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
    private CircularLinkedList patients;
    private DoublyLinkedList appointments;
    private SinglyLinkedList users;
    private BTree medicalCare;
    private SinglyLinkedList illness;
    @FXML
    private BorderPane bp;
    @FXML
    private ListView<?> illnessListView;
    @FXML
    private ComboBox<List<String>> cB_PatientsAppointments;
    @FXML
    private Button btnHistorial;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnPayment;
    @FXML
    private Button btnCleanOnAction;
    @FXML
    private TextField numIdText;

    /**
     * Initializes the controller class.
     */
    //Los doctores 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.users = util.Utility.getSinglyLinkedListPassword();
            
            if(!util.Utility.getDoublyLinkedListAppointment().isEmpty()){
                this.appointments = util.Utility.getDoublyLinkedListAppointment();
            }else{
                this.appointments = getAppointment();
            }
            if(!util.Utility.getBTree().isEmpty()){
                this.medicalCare = util.Utility.getBTree();
            }else{
                this.medicalCare = getMedical();
            }
            if(!util.Utility.getCircularLinkedList().isEmpty()){
                this.patients = util.Utility.getCircularLinkedList();
            }else{
                this.patients = getPatients();
            }
            //Si no esta vacia
            if(!util.Utility.getSinglyLinkedList().isEmpty()){
                //carga la lista utility, que añadio
                this.illness = util.Utility.getSinglyLinkedList();
            }else{//si esta vacia por primera vez entonces
                //Llama al metodo que carga el archivo
                this.illness = getIllness();
            }
            this.appointments.sort();
            if(!appointments.isEmpty() && !getData().isEmpty()){  
                cB_PatientsAppointments.setItems(getData());
            }
                 System.out.print(appointments.toString());
        } catch (ListException ex) {
            Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
      public static void loadPage(URL ui) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(ui);//carga el url

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            //crea un nuevo stage para que parezca sin el login
            stage.setScene(scene);
            stage.setTitle("Proyecto Final Gr3 - 2022");
            stage.setResizable(false);
            stage.show();
            //llama a la ventana login para cerrarla
     

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    }
  

    @FXML
    private void btnHistorialOnAction(ActionEvent event) {
        if(cB_PatientsAppointments.getValue() != null){
           util.Utility.setPatientId(cB_PatientsAppointments.getValue());
          loadPage(getClass().getResource("FXMLHistorialMedicalCare.fxml"));
        }else{//Alert que no se econtro
            
               
            
        }
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        
        if(!medicalCare.isEmpty() && medicalCare != null){
            
                
            
        }else{
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
            btnPayment.setVisible(true);
        
    }

    @FXML
    private void btnPaymentOnAction(ActionEvent event) {
         loadPage(getClass().getResource("FXMLAddPayment.fxml"));
    }
    
    
     private void addArchive(Object o, String path) {
        BufferedReader br = archives.getBufferedReader(path);
        PrintStream ps = archives.getPrintStream(true, path);
        File file = new File(path+".txt");
        try {
            if(file.exists()){
             String lineRegister = "";
             while(lineRegister != null){

                lineRegister = br.readLine();

                if(lineRegister != null){

                }else{
                   ps.println(o.toString());
                   break;
                 }
              }
            }else{
                file.createNewFile();
            }
       } catch (IOException ex) {
                Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    
    
       //El getData para la tableView
     private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= appointments.size(); i++) {
                Appointment ap = (Appointment) appointments.getNode(i).data;
                Security s = (Security) users.getNode(util.Utility.getUser()).data;
                if(Integer.parseInt(s.getUser()) == ap.getDoctorID()){
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(String.valueOf(ap.getIdentity()));
                    arrayList.add(String.valueOf(ap.getPatientID()));
                    arrayList.add(String.valueOf(ap.getDoctorID()));
                    arrayList.add(util.Utility.formatDateTime(ap.getDateTime()));
                    arrayList.add(ap.getRemarks());

                    data.add(arrayList);
                }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
        //Para obetner los datos dela rchivo y cargarlos alas listas
    private DoublyLinkedList getAppointment() {
        DoublyLinkedList list = util.Utility.getDoublyLinkedListAppointment();
        BufferedReader br = archives.getBufferedReader("appointment");
        File file = new File("appointment.txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {
             
                    int idPatient = 0;
                    int idDoctor = 0;
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
                            case 4:
                                remarks = sT.nextToken();
                                controlTokens++;//Remarks 
                                break;
                        }
                    }//End while   
                     Appointment ap = new Appointment(idPatient, idDoctor, dateTime, remarks);
                    
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                      if(lineRegister != null){
                         list.add(ap);
                      }
                    
                    lineRegister = br.readLine();
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setDoublyLinkedListAppointment(list);

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
    private BTree getMedical() {
        BTree list = util.Utility.getBTree();
        BufferedReader br = archives.getBufferedReader("medical");
        File file = new File("medical.txt");

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

            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
     private CircularLinkedList getPatients() {
    
        CircularLinkedList list = util.Utility.getCircularLinkedList();
        BufferedReader br = archives.getBufferedReader("patients");
                 
        File file = new File("patients.txt");

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
    
    //Remover las variables de un archivo
    //Se remueve por id
   private void removeArchive(String lineToRemove, String path) {
      try {
 
        File file = new File(path+".txt");
 
        if (!file.isFile()) {
            return;
        }
 
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = archives.getBufferedReader(path);
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
   
   private SinglyLinkedList getIllness() {
        SinglyLinkedList list = util.Utility.getSinglyLinkedList();
        BufferedReader br = archives.getBufferedReader("illness");
        File file = new File("illness.txt");
       
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
    
}
