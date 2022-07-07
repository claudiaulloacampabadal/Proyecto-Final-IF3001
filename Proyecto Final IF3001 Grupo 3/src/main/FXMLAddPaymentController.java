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
import javafx.scene.layout.BorderPane;
import static main.FXMLAddPatientController.loadPage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLAddPaymentController implements Initializable {
    Alert alert;
    private Payment payment;
    BorderPane paymentPane;

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
    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        try {
            if (!idTextField.getText().equals("") && !serviceTextField.getText().equals("") && !"".equals(calendarChoice) && !totalTextField.getText().equals("")) {
                if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                    //Le hace una isntancia para el date
                    Calendar date = Calendar.getInstance();
                    //Le da un set a date para obtener los valores que se requieren               
                    date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                    payment = new Payment(Integer.parseInt(idTextField.getText()), paymentComboBox.getAccessibleText(), Integer.parseInt(serviceTextField.getText()), date.getTime(), Integer.parseInt(totalTextField.getText()));
                    if (!payment.equals(idTextField)) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creating bill");
                        alert.setContentText("Created bill");
                        alert.show();
                        loadPage(getClass().getResource("FXMLPayment.fxml"), paymentPane);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Creating bill");
                        alert.setContentText("Element is repeated");
                        alert.show();
                    }
                } else {//Revisa que no esten vacios los espacios
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Creating bill");
                    alert.setContentText("Fill ALL the blank spaces");
                    alert.show();

                }
            }
        } catch (NumberFormatException nfe) {//La validacion si insertan una letra
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Patients - Add");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
        }
    }
}
