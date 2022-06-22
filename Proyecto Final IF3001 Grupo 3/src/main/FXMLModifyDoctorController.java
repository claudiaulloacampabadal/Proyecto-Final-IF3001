/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLModifyDoctorController implements Initializable {

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
    private ComboBox<?> cB_Id;
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
        // TODO
    }    

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
    }
    
}
