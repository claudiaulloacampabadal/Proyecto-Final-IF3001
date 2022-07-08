/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Appointment;
import domain.Archives.ArchiveTXT;
import domain.Configuration;
import domain.MedicalCare;
import domain.Report;
import domain.Security;
import domain.Sickness;
import domain.TDA.AVL;
import domain.TDA.BST;
import domain.TDA.BTree;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import domain.TDA.TreeException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private AVL reports;
    Alert alert;
    @FXML
    private BorderPane bp;
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
    @FXML
    private TextArea txtAnnotations;
    @FXML
    private ComboBox<List<String>> cB_Illness;

    /**
     * Initializes the controller class.
     */
    //Los doctores 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Se cargan las listas
            this.users = util.Utility.getSinglyLinkedListPassword();
            this.appointments = util.Utility.getDoublyLinkedList();
            this.medicalCare = util.Utility.getBTree();
            this.patients = util.Utility.getCircularLinkedList();
            this.illness = util.Utility.getSinglyLinkedList();
               this.reports = util.Utility.getAVL();
            this.appointments.sort();
            if(!appointments.isEmpty() && !getData().isEmpty()){  
                cB_PatientsAppointments.setItems(getData());
            }
            cB_Illness.setItems(getDataIllness());
        } catch (ListException ex) {
            Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
        }
        numIdText.setText(String.valueOf(MedicalCare.getAutoID()+1)); 
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
            try {
                util.Utility.setPatientId(cB_PatientsAppointments.getValue());
                Security s = (Security) users.getNode(util.Utility.getUser()).data;
                if(medicalCare.contains(new MedicalCare(Integer.parseInt(s.getUser()),
                  Integer.parseInt(util.Utility.getPatientId()), LocalDateTime.MIN, 0, ""))){
                    System.out.print(s.getUser());
                    util.Utility.setPatientId(cB_PatientsAppointments.getValue());
                    loadPage(getClass().getResource("FXMLHistorialMedicalCare.fxml"));
                }
                
            } catch (ListException ex) {
                Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TreeException ex) {
                Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{//Alert que no se encontro
           alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Medical Care Historial - Add");
                 alert.setContentText("Choose a patient!");
                 alert.show();   
               
            
        }
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        if(!medicalCare.isEmpty() && medicalCare != null){
            int autoId = MedicalCare.getAutoID();
            if(!txtAnnotations.getText().equals("")&& cB_PatientsAppointments.getValue()!= null &&
                cB_Illness.getValue() != null){
               try {
                    String idPat = String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", "").substring(0,9);
                    String [] idIll = String.valueOf(cB_Illness.getValue()).replace("[","").replace("]", "").split(";");
                    String date =  String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", "").substring(20,36);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    Security s = (Security) users.getNode(util.Utility.getUser()).data;
                    //Doctor ID Pattient ID LocalDateTime Illness ID Annotations
                    MedicalCare mc = new MedicalCare(Integer.parseInt(s.getUser()), Integer.parseInt(idPat), LocalDateTime.now(), Integer.parseInt(idIll[0]), 
                            txtAnnotations.getText());
                    MedicalCare.setAutoID(autoId);
                    BST configurations = util.Utility.getBST();
                    Configuration confg = (Configuration) configurations.getRoot().data;
                    
                    
                    medicalCare.add(mc);
                    archives.addArchive(mc, confg.getPath()+"\\medical");
                    if(autoId == appointments.size()){
                        appointments.removeLast();
                    }else{
                      appointments.remove(new Appointment(Integer.parseInt(idPat),Integer.parseInt(s.getUser()) , LocalDateTime.parse(date,format)));
                    }
                    util.Utility.setDoublyLinkedList(appointments);
                    btnPayment.setVisible(true);
                     removeArchiveAp(String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", ""), "appointment");
                     alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Medical Care - Add");
                     alert.setContentText("Element add succesfully");
                     alert.show();
                    numIdText.setText(String.valueOf(autoId+1));
                    txtAnnotations.setText("");
                    loadPage(getClass().getResource("FXMLAddAppointmentDoctor.fxml"));
                    alert.close();
                } catch (ListException ex) {
                    Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Appointment - Add");
                 alert.setContentText("Fill ALL the black spaces");
                 alert.show();    
            }
        }else{
            int autoId = MedicalCare.getAutoID();
            if(!txtAnnotations.getText().equals("")&& cB_PatientsAppointments.getValue()!= null &&
                cB_Illness.getValue() != null){
               try {
                    String idPat = String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", "").substring(0,9);
                    String [] idIll = String.valueOf(cB_Illness.getValue()).replace("[","").replace("]", "").split(";");
                    String date =  String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", "").substring(20,36);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    Security s = (Security) users.getNode(util.Utility.getUser()).data;
                    //Doctor ID Pattient ID LocalDateTime Illness ID Annotations
                    MedicalCare mc = new MedicalCare(Integer.parseInt(s.getUser()), Integer.parseInt(idPat), LocalDateTime.now(), Integer.parseInt(idIll[0]), 
                            txtAnnotations.getText());
                    MedicalCare.setAutoID(autoId);
                    medicalCare.add(mc);
                    BST configurations = util.Utility.getBST();
                     Configuration confg = (Configuration) configurations.getRoot().data;
                    archives.addArchive(mc, confg.getPath()+"\\medical");
                      appointments.remove(new Appointment(Integer.parseInt(idPat),Integer.parseInt(s.getUser()) , LocalDateTime.parse(date,format)));
                    util.Utility.setDoublyLinkedList(appointments);
                    btnPayment.setVisible(true);
                     removeArchiveAp(String.valueOf(cB_PatientsAppointments.getValue()).replace("[","").replace("]", ""), confg.getPath()+"\\appointment");
                     reports.add(new Report(LocalDateTime.now(),"Doctor", "Añadio un medical care"));
                    archives.addArchive(new Report(LocalDateTime.now(),"Doctor", "Añadio un medical care"), "reportg");
                    util.Utility.setAVL(reports);
                    
                     alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Medical Care - Add");
                     alert.setContentText("Element add succesfully");
                     alert.show();
                    numIdText.setText(String.valueOf(autoId+1));
                    loadPage(getClass().getResource("FXMLAddAppointmentDoctor.fxml"));
                    alert.close();
                } catch (ListException ex) {
                    Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("MedicalCare - Add");
                 alert.setContentText("Fill ALL the black spaces");
                 alert.show();    
            }
            
            
        }
       
        
    }

    @FXML
    private void btnPaymentOnAction(ActionEvent event) {
        if(cB_PatientsAppointments.getValue() != null){
         util.Utility.setPatientId(cB_PatientsAppointments.getValue());
         loadPage(getClass().getResource("FXMLPayment.fxml"));
        }else{
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Payment - Add");
            alert.setContentText("Select and Appointment Patient");
           alert.show();    
            
        }
    }
      @FXML
    private void btnCleanOnAction(ActionEvent event) {
        txtAnnotations.setText("");
        cB_PatientsAppointments.getItems().addAll(getData());
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
                    if(Integer.parseInt(s.getUser()) == ap.getDoctorID()){
                        arrayList.add(String.valueOf(ap.toString()));

                        data.add(arrayList);
                    }
               }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
    //Remover las variables de un archivo
    //Se remueve por id

    private ObservableList<List<String>> getDataIllness() {
         ObservableList<List<String>> data = FXCollections.observableArrayList();
        try {
            for(int i = 1; i <= illness.size(); i++) {
                Sickness ill = (Sickness) illness.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                
                arrayList.add(ill.toString());
                
                data.add(arrayList);
                
            }
  
        } catch (ListException ex) {
            Logger.getLogger(FXMLMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
        }
          return data; 
   }
    
    public void removeArchiveAp(String lineToRemove, String path) {
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
   
                //Para separar los tokens
              
                      //Reviso que id no sea el mismo que el "line to remove"
                if (!line.equalsIgnoreCase(lineToRemove)) {
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

    
}
