/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dateTimePicker.DateTimePicker;
import domain.Appointment;
import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.MailMessage;
import domain.Patient;
import domain.Security;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static main.FXMLAddDoctorController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddAppointmentController implements Initializable {
    MailMessage mail = new MailMessage();
    ArchiveTXT  archives = new ArchiveTXT();
    private DoublyLinkedList appointments;
    private DoublyLinkedList doctors;
    private SinglyLinkedList users;
    private CircularLinkedList patients;
    Alert alert;
    
    BorderPane appointmentPane;
    
    @FXML
    private BorderPane bp;
    @FXML
    private ComboBox<List<String>> cB_DoctorsList;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Pane pane;
    DateTimePicker calendarChoice = new DateTimePicker(LocalDateTime.now());
    @FXML
    private TextField idUserTextField;
    @FXML
    private TextField numTextField;
    @FXML
    private TextArea txtARemarks;
    @FXML
    private Button btnClean;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //CALENDAR CHOICE ES UN DATE PICKER
            calendarChoice.relocate(100, 154);
            this.pane.getChildren().add(calendarChoice);
            //Se llenan todas las listas para revisarlas
            this.appointments = util.Utility.getDoublyLinkedListAppointment();
            this.appointmentPane = util.Utility.getBorderPaneAppointment();
            if(!util.Utility.getDoublyLinkedList().isEmpty()){
                this.doctors = util.Utility.getDoublyLinkedList();
            }else{
                this.doctors = getDoctors();
            }
            this.users = util.Utility.getSinglyLinkedListPassword();
            if(!doctors.isEmpty() && doctors != null) {
                this.cB_DoctorsList.getItems().addAll(getData());
            }
            if(!users.isEmpty()){
                Security s = (Security) users.getNode(util.Utility.getUser()).data;
                idUserTextField.setText(s.getUser());
            }
             if(!util.Utility.getCircularLinkedList().isEmpty()) {
                this.patients = util.Utility.getCircularLinkedList();
            }else{
                 this.patients = getPatients();
             }
            
            
            numTextField.setText(String.valueOf(Appointment.getAutoID()+1));
            
            
            
        } catch (ListException ex) {
            Logger.getLogger(FXMLAddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
    
    public static void loadPage(URL ui, BorderPane bp){
        Parent root = null;
        try {
            root = FXMLLoader.load(ui); 
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
        //cleaning nodes
        bp.setTop(null);
        bp.setCenter(null); 
        bp.setBottom(null); 
        bp.setLeft(null);
        bp.setRight(null);
        
        bp.setCenter(root);
    }

    
    @FXML
    private void btnAddOnAction(ActionEvent event){
        //Añadir una cita
            try{
                if(calendarChoice.getTime() != null &&  cB_DoctorsList.getValue() != null){
                    try { 
                        int autoId = Appointment.getAutoID();
                        String id = String.valueOf(cB_DoctorsList.getValue()).replace("[", "").replace("]", "").substring(0, 9);
                            Date today = calendarChoice.getTime();
                            LocalDateTime ldt = LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
                        
                        if(appointments != null && !appointments.isEmpty() && appointments.contains(new Appointment( Integer.parseInt(id), Integer.parseInt(idUserTextField.getText()), ldt, txtARemarks.getText()))){
                             Appointment.setAutoID(autoId);  
                             
                        }else{
                            Appointment.setAutoID(autoId);
                            Appointment ap = new Appointment(Integer.parseInt(idUserTextField.getText()), Integer.parseInt(id)
                               , ldt, txtARemarks.getText());
                            int index = patients.indexOf(new Patient(ap.getPatientID(), "", "", null, "", ""));
                            Patient p = (Patient) patients.getNode(index).data;
                            index = doctors.indexOf(new Doctor(ap.getDoctorID(), "", "", null, "", "", ""));
                            Doctor d = (Doctor) doctors.getNode(index).data;
                                if(!appointments.contains(ap)){
                                    appointments.add(ap);
                                    util.Utility.setDoublyLinkedListAppointment(appointments);
                                    addArchive(ap,"appointment");
                                    btnCleanOnAction(event);
                                    numTextField.setText(String.valueOf(ap.getIdentity()));
                                    alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Appointment - Add");
                                    alert.setContentText("Element add succesfully");
                                    alert.show();
                                    loadPage(getClass().getResource("FXMLAppointment.fxml"),appointmentPane); 
                                     mail.sendMail(p.getEmail(), p.getLastname() + ", " + p.getFirstname(), util.Utility.message("Appointment", "", "", util.Utility.formatDateTime(ap.getDateTime()), 
                                            d.getLastname()+", "+d.getFirstname()));
                                    btnCleanOnAction(event);
                                }else{
                                    alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Appointment - Add");
                                    alert.setContentText("Element is Repeated");
                                    alert.show(); 
                                }
                        }
                       
                    } catch (ListException ex) {
                        Logger.getLogger(FXMLAddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLAddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                   alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment - Update");
                    alert.setContentText("Fill all the blank spaces");
                    alert.show();

                }
            }catch(NumberFormatException nfe){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patient - Update");
                alert.setContentText("Invalid character, try a number.");
                alert.show(); 
            }
      
             
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        Stage mystage = (Stage) btnClose.getScene().getWindow();
        mystage.close();
    }
    
    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        txtARemarks.setText("");
    }
    

    
    private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= doctors.size(); i++) {
                Doctor d = (Doctor) doctors.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                arrayList.add(d.toString());
        
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
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
    
       //Para obetner los datos dela rchivo y cargarlos alas listas
    private DoublyLinkedList getDoctors() {
    
        DoublyLinkedList list = util.Utility.getDoublyLinkedList();
        BufferedReader br = archives.getBufferedReader("doctors");
                 
        File file = new File("doctors.txt");

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
                  util.Utility.setDoublyLinkedList(list);

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
    

 }