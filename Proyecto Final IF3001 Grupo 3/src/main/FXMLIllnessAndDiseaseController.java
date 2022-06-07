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
import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Proyecto Final
 */
public class FXMLIllnessAndDiseaseController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
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
    
        //Si no esta vacia
        if(!util.Utility.getSinglyLinkedList().isEmpty()){
            //carga la lista utility, que añadio
            this.illness = util.Utility.getSinglyLinkedList();
        }else{//si esta vacia por primera vez entonces
            //Llama al metodo que carga el archivo
           this.illness = getIllness();
        }
        
      //  getIllness();
        this.idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        this.descriptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(1));//tome los valores que estan en el indice 0
            }
        });
          
        //Cargar los datos en una tableView
        if (illness!=null && !illness.isEmpty()) {
            this.patientsTableView.setItems(getData());
        }  

    }

    //Metodo para borrar todo el bp y que carge la otra pagina
      public static void loadPage(URL ui, BorderPane bp){
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

    //Agregar
    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        loadPage(getClass().getResource("FXMLAddIllnessAndDisease.fxml"),bp);
    }
    
    //Leer
    @FXML
    private void btnReadOnAction(ActionEvent event) {
    }

    //Modificar
    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    //Eliminar
    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        TextInputDialog delete = new TextInputDialog("Element for delete");
        delete.setTitle("");
        delete.showAndWait();
        
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to remove this element?");
        alert.showAndWait();
        
        if(alert.getResult().getText().equalsIgnoreCase("aceptar")){
            try {
                illness.remove(new Sickness(Integer.parseInt(delete.getResult()), ""));
                util.Utility.setSinglyLinkedList(illness);
                this.patientsTableView.setItems(getData());
            } catch (ListException ex) {
                Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch(NumberFormatException nfe){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illness - Add");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            }
            removeArchive(Integer.parseInt(delete.getResult()));
        }

    }
    
    //El getData para la tableView
     private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= illness.size(); i++) {
                Sickness s = (Sickness) illness.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                
                arrayList.add(String.valueOf(s.getIdentity()));
                arrayList.add(s.getDescription());
               
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
       
          return data; 
        
     }
     
     //Metodo para leer los archivos con listas
    private SinglyLinkedList getIllness() {
        SinglyLinkedList list = util.Utility.getSinglyLinkedList();
        BufferedReader br = archives.getBufferedReader("illness");
        File file = new File("illness.txt");
       
        try {
            //Revisa si el archivo existe
           if(file.exists()){
             String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String sick ="";
                    int id = 0;
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister,";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        if(controlTokens == 1){
                            id = Integer.parseInt(sT.nextToken());
                        }else if(controlTokens == 2){
                            sick = sT.nextToken();
                        }
                        controlTokens++;
                    }//End while   
                    
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(list.isEmpty()){
                        list.add(new Sickness(id, sick));
                        lineRegister = br.readLine();
                    }else if(!list.contains(new Sickness(id, sick))){
                        list.add(new Sickness(id, sick));
                        lineRegister = br.readLine();
                    }
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setSinglyLinkedList(list);
           }else{
               //Sino existe se crea uno
               file.createNewFile();
           }
   
        }catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return list;
        }

    //Remover las variables de un archivo
    private void removeArchive(int result) {
        //Llamo losd emtods BuffereRedar Y printStream
        BufferedReader br = archives.getBufferedReader("illness");
        PrintStream ps = archives.getPrintStream(true, "illness");
           try {
             String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    //El token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister,";");
                   
                    //Para separar los tokens
                    id = Integer.parseInt(sT.nextToken());
                    //Reviso si el id es el mismo que el result
                        if(id == result){
                            //Elimino la linea del archivo
                            ps.print(lineRegister);
                        }
                    
                  
                }
           
        }catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }

  }
