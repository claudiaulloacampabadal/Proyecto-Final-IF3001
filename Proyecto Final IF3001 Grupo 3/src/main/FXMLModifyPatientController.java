/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.MailMessage;
import domain.Patient;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLModifyDoctorController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLModifyPatientController implements Initializable {
    
    ArchiveTXT archives = new ArchiveTXT();
    CircularLinkedList patients;
    SinglyLinkedList users;
    private Alert alert;
    BorderPane patientPane;

    @FXML
    private BorderPane bp;
    @FXML
    private TextField firstNTextField;
    @FXML
    private TextField lastNTextField;
    @FXML
    private DatePicker calendarChoice;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<List<String>> cB_IdPatient;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.patients = util.Utility.getCircularLinkedList();
        this.users = util.Utility.getSinglyLinkedListPassword();
        this.patientPane = util.Utility.getBorderPanePatient();
        if(patients != null && !patients.isEmpty()){
            cB_IdPatient.getItems().addAll(getData());
        }
    } 
    
    
     public static void loadPage(URL ui, BorderPane bp) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(ui);//carga el url
             bp.setCenter(null); 
             bp.setRight(null);
             bp.setLeft(null);
             bp.setTop(null);
             bp.setBottom(null);
    
            bp.setCenter(root);
            
            //llama a la ventana login para cerrarla
          //cierra la pantalla del login

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    }

    @FXML
    private void btnSearchOnAction(ActionEvent event) {
        if(cB_IdPatient.getValue() != null){
            try {
                String id = String.valueOf(cB_IdPatient.getValue()).replace("[","").replace("]", "");
                int index = patients.indexOf(new Patient(Integer.parseInt(id), "", "", null, "", ""));
                Patient p = (Patient) patients.getNode(index).data;
                this.firstNTextField.setText(p.getFirstname());
                this.lastNTextField.setText(p.getLastname());
                //Para poner la fecha de nuevo
                Date oldDate = p.getBirthday();
                 LocalDate newDate = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                this.calendarChoice.setValue(newDate);
                this.emailTextField.setText(p.getEmail());
                this.addressTextField.setText(p.getAdress());
            } catch (ListException ex) {
                Logger.getLogger(FXMLModifyPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Patient - Update");
            alert.setContentText("Select the ID to search");
            alert.show();
            
        }
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        if (!patients.isEmpty()) {
            try {
                String id = String.valueOf(cB_IdPatient.getValue()).replace("[", "").replace("]", "");
                if (!id.equals("") && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("")
                        && !emailTextField.getText().equals("") && !addressTextField.getText().equals("")) {
                    if (util.Utility.emailValidation(emailTextField.getText())) {
                            Calendar date = Calendar.getInstance();
                            //Le da un set añ date para obtener los valores que se requieren
                            date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue() - 1, calendarChoice.getValue().getDayOfMonth());

                            Patient p1 = new Patient(Integer.parseInt(id), "", "", null, "", "");
                            Patient p2 = new Patient(Integer.parseInt(id), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), emailTextField.getText(), addressTextField.getText());
                            if (patients.contains(p1)) {
                                //Buscar donde esta
                                updateList(p1, p2);
                                modifyArchive(id, p2);
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Patient - Update");
                                alert.setContentText("Element update, succesfully!");
                                alert.show();
                                loadPage(getClass().getResource("FXMLPatients.fxml"), patientPane);
                            }
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Patient - Update");
                        alert.setContentText("Fill the email space correctly");
                        alert.show();

                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Patient - Update");
                    alert.setContentText("Fill all the blank spaces");
                    alert.show();
                }
            } catch (NumberFormatException nfe) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patient - Update");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            } catch (ListException ex) {
                Logger.getLogger(FXMLModifyPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        Stage mystage = (Stage) btnClose.getScene().getWindow();
        mystage.close();
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        this.firstNTextField.setText("");
        this.lastNTextField.setText("");
        this.calendarChoice.getEditor().clear();
        this.emailTextField.setText("");
        this.addressTextField.setText("");
    }
    
    
       private void updateList(Object ob1, Object ob2){
        try {
            DoublyLinkedList list = new DoublyLinkedList();
            Patient p1 = (Patient) ob1;
            Patient p2 = (Patient) ob2;
            
            for (int i = 1; i <= patients.size(); i++) {
                if(util.Utility.equals(p1, patients.getNode(i).data)){
                    list.add(new Patient(p1.getId(), p2.getLastname(), p2.getFirstname(), p2.getBirthday(), p2.getEmail(), p2.getAdress()));
                }else{
                   list.add(patients.getNode(i).data);
              }
            }
            patients.clear();
            for (int i = 1; i <= list.size(); i++) {
               patients.add(list.getNode(i).data);
            }
            util.Utility.setCircularLinkedList(patients);
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void modifyArchive(String lineToModify, Object o) {
      try {
 
        File file = new File("patients.txt");
        if (!file.isFile()) {
            return;
        }
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = archives.getBufferedReader("patients");
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
                if (!id.equalsIgnoreCase(lineToModify)) {
                    pw.println(line);
                    pw.flush();
                }else{
                    pw.println(o.toString());
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
        if (!tempFile.renameTo(new File("patients.txt"))){
            System.out.println("Could not rename file");
 
        }
    } catch (FileNotFoundException ex) {
    } catch (IOException ex) {
    }
        
   }
    
    private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= patients.size(); i++) {
                Patient p = (Patient) patients.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                arrayList.add(String.valueOf(p.getId()));
               
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
}
