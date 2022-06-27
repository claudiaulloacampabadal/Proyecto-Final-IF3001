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
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
import static main.FXMLModifyIllnesAndDiseaseController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLModifyDoctorController implements Initializable {

    private DoublyLinkedList doctors;
    private BorderPane doctorPane;
    ArchiveTXT archives = new ArchiveTXT();
    private Alert alert;
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
    private TextField phoneTextField;
    @FXML
    private ComboBox<List<String>> cB_Id;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se obtiene todo lo que se necesita
        //La lista enlazada
        this.doctors = util.Utility.getDoublyLinkedList();
        //El BorderPane del doctor
        this.doctorPane = util.Utility.getBorderPaneDoctor();
        //EL comboBox de los id de los doctores
        cB_Id.getItems().addAll(getData());
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
    private void btnUpdateOnAction(ActionEvent event) {
         if(!doctors.isEmpty()){
            try {
                  String id = String.valueOf(cB_Id.getValue()).replace("[","").replace("]", "");
                if(!id.equals("") && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("")&& calendarChoice.getValue() != null 
                   && !emailTextField.getText().equals("")&& !phoneTextField.getText().equals("") && !addressTextField.getText().equals("")){
             
                      Calendar date = Calendar.getInstance();
                     //Le da un set añ date para obtener los valores que se requieren
                     date.set(calendarChoice.getValue().getYear(),calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                    
                    Doctor d1 = new Doctor(Integer.parseInt(id), "", "", null, "", "", "");
                    Doctor d2 = new Doctor(Integer.parseInt(id),lastNTextField.getText(), firstNTextField.getText(), date.getTime(),phoneTextField.getText() , emailTextField.getText(), addressTextField.getText());
                    if(doctors.contains(d1)){
                        //Buscar donde esta
                        updateList(d1,d2);
                        modifyArchive(id,d2 );
                       alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Illness - Update");
                       alert.setContentText("Element update, succesfully!");
                       alert.show();
                        loadPage(getClass().getResource("FXMLDoctorsAndSpecialists.fxml"),doctorPane);
                    }else{
                       alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Illness - Update");
                       alert.setContentText("Element doesn't exist, try again");
                       alert.show();
                    }
                }else{
                     alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Illness - Update");
                    alert.setContentText("Fill all the blank spaces");
                    alert.show();
                }
     } catch (ListException ex) {
             Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
      } catch(NumberFormatException nfe){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Update");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
            }
            
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Update");
            alert.setContentText("List is Empty!");
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
        this.firstNTextField.setText("");
        this.lastNTextField.setText("");
        this.calendarChoice.getEditor().clear();
        this.emailTextField.setText("");
        this.addressTextField.setText("");
        this.phoneTextField.setText("");
    }
    
    
    private void updateList(Object ob1, Object ob2){
        try {
            DoublyLinkedList list = new DoublyLinkedList();
            Doctor d1 = (Doctor) ob1;
            Doctor d2 = (Doctor) ob2;
            
            for (int i = 1; i <= doctors.size(); i++) {
                if(util.Utility.equals(d1, doctors.getNode(i).data)){
                    list.add(new Doctor(d1.getId(), d2.getLastname(), d2.getFirstname(), d2.getBirthday(), d2.getPhoneNumber(), d2.getEmail(), d2.getAdress()));
                }else{
                   list.add(doctors.getNode(i).data);
              }
            }
            doctors.clear();
            for (int i = 1; i <= list.size(); i++) {
               doctors.add(list.getNode(i).data);
            }
            util.Utility.setDoublyLinkedList(doctors);
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void modifyArchive(String lineToModify, Object o) {
      try {
 
        File file = new File("doctors.txt");
        if (!file.isFile()) {
            return;
        }
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = archives.getBufferedReader("doctors");
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
        if (!tempFile.renameTo(new File("doctors.txt"))){
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
            for(int i = 1; i <= doctors.size(); i++) {
                Doctor d = (Doctor) doctors.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                arrayList.add(String.valueOf(d.getId()));
               
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
    
    
    
    
    
}
