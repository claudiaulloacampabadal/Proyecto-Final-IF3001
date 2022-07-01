/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Appointment;
import domain.Archives.ArchiveTXT;
import domain.Security;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLAppointmentController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
    private DoublyLinkedList appointments;
    private SinglyLinkedList users;
    Alert alert;
    
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
    private TableColumn<List<String>, String> idPatientTableColumn;
    @FXML
    private TableColumn<List<String>, String> idDoctorTableColumn;
    @FXML
    private TableColumn<List<String>, String> dateTimeTableColumn;
    @FXML
    private TableColumn<List<String>, String> remarksTableColumn;
    @FXML
    private TableView<List<String>> appointmentTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Si no esta vacia
        if(!util.Utility.getDoublyLinkedListAppointment().isEmpty()){
            //carga la lista utility, que añadio
            this.appointments = util.Utility.getDoublyLinkedListAppointment();
        }else{//si esta vacia por primera vez entonces
            //Llama al metodo que carga el archivo
            this.appointments = getAppointment();
             util.Utility.setDoublyLinkedListAppointment(appointments);
        }
        this.users = util.Utility.getSinglyLinkedListPassword();
        
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
         this.idDoctorTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(2));//tome los valores que estan en el indice 0
            }
        });
        this.dateTimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));//tome los valores que estan en el indice 0
            }
        });
         this.remarksTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(4));//tome los valores que estan en el indice 0
            }
        });
       
        //Cargar los datos en una tableView
        if (appointments!=null && !appointments.isEmpty()) {
            this.appointmentTableView.setItems(getData());
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
     
    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        util.Utility.setBorderPaneAppointment(bp);
         loadPage(getClass().getResource("FXMLAddAppointment.fxml"));
    }

    @FXML
    private void btnReadOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        util.Utility.setBorderPaneAppointment(bp);
        loadPage(getClass().getResource("FXMLModifyAppointment.fxml"));
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
    }
    
      //El getData para la tableView
    private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= appointments.size(); i++) {
                Appointment ap = (Appointment) appointments.getNode(i).data;
                Security s = (Security) users.getNode(util.Utility.getUser()).data;
                
                List<String> arrayList = new ArrayList<>();
                
                if(s.getUser().equalsIgnoreCase(String.valueOf(ap.getPatientID()))){
                    arrayList.add(String.valueOf(ap.getIdentity()));
                    arrayList.add(String.valueOf(ap.getPatientID()));
                    arrayList.add(String.valueOf(ap.getDoctorID()));
                    arrayList.add(util.Utility.formatDateTime(ap.getDateTime()));
                    arrayList.add(ap.getRemarks());
               
                    data.add(arrayList);
             }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
      //Para obetner los datos dela rchivo y cargarlos alas listas
    private DoublyLinkedList getAppointment() {
        DoublyLinkedList list = util.Utility.getDoublyLinkedListAppointment();
        BufferedReader br = archives.getBufferedReader("appointment");
        File file = new File("appointment.txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {
                    int id = 0;
                    int idPatient = 0;
                    int idDoctor = 0;
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime dateTime = null; 
                    String remarks ="";
 
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                id = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del Paciente
                                break;
                            case 2:
                                idPatient = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del Paciente
                                break;
                            case 3:
                                idDoctor = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del doctor
                                break;
                            case 4:
                                //Convierte de String a local date time
                                dateTime = LocalDateTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 5:
                                remarks = sT.nextToken();
                                controlTokens++;//Remarks 
                                break;
                        }
                    }//End while   
                     Appointment ap = new Appointment(id, idPatient, idDoctor, dateTime, remarks);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if(lineRegister != null){
                        list.add(ap);
                    }
                        lineRegister = br.readLine();
                 
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setDoublyLinkedListAppointment(list);

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
