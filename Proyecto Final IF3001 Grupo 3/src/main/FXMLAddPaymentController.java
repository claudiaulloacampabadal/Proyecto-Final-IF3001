/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import domain.Patient;
import domain.Payment;
import domain.TDA.HeaderLinkedQueue;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLAddPaymentController implements Initializable {
    Alert alert;
    private Payment payment;

    @FXML
    private Button btnCreate;
    @FXML
    private TextField serviceTextField;
    @FXML
    private TextField totalTextField;
    @FXML
    private ComboBox<List<String>> paymentComboBox;
    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker calendarChoice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        if (!idTextField.getText().equals("") && !serviceTextField.getText().equals("") && !"".equals(calendarChoice)) {
            if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                //Le hace una isntancia para el date
                Calendar date = Calendar.getInstance();
                //Le da un set a date para obtener los valores que se requieren
                date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
            }
        }
    }
}
