/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Security;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLLoginController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
    private Alert alert;
    SinglyLinkedList users;

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
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //carga los datos de la lista con el archivo
        this.users = getUsers();
        //Pone los items en el login
        cbLogin.setItems(ol);

    }

    ObservableList<String> ol = FXCollections.observableArrayList("Administrator", "Patient","Doctor");

    //Loader para cargar la otra pagina
    //solo se manda el url no el border pane por que lo qeu se necesita es borrarlo
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
            //llama a la ventana login para cerrarla
            Stage mystage = (Stage) this.btnLog.getScene().getWindow();
            mystage.close();//cierra la pantalla del login

        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
    }

    @FXML
    private void btnLogOnAction(ActionEvent event) {
        //Añadir la validadcion de la contraseña y el metodo de encriptacion y descencriptacion
        // Validar si la contraseña es 
        try {
            //revisa si no estan en blanco
        if (cbLogin.getValue() != null && !txtUser.getText().equals("") && !txtPassword.getText().equals("")) {

            //revisa si la lista contiene ese usuario
            if(users.contains(new Security(txtUser.getText(), txtPassword.getText(), cbLogin.getValue()))) {
                int index = users.indexOf(new Security(txtUser.getText(), txtPassword.getText(), cbLogin.getValue()));
                util.Utility.setUser(index);
                    //dependiendo si es paciente o adminstrador abre diferentes menus
                if(cbLogin.getValue().equalsIgnoreCase("Administrator")){//carga las paginas dependiendo si es administrador o paciente
                      loadPage(getClass().getResource("FXMLMainMenu.fxml"));
                } else if(cbLogin.getValue().equalsIgnoreCase("Patient")){
                     loadPage(getClass().getResource("FXMLMainMenuPatient.fxml"));
                } else if(cbLogin.getValue().equalsIgnoreCase("Doctor")){
                     loadPage(getClass().getResource("FXMLMainMenuDoctor.fxml"));
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log Action");
                alert.setHeaderText("Login..");
                alert.setContentText("Incorrect user or password, try again.");
                alert.show();
            }
      
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Log Action");
            alert.setHeaderText("Login..");
            alert.setContentText("Fill All the Spaces");
            alert.show();
            }
        } catch (ListException ex) {
             //Si esta vacia tira la alerta
             alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Log Action");
            alert.setHeaderText("Login..");
            alert.setContentText("Doesn´t found anything, is empty");
            alert.show();
        }

    }

    private SinglyLinkedList getUsers() {
        SinglyLinkedList list = util.Utility.getSinglyLinkedListPassword();
        BufferedReader br = archives.getBufferedReader("users");
        File file = new File("users.txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    String user = "";
                    String password = "";
                    String type = "";

                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;
                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                user = sT.nextToken();
                                controlTokens++;
                                break;
                            case 2:
                                password = sT.nextToken();
                                controlTokens++;
                                break;
                            case 3:
                                type = sT.nextToken();
                                controlTokens++;
                                break;
                            default:
                                break;
                        }
                    }//End while   
      
                    Security sec  = new Security(user, password, type);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(lineRegister != null){
                        list.add(sec);
                    }
                    lineRegister = br.readLine();
                   
                }
                //Se pone aqui para que se carge en el addList
                //pone la lista en el set de utility
                util.Utility.setSinglyLinkedListPassword(list);
            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
