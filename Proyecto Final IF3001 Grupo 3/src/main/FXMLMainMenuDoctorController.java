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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLMainMenuDoctorController implements Initializable {
    
    Alert alert;

    @FXML
    private BorderPane bp;
    @FXML
    private Button btnPatients;
    @FXML
    private Button btnMedicalAttention;
    @FXML
    private Button btnPayment;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnHome;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label lbClinica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnPatientsOnAction(ActionEvent event) {
    }

    @FXML
    private void btnMedicalAttentionOnAction(ActionEvent event) {
    }

    @FXML
    private void btnPaymentOnAction(ActionEvent event) {
    }

    @FXML
    private void btnReportOnAction(ActionEvent event) {
    }

    @FXML
    private void btnExitOnAction(ActionEvent event) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to close this session?");
        alert.showAndWait();
           
            if(alert.getResult().getText().equalsIgnoreCase("aceptar"))
                System.exit(-1);
    }

    @FXML
    private void btnHomeOnAction(ActionEvent event) {
        
    }

    
}
