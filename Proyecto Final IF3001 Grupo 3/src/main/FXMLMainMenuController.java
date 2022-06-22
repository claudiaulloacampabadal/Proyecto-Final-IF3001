/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
public class FXMLMainMenuController implements Initializable {

    Alert alert;
    @FXML
    private Button btnPatients;
    @FXML
    private Button btnDoctorsAndSpecial;
    @FXML
    private Button btnDisease;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnConfiguration;
    @FXML
    private Label lbClinica;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

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
        //bp.setTop(null);
        bp.setCenter(null); 
        //bp.setBottom(null); 
        //bp.setLeft(null);
        //bp.setRight(null);
        bp.setCenter(root);
    }


    @FXML
    private void btnPatientsOnAction(ActionEvent event) {
         loadPage(getClass().getResource("FXMLPatients.fxml"), bp);
    }

    @FXML
    private void btnDoctorsOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLDoctorsAndSpecialists.fxml"), bp);
    }

    @FXML
    private void btnDiseaseOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLIllnessAndDisease.fxml"), bp);
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
         this.lbClinica.setText("Clínica CFM");
        this.bp.setCenter(ap);
    }

    @FXML
    private void btnConfigurationOnAction(ActionEvent event) {
    }



    
}
