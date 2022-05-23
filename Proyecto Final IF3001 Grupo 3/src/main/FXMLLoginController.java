/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeDebug;
import static main.FXMLMainMenuController.loadPage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label lbClinica;
    @FXML
    private ComboBox<String> cbLogin;
    @FXML
    private Button btnLog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        cbLogin.setItems(ol);
        
    }  
    
    ObservableList<String> ol = FXCollections.observableArrayList("Administrator","Patient");

    
    //Loader para cargar la otra pagina
    //solo se manda el url no el border pane por que lo qeu se necesita es borrarlo
        public void loadPage(URL ui){
     
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
             Stage mystage = (Stage) this.btnLog.getScene().getWindow();
             mystage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    
    }
    
    @FXML
    private void btnLogOnAction(ActionEvent event) {
        
        
        if(cbLogin.getValue().equalsIgnoreCase("Administrator")){//carga las paginas dependiendo si es administrador o paciente
            loadPage(getClass().getResource("FXMLMainMenu.fxml"));
            
        }else if(cbLogin.getValue().equalsIgnoreCase("Patient")){
            loadPage(getClass().getResource("FXMLMainMenuPatient.fxml"));
            
        }
        
    }
    
}
