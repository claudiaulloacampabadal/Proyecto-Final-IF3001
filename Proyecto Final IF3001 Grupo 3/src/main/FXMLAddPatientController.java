/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.MailMessage;
import domain.Patient;
import domain.Security;
import domain.TDA.CircularLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLAddDoctorController.loadPage;


/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddPatientController implements Initializable {
    
    MailMessage mail = new MailMessage();
    ArchiveTXT archives = new ArchiveTXT();
    CircularLinkedList patients;
    SinglyLinkedList users;
    private Alert alert;
    BorderPane patientPane;

    @FXML
    private BorderPane bp;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField firstNTextField;
    @FXML
    private TextField lastNTextField;
    @FXML
    private DatePicker calendarChoice;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.patients = util.Utility.getCircularLinkedList();
        this.users = util.Utility.getSinglyLinkedListPassword();
        this.patientPane = util.Utility.getBorderPanePatient();

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
    private void btnAddOnAction(ActionEvent event) {
        if (!patients.isEmpty() && patients != null) {//Cuando no esta vacia entra aquie para ver si no se repide un id
            try {
                //Inspecciona que todas las casillas esten completas
                if (!idTextField.getText().equals("") && !emailTextField.getText().equals("") && !addressTextField.getText().equals("")
                        && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("") && !"".equals(calendarChoice)) {

                    if (util.Utility.emailValidation(emailTextField.getText())) {

                        if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                            //Le hace una isntancia para el date
                            Calendar date = Calendar.getInstance();
                            //Le da un set añ date para obtener los valores que se requieren
                            date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                            Patient p = new Patient(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), emailTextField.getText(), addressTextField.getText());
                            Security s = new Security(idTextField.getText(), util.Utility.passwordGenerator(), "Patient");
                            //Si no contiene el mismo id del doctor o algo similar
                            if (!patients.contains(p)) {
                                patients.add(p);
                                util.Utility.setCircularLinkedList(patients);
                                addArchive(p, "patients");
                                addArchive(s, "users");
                                mail.sendMail(p.getEmail(), p.getLastname() + ", " + p.getFirstname(), util.Utility.message("Patient", s.getUser(), s.getPassword(), "", ""));
                                btnCleanOnAction(event);
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Patient - Add");
                                alert.setContentText("Element add succesfully");
                                alert.show();
                                loadPage(getClass().getResource("FXMLPatients.fxml"), patientPane);
                            } else {
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Patients - Add");
                                alert.setContentText("Element is repeated");
                                alert.show();
                            }
                        } else {//Validacion para llenar el id y que sean 9 digitos
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Patient - Update");
                            alert.setContentText("Fill the id space correctly" + "\n Ej. 123456789");
                            alert.show();

                        }
                    } else {//Validacion del correo para que sea un correo
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Patient - Update");
                        alert.setContentText("Fill the email space correctly" + "\n Ej. example@domain.com");
                        alert.show();

                    }

                } else {//Revisa que no esten vacios los espacios
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Patients - Add");
                    alert.setContentText("Fill ALL the blank spaces");
                    alert.show();

                }
            } catch (NumberFormatException nfe) {//La validacion si insertan una letra
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patients - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();

            } catch (ListException ex) {//Catch clauses
                Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FXMLAddPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //Para añadir el primer dato
                if (!idTextField.getText().equals("") && !emailTextField.getText().equals("") && !addressTextField.getText().equals("")
                        && !firstNTextField.getText().equals("") && !lastNTextField.getText().equals("") && !calendarChoice.equals("")) {
                    if (util.Utility.emailValidation(emailTextField.getText())) {
                        if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                            Calendar date = Calendar.getInstance();
                            date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue() - 1, calendarChoice.getValue().getDayOfMonth());
                            Patient p = new Patient(Integer.parseInt(idTextField.getText()), lastNTextField.getText(), firstNTextField.getText(), date.getTime(), emailTextField.getText(), addressTextField.getText());
                            //Algoritmo para genera una contraseña
                            Security s = new Security(idTextField.getText(), util.Utility.passwordGenerator(), "Patient");
                            patients.add(p);
                            util.Utility.setCircularLinkedList(patients);
                            addArchive(p, "patients");
                            addArchive(s, "users");
                            mail.sendMail(p.getEmail(), p.getLastname() + ", " + p.getFirstname(), util.Utility.message("Patient", s.getUser(), s.getPassword(), "", ""));
                            btnCleanOnAction(event);
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Patients - Add");
                            alert.setContentText("Element add succesfully");
                            alert.show();
                            loadPage(getClass().getResource("FXMLPatients.fxml"), patientPane);
                        } else {//Validacion de cedula que sean 9 digitos
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Patient - Update");
                            alert.setContentText("Fill the id space correctly" + "\n Ej. 123456789");
                            alert.show();
                        }
                    } else {//Validacion que sean un correo para que lo envie
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Patient - Update");
                        alert.setContentText("Fill the email space correctly" + "\n Ej. example@domain.com");
                        alert.show();

                    }
                } else {//Vlaidacion para los espacios en blanco, para que se llene todos
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Patients - Add");
                    alert.setContentText("Fill the blank spaces");
                    alert.show();

                }
            } catch (NumberFormatException nfe) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patients - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            } catch (Exception ex) {
                Logger.getLogger(FXMLAddPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        //Llama el stage a traves de boton y cierra la pagina
        Stage mystage = (Stage) btnClose.getScene().getWindow();
        mystage.close();
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        //Borra todos los espacion
        this.idTextField.setText("");
        this.firstNTextField.setText("");
        this.lastNTextField.setText("");
        this.calendarChoice.getEditor().clear();
        this.emailTextField.setText("");
        this.addressTextField.setText("");

    }

    private void addArchive(Object o, String path) {
        //Añade lo ques e encesite a los archivos correspoendiente spor medico del path
        //Le dice que lea el archivo
        BufferedReader br = archives.getBufferedReader(path);
        //Le dice que escriba
        PrintStream ps = archives.getPrintStream(true, path);
        try {
            String lineRegister = "";
            while (lineRegister != null) {
                
                lineRegister = br.readLine();
                //Cunado esta nulo escribe
                if (lineRegister == null) {
                    ps.println(o);
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
