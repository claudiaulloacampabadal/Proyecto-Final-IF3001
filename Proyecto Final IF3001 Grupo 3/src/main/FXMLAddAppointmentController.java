/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dateTimePicker.DateTimePicker;
import domain.TDA.DoublyLinkedList;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddAppointmentController implements Initializable {
    
    DoublyLinkedList appoitments;
    @FXML
    private BorderPane bp;
    @FXML
    private ComboBox<?> cB_DoctorsList;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;
    @FXML
    private Pane pane;
    @FXML
     DateTimePicker calendarChoice = new DateTimePicker(LocalDateTime.now());
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendarChoice.relocate(195, 154);
        this.pane.getChildren().add(calendarChoice);

   }

    @FXML
    private void btnAddOnAction(ActionEvent event){
        
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
    }
    

 }