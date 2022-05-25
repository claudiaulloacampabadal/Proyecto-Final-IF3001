/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.MailMessage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLPatientsController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private TableView<?> patientsTableView;
    @FXML
    private TableColumn<?, ?> idTableColumn;
    @FXML
    private TableColumn<?, ?> emailTableColumn;
    @FXML
    private TableColumn<?, ?> firstNameTableColumn;
    @FXML
    private TableColumn<?, ?> lastNameTableColumn;
    @FXML
    private TableColumn<?, ?> birthdayTableColumn;
    @FXML
    private TableColumn<?, ?> nameTableColumn1;
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

    @FXML
    private void btnCreateOnAction(ActionEvent event){

       try {
//            //Cuando se añade se manda un correo
//            //Se manda ID y password con logo y nombre de la clinic
//actualmente manda un correro ami correro, par amandarlo a otro se modifica el string con el correo que se quiere a enviar
            MailMessage.sendMail("macebonilla03@gmail.com");
          } catch (Exception ex) {
           Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
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
