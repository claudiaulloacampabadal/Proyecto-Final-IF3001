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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLMainMenuPatientController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Button btnPayment;
    @FXML
    private Button btnAppointment;
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
    private void btnPaymentOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAppointmentOnAction(ActionEvent event) {
    }

    @FXML
    private void btnReportOnAction(ActionEvent event) {
    }

    @FXML
    private void btnExitOnAction(ActionEvent event) {
    }

    @FXML
    private void btnHomeOnAction(ActionEvent event) {
    }
    
}
