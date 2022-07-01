/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.Security;
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
import javafx.stage.Stage;
import static main.FXMLAddIllnesAndDiseaseController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddDoctorController implements Initializable {
    ArchiveTXT archives = new ArchiveTXT();

    DoublyLinkedList doctors;
    BorderPane doctorPane;
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
        //Llama a la lista 
       this.doctors = util.Utility.getDoublyLinkedList();
       //Llama al BorderPane del doctor
       this.doctorPane = util.Utility.getBorderPaneDoctor();
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
                //Inspecciona que todas las casillas esten completas
             if(!idTextField.getText().equals("") && ! emailTextField.getText().equals("") && !phoneTextField.getText().equals("")
               && !addressTextField.getText().equals("") && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("")
               && !"".equals(calendarChoice)){
                 if(util.Utility.emailValidation(emailTextField.getText())){
                     //Le hace una isntancia para el date
                     Calendar date = Calendar.getInstance();
                     //Le da un set añ date para obtener los valores que se requieren
                     date.set(calendarChoice.getValue().getYear(),calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                     Doctor d = new Doctor(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), phoneTextField.getText(), emailTextField.getText(), addressTextField.getText());
                     Security s = new Security(idTextField.getText(),util.Utility.passwordGenerator() , "Doctor"); 
                     //Si no contiene el mismo id del doctor o algo similar
                     if(!doctors.contains(d)){
                         doctors.add(d);
                         util.Utility.setDoublyLinkedList(doctors);
                         addArchive(d,"doctors");
                         addArchive(s,"users");
                         btnCleanOnAction(event);
                         alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("Doctors - Add");
                         alert.setContentText("Element add succesfully");
                         alert.show();
                          loadPage(getClass().getResource("FXMLDoctorsAndSpecialists.fxml"),doctorPane);
                     }else{
                       alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Doctors - Add");
                       alert.setContentText("Element is repeated");
                       alert.show();

                     }
                  }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Doctor - Add");
                    alert.setContentText("Email wrong domain.com");
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
                 if(util.Utility.emailValidation(emailTextField.getText())){
                     Calendar date = Calendar.getInstance();
                     date.set(calendarChoice.getValue().getYear(),calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                     Doctor d = new Doctor(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), phoneTextField.getText(), emailTextField.getText(), addressTextField.getText());
                    //Algoritmo para genera una contraseña
                     Security s = new Security(idTextField.getText(),util.Utility.passwordGenerator() , "Doctor"); 
                        doctors.add(d);
                        util.Utility.setDoublyLinkedList(doctors);
                        addArchive(d,"doctors");
                        addArchive(s, "users");
                        btnCleanOnAction(event);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Doctors - Add");
                        alert.setContentText("Element add succesfully");
                        alert.show();
                        loadPage(getClass().getResource("FXMLDoctorsAndSpecialists.fxml"),doctorPane);
                 }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Doctor - Add");
                    alert.setContentText("Email wrong domain.com");
                    alert.show();
                 }
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
         Stage mystage = (Stage) btnClose.getScene().getWindow();
            mystage.close();
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
    
}
