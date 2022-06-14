/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.MailMessage;
import domain.TDA.CircularLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import static main.FXMLPatientsController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddPatientController implements Initializable {
    
    CircularLinkedList patients;

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
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.patients = util.Utility.getCircularLinkedList();
        
    }   
    
     public static void loadPage(URL ui, BorderPane bp) {
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
              try {
           //Cuando se añade se manda un correo
            //Se manda ID y password con logo y nombre de la clinic
           //actualmente manda un correro ami correro, para mandarlo a otro se modifica el string con el correo que se quiere a enviar
            MailMessage.sendMail("macebonilla03@gmail.com","Maria Celeste");
            //en el correo se envia el usuario y la contraseña
          } catch (Exception ex) {
           Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLPatient.fxml"), bp);
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        idTextField.setText("");
        firstNTextField.setText("");
        lastNTextField.setText("");
        addressTextField.setText("");
        emailTextField.setText("");
    }
    
}
