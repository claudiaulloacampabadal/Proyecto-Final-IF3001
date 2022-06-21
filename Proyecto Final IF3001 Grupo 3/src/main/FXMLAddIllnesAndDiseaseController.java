/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Sickness;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLMainMenuController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddIllnesAndDiseaseController implements Initializable {

    SinglyLinkedList illness;
    ArchiveTXT archives = new ArchiveTXT();
    private Alert alert;
    BorderPane illnessPane;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField tF_Description;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.illness = util.Utility.getSinglyLinkedList();
        this.illnessPane = util.Utility.getBorderPaneIllness();
    }
    //Metodo para borrar todo el bp y que carge la otra pagina   
  
    public static void loadPage(URL ui, BorderPane bp) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(ui);//carga el url
             bp.setCenter(null); 
             bp.setRight(null);
             bp.setLeft(null);
             bp.setTop(null);
             bp.setBottom(null);
    
            bp.setCenter(root);
            
            //llama a la ventana login para cerrarla
            //cierra la pantalla del login

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    }

    
    @FXML
    private void btnAddOnAction(ActionEvent event) {
        if (!illness.isEmpty() && illness != null) {
            try {
                if (!idTextField.getText().equals("") && !tF_Description.getText().equals("")) {
                    Sickness s = new Sickness(Integer.parseInt(idTextField.getText()), tF_Description.getText());
                    if (!illness.contains(s)) {
                        illness.add(s);
                        util.Utility.setSinglyLinkedList(illness);
                        addArchive(s);
                        btnCleanOnAction(event);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Illness - Add");
                        alert.setContentText("Element add succesfully");
                        alert.show();
                           loadPage(getClass().getResource("FXMLIllnessAndDisease.fxml"),illnessPane);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Illness - Add");
                        alert.setContentText("Element is repeated");
                        alert.show();

                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Illness - Add");
                    alert.setContentText("Fill the blank spaces");
                    alert.show();

                }
            } catch (NumberFormatException nfe) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illness - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();

            } catch (ListException ex) {
                Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //Para añadir el primer dato
                if (!idTextField.getText().equals("") && !tF_Description.getText().equals("")) {
                    Sickness s = new Sickness(Integer.parseInt(idTextField.getText()), tF_Description.getText());
                    illness.add(s);
                    util.Utility.setSinglyLinkedList(illness);
                    addArchive(s);
                    btnCleanOnAction(event);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Illness - Add");
                    alert.setContentText("Element add succesfully");
                    alert.show();
                         loadPage(getClass().getResource("FXMLIllnessAndDisease.fxml"),illnessPane);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Illness - Add");
                    alert.setContentText("Fill the blank spaces");
                    alert.show();

                }
            } catch (NumberFormatException nfe) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illness - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            }
        }
    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        Stage mystage = (Stage) btnClose.getScene().getWindow();
            mystage.close();
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        idTextField.setText("");
        tF_Description.setText("");
    }

    private void addArchive(Sickness s) {
        BufferedReader br = archives.getBufferedReader("illness");
        PrintStream ps = archives.getPrintStream(true, "illness");
        try {
            String lineRegister = "";
            while (lineRegister != null) {

                lineRegister = br.readLine();

                if (lineRegister != null) {

                } else {
                    ps.println(s.toString());
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
