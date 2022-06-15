/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.Sickness;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import static main.FXMLAddPatientController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddDoctorController implements Initializable {
    ArchiveTXT archives = new ArchiveTXT();
    
    DoublyLinkedList doctors;
    Alert alert;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField idTextField;
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
    private TextField phoneTextField;
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
       this.doctors = util.Utility.getDoublyLinkedList();
       
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
    private void btnAddOnAction(ActionEvent event) {
         if(!doctors.isEmpty() && doctors != null){
            try{
             if(!idTextField.getText().equals("") && ! emailTextField.getText().equals("") && !phoneTextField.getText().equals("")
               && !addressTextField.getText().equals("") && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("")
               && !"".equals(calendarChoice)){
                 Calendar date = Calendar.getInstance();
                 date.set(calendarChoice.getValue().getYear(),calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                 Doctor d = new Doctor(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), phoneTextField.getText(), emailTextField.getText(), addressTextField.getText());
                 if(!doctors.contains(d)){
                     doctors.add(d);
                     util.Utility.setDoublyLinkedList(doctors);
                     addArchive(d);
                     btnCleanOnAction(event);
                     alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Doctors - Add");
                     alert.setContentText("Element add succesfully");
                     alert.show();
                 }else{
                   alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Doctors - Add");
                   alert.setContentText("Element is repeated");
                   alert.show();

                 }
             }else{
                alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Doctors - Add");
                 alert.setContentText("Fill ALL the blank spaces");
                 alert.show();

             }
            }catch(NumberFormatException nfe){
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Doctors - Add");
                 alert.setContentText("Invalid character, try a number.");
                 alert.show();
            
        } catch (ListException ex) {
            Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            try{
                //Para añadir el primer dato
             if(!idTextField.getText().equals("") && ! emailTextField.getText().equals("") && !phoneTextField.getText().equals("")
               && !addressTextField.getText().equals("") && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("")
               && !calendarChoice.equals("")){
                 Calendar date = Calendar.getInstance();
                 date.set(calendarChoice.getValue().getYear(),calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                 Doctor d = new Doctor(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), phoneTextField.getText(), emailTextField.getText(), addressTextField.getText());
                    doctors.add(d);
                    util.Utility.setDoublyLinkedList(doctors);
                    addArchive(d);
                    btnCleanOnAction(event);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Doctors - Add");
                    alert.setContentText("Element add succesfully");
                    alert.show();
             }else{
                alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Doctor - Add");
                 alert.setContentText("Fill the blank spaces");
                 alert.show();

             }
            }catch(NumberFormatException nfe){
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Doctor - Add");
                 alert.setContentText("Invalid character, try a number.");
                 alert.show();
            }
        }
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLDoctorsAndSpecialists.fxml"), bp);
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        this.idTextField.setText("");
        this.firstNTextField.setText("");
        this.lastNTextField.setText("");
        this.calendarChoice.getEditor().clear();
        this.emailTextField.setText("");
        this.addressTextField.setText("");
        this.phoneTextField.setText("");
    }
    
    private void addArchive(Doctor d) {
        BufferedReader br = archives.getBufferedReader("doctors");
        PrintStream ps = archives.getPrintStream(true, "doctors");
        File file = new File("doctors.txt");
        try {
            if(file.exists()){
             String lineRegister = "";
             while(lineRegister != null){

                lineRegister = br.readLine();

                if(lineRegister != null){

                }else{
                   ps.println(d.toString());
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
    
}
