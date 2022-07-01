/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Appointment;
import domain.MedicalCare;
import domain.Security;
import domain.TDA.BTree;
import domain.TDA.BTreeNode;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLHistoricalMedicalCareController implements Initializable {

    ObservableList<List<String>> data;
    private BTree medicalCare;
    private SinglyLinkedList users;
    @FXML
    private BorderPane bp;
    @FXML
    private Button btnClose;

    @FXML
    private TableColumn<List<String>, String> idTableColumn;
    @FXML
    private TableColumn<List<String>, String> idPatientTableColumn;
    @FXML
    private TableColumn<List<String>, String> dateTimeTableColumn;
    @FXML
    private TableColumn<List<String>, String> sickTableColumn;
    @FXML
    private TableColumn<List<String>, String> annotationsTableColumn;
    @FXML
    private TableView<List<String>> historialTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
         this.medicalCare = util.Utility.getBTree();
        this.users = util.Utility.getSinglyLinkedListPassword();
        this.data = FXCollections.observableArrayList();
        
        this.idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        this.idPatientTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(1));//tome los valores que estan en el indice 0
            }
        });
         this.dateTimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(2));//tome los valores que estan en el indice 0
            }
        });
        this.sickTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));//tome los valores que estan en el indice 0
            }
        });
         this.annotationsTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(4));//tome los valores que estan en el indice 0
            }
        });
          
        //Cargar los datos en una tableView
        if (medicalCare!=null && !medicalCare.isEmpty()) {
            this.historialTableView.setItems(getData(medicalCare.getRoot()));
        }  
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage mystage = (Stage) btnClose.getScene().getWindow();
        mystage.close();
    }
    
      //El getData para la tableView
     private ObservableList<List<String>> getData(BTreeNode node){
        //recordar agregar los datos
        while(node!= null){
            try {
                Security s = (Security) users.getNode(util.Utility.getUser()).data;
                MedicalCare mc = (MedicalCare) node.data;
                if(s.getUser().equals(String.valueOf(mc.getDoctorID()))){
                   if(util.Utility.getPatientId().equals(String.valueOf(mc.getPatientID()))){ 
                     List<String> arrayList = new ArrayList<>();
                     arrayList.add(String.valueOf(mc.getIdentity()));
                     arrayList.add(String.valueOf(mc.getPatientID()));
                     arrayList.add(util.Utility.formatDateTime(mc.getDateTime()));
                     arrayList.add(String.valueOf(mc.getSicknessID()));
                     arrayList.add(mc.getAnnotations());
                     
                      data = getData(node.right);
                      data = getData(node.left);
                   }
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLHistoricalMedicalCareController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
       return data; 
       
     }
    
}
