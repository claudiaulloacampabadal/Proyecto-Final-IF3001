/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.MailMessage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javax.mail.MessagingException;
import static main.FXMLMainMenuController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLPatientsController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private TableView<List<String>> patientsTableView;
    @FXML
    private TableColumn<List<String>, String> idTableColumn;
    @FXML
    private TableColumn<List<String>, String> emailTableColumn;
    @FXML
    private TableColumn<List<String>, String> firstNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> lastNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> birthdayTableColumn;
       @FXML
    private TableColumn<List<String>, String> addressTableColumn;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnRead;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void btnCreateOnAction(ActionEvent event){
        //Se manda a bari el lugar para agregar a los pacientes
        loadPage(getClass().getResource("FXMLAddPatient.fxml"), bp);
    }
    
    @FXML
    private void btnReadOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
    }
    
    
    
    
}
