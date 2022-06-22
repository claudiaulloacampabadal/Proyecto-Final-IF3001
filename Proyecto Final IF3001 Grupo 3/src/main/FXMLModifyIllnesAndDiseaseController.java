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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static main.FXMLAddIllnesAndDiseaseController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLModifyIllnesAndDiseaseController implements Initializable {

     SinglyLinkedList illness;
    ArchiveTXT archives = new ArchiveTXT();
    private Alert alert;
    BorderPane illnessPane;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField tF_Description;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClean;
    @FXML
    private ComboBox<List<String>> cB_Id;
    @FXML
    private Button btnUpdate;

    /**
     * Initializes the controller class.
     */
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.illness = util.Utility.getSinglyLinkedList();
        this.illnessPane = util.Utility.getBorderPaneIllness();
        cB_Id.getItems().addAll(getData());
    } 
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
    private void bntCloseOnAction(ActionEvent event) {
          Stage mystage = (Stage) btnClose.getScene().getWindow();
            mystage.close();
    }

    @FXML
    private void btnCleanOnAction(ActionEvent event) {
        String id = String.valueOf(cB_Id.getValue()).replace("[","").replace("]", "");
         System.out.println(Integer.parseInt(id));
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
         if(!illness.isEmpty()){
            try {
                  String id = String.valueOf(cB_Id.getValue()).replace("[","").replace("]", "");
                if(!id.equals("") && !tF_Description.getText().equals("")){
                    Sickness s = new Sickness(Integer.parseInt(id), "");
                    if(illness.contains(s)){
                        //Buscar donde esta
                        updateList(s,tF_Description.getText());
                        modifyArchive(id, tF_Description.getText());
                       alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Illness - Update");
                       alert.setContentText("Element update, succesfully!");
                       alert.show();
                        loadPage(getClass().getResource("FXMLIllnessAndDisease.fxml"),illnessPane);
                    }else{
                       alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Illness - Update");
                       alert.setContentText("Element doesn't exist, try again");
                       alert.show();
                    }
                }else{
                     alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Illness - Update");
                    alert.setContentText("Fill all the blank spaces");
                    alert.show();
                }
     } catch (ListException ex) {
             Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
      } catch(NumberFormatException nfe){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Update");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
            }
            
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illness - Update");
            alert.setContentText("List is Empty!");
            alert.show();
            
        }
    }
    private void updateList(Object o, String t){
        try {
            SinglyLinkedList list = new SinglyLinkedList();
            Sickness s = (Sickness) o;
            for (int i = 1; i <= illness.size(); i++) {
                if(util.Utility.equals(s, illness.getNode(i).data)){
                    list.add(new Sickness(s.getIdentity(), t));
                }else{
                   list.add(illness.getNode(i).data);
              }
            }
            illness.clear();
            for (int i = 1; i <= list.size(); i++) {
               illness.add(list.getNode(i).data);
            }
            util.Utility.setSinglyLinkedList(illness);
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    *param lineToModify
    *param Description
    */
     private void modifyArchive(String lineToModify, String description) {
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
                if (!id.equalsIgnoreCase(lineToModify)) {
                    pw.println(line);
                    pw.flush();
                }else{
                    pw.println(lineToModify+";"+description);
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
    
    private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= illness.size(); i++) {
                Sickness s = (Sickness) illness.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                
                arrayList.add(String.valueOf(s.getIdentity()));
               
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
}
