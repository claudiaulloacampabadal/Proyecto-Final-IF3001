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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLIllnessAndDiseaseController implements Initializable {

    ArchiveTXT archives;
    private SinglyLinkedList illness;
    private Alert alert;

    @FXML
    private BorderPane bp;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnRead;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<List<String>> patientsTableView;
    @FXML
    private TableColumn<List<String>, String> idTableColumn;
    @FXML
    private TableColumn<List<String>, String> descriptionTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //  getIllness();
        
        this.illness = util.Utility.getSinglyLinkedList();
        util.Utility.setSinglyLinkedList(illness);
        this.idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        this.idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        
            if (illness!=null && !illness.isEmpty()) {
            this.patientsTableView.setItems(getData());
 
        }  

    }

    public void loadPage(URL ui) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(ui);//carga el url

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            //crea un nuevo stage para que parezca sin el login
            stage.setScene(scene);
            stage.setTitle("Proyecto Final Gr3 - 2022");
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }

    }

    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLAddIllnessAndDisease.fxml"));
    }

    @FXML
    private void btnReadOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {

    }
     private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();
      
         

        try {
            for(int i = 1; i < util.Utility.getSinglyLinkedList().size(); i++) {
                Sickness s = (Sickness) util.Utility.getSinglyLinkedList().getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                
                arrayList.add(String.valueOf(s.getIdentity()));
                arrayList.add(s.getDescription());//Comparo se le elmentos 1= Low 2 = Medium 3 = High para que aprezacn en la tabla
               
                data.add(arrayList);
                //Se hace un metodo que me retorne el priority del elemento que esta
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    
          
          return data; 
        
     }
     
    public void getIllness() {
        illness = util.Utility.getSinglyLinkedList();
        BufferedReader br = archives.getBufferedReader("illness");
       
        try {
             String lineRegister = br.readLine();

                while (lineRegister != null) {

                    String  sick = "";
                    int id = 0;
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister,";");
                    int controlTokens = 0;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        if (controlTokens == 0) {
                            id = Integer.parseInt(sT.nextToken());//Guardamos el 1
                        } else if (controlTokens == 1) {
                            sick = sT.nextToken();
                        }
                        controlTokens++;
                    }//End while
                    
                    illness.add(new Sickness(id, sick));
                    util.Utility.setSinglyLinkedList(illness);
                    lineRegister = br.readLine();

                }
       
        }catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        }

    }
