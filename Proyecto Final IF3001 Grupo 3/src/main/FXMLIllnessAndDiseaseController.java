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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
    private TableColumn<List<String>, String> idTableColumn;
    @FXML
    private TableColumn<List<String>, String> descriptionTableColumn;
    @FXML
    private TableView<List<String>> illnessTableView;

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
            this.illnessTableView.setItems(getData());
        }  

    }

    public static void loadPage(URL ui) {

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
            //llama a la ventana login para cerrarla
     

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    }

    
    //Agregar
    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        util.Utility.setBorderPaneIllness(bp);
        loadPage(getClass().getResource("FXMLAddIllnessAndDisease.fxml"));
    }
   
    
    //Leer
    @FXML
    private void btnReadOnAction(ActionEvent event) {
         //Leer segun el id
        TextInputDialog update = new TextInputDialog();
        update.setTitle("Illness And Disease");
        update.setHeaderText("Enter the ID of the element to read");
        update.showAndWait();
        try {
            Sickness s = new Sickness(Integer.parseInt(update.getResult()), "");
            if(illness.contains(s)){
                int index = illness.indexOf(s);
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Illness - Read");
               alert.setHeaderText("The element is...");
               alert.setContentText(String.valueOf(illness.getNode(index).data));
               alert.show();
                
            }else{
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Illness - Read");
               alert.setContentText("Element doesn't exist");
               alert.show();
                
            }
       } catch (ListException ex) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Update");
            alert.setContentText("List is Empty!");
            alert.show();
            
      } catch(NumberFormatException nfe){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Read");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
            }
        
    }

    //Modificar
    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
       util.Utility.setBorderPaneIllness(bp);
       loadPage(getClass().getResource("FXMLModifyIllnessAndDisease.fxml"));
    }

    //Eliminar
    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        //Prgunta cual es el id que se quiere remover
        TextInputDialog delete = new TextInputDialog();
        delete.setTitle("Remove-Illness");
        delete.setHeaderText("Enter the ID to remove");
        delete.showAndWait();
        //Se pregunta si de verdad quiere remover el archivo
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to remove this element?");
        alert.showAndWait();
        
        if(alert.getResult().getText().equalsIgnoreCase("aceptar")){
            try {
                //Se remueve de la lista
                illness.remove(new Sickness(Integer.parseInt(delete.getResult()), ""));
                util.Utility.setSinglyLinkedList(illness);
                //Si esta vacia solo se limpia la lista sino se vueleve a llamar la tabla
                if(!illness.isEmpty()){
                    this.illnessTableView.setItems(getData());
                }else{
                    this.illnessTableView.getItems().clear();
               }
            } catch (ListException ex) {
                Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch(NumberFormatException nfe){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illness - Remove");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            }
            //Se ejecuta el metodo remueve archivo
            //Se manda el id
            removeArchive(delete.getResult());
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
    //Se remueve por id
    private void removeArchive(String lineToRemove) {
      try {
 
        File file = new File("illness.txt");
 
        if (!file.isFile()) {
            return;
        }
 
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = archives.getBufferedReader("illness");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
 
        String line = "";
        //Read from the original file and write to the new
        //unless content matches data to be removed.
        while (line != null) {
            //El token es;
            line = br.readLine();
            if(line != null){
                StringTokenizer sT = new StringTokenizer(line,";");
                //Para separar los tokens
                String id = sT.nextToken();
                      //Reviso que id no sea el mismo que el "line to remove"
                if (!id.equalsIgnoreCase(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            }
        pw.close();
        br.close();
        //Delete the original file
        if (!file.delete()) {
            System.out.println("Could not delete file");
            return;
        }
 
        //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(new File("illness.txt"))){
            System.out.println("Could not rename file");
 
        }
    } catch (FileNotFoundException ex) {
    } catch (IOException ex) {
    }
        
   }
    

  }
