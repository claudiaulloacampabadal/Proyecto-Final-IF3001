/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import domain.Payment;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.QueueException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLAddPatientController.loadPage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLAddPaymentController implements Initializable {
    Alert alert;
    private HeaderLinkedQueue payment;
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
    @FXML
    private Button btnSend;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (!util.Utility.getHeaderLinkedQueue().isEmpty()) {
            this.payment = util.Utility.getHeaderLinkedQueue();
        }
        try {
            if (!payment.isEmpty() && !getData().isEmpty()) {
                paymentComboBox.setItems(getData());
            }
            System.out.print(payment.toString());
        } catch (QueueException ex) {
            Logger.getLogger(FXMLAddPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    private void btnCreateOnAction(ActionEvent event) throws QueueException {
        try {
            if (!idTextField.getText().equals("") && !serviceTextField.getText().equals("") && !"".equals(calendarChoice) && !totalTextField.getText().equals("")) {
                if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                    //Le hace una isntancia para el date
                    Calendar date = Calendar.getInstance();
                    //Le da un set a date para obtener los valores que se requieren               
                    date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                    payment.enQueue(new Payment(Integer.parseInt(idTextField.getText()), paymentComboBox.getAccessibleText(), Double.parseDouble(serviceTextField.getText()), date.getTime(), Double.parseDouble(totalTextField.getText())));
                    if (!payment.contains(payment)) {
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
        Stage mystage = (Stage) btnCreate.getScene().getWindow();
        mystage.close();
    }
    
     //El getData para la tableView
     private ObservableList<List<String>> getData() throws QueueException{
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        domain.TDA.HeaderLinkedQueue c2= new domain.TDA.HeaderLinkedQueue();

        while (!payment.isEmpty()) {
            //desencola
            Payment p = (Payment) payment.deQueue();
            
            //encola en la auxiliar
            c2.enQueue(p);
            
            //Se le actualizan los cambios a la cola original en utility
            util.Utility.setHeaderLinkedQueue(payment);

            List<String> arrayList = new ArrayList<>();
            arrayList.add(" "+String.valueOf(p.getId()));
            arrayList.add(" "+util.Utility.format(p.getBillingDate()));
            arrayList.add(" "+String.valueOf(p.getPaymentMode()));
            arrayList.add(" "+String.valueOf(p.getServiceCharge()));
            arrayList.add(" "+String.valueOf(p.getTotalCharge()));

            data.add(arrayList);
        }
        payment=c2;
        util.Utility.setHeaderLinkedQueue(payment);

        return data;
    }

    @FXML
    private void btnSendOnAction(ActionEvent event) {
    }
    
    //desencolar por id
   private void remove(Object element, HeaderLinkedQueue queue) throws QueueException{
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
       
        while(!queue.isEmpty()){
             if(util.Utility.equals(queue.front(), element)){
                 queue.deQueue();
             }else{
                 aux.enQueue(queue.deQueue());
             }
        }
   
        while(!aux.isEmpty()){
            queue.enQueue(aux.deQueue());
        }
        
    }

}
