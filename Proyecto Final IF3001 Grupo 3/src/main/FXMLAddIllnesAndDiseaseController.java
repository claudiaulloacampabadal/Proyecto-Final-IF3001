/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Sickness;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAddIllnesAndDiseaseController implements Initializable {

    SinglyLinkedList illness;
    Alert alert;
    ArchiveTXT archive;
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

    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        if (illness != null && !illness.isEmpty()) {
            try {
                if (idTextField.getText().equals("") || tF_Description.getText().equals("")) {
                    illness.add(new Sickness(Integer.parseInt(idTextField.getText()), tF_Description.getText()));
                    util.Utility.setSinglyLinkedList(illness);
                    setIllness(new Sickness(Integer.parseInt(idTextField.getText()), tF_Description.getText()));
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Illness - Add");
                    alert.setContentText("Fill ALL the blank spaces.");
                    alert.show();
                }

            } catch (NumberFormatException ex) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illness - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            }
        }

    }

    @FXML
    private void bntCloseOnAction(ActionEvent event) {
        System.exit(-1);
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        idTextField.setText("");
        tF_Description.setText("");
    }

    private void setIllness(Sickness sickness) {
        BufferedReader br = archive.getBufferedReader("illness");
        PrintStream ps = archive.getPrintStream(true, "illness");

        String lineRegister;
        try {
            lineRegister = br.readLine();

            while (lineRegister != null) {

                lineRegister = br.readLine();

                if (lineRegister != null) {
                    ps.println(sickness.toString());
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLAddIllnesAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
