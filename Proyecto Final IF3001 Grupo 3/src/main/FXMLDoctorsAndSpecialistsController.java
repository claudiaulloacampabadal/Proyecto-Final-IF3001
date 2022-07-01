/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Doctor;
import domain.TDA.DoublyLinkedList;
import domain.TDA.ListException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLDoctorsAndSpecialistsController implements Initializable {
    ArchiveTXT archives = new ArchiveTXT();
    Alert alert;
    private DoublyLinkedList doctors;
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
    private TableColumn<List<String>, String> firstNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> lastNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> birthdayTableColumn;
    @FXML
    private TableColumn<List<String>, String> emailTableColumn;
    @FXML
    private TableColumn<List<String>, String> phoneTableColumn;
    @FXML
    private TableColumn<List<String>, String> addressTableColumn;
    @FXML
    private TableView<List<String>> doctorTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Si no esta vacia
        if(!util.Utility.getDoublyLinkedList().isEmpty()){
            //carga la lista utility, que añadio
            this.doctors = util.Utility.getDoublyLinkedList();
        }else{//si esta vacia por primera vez entonces
            //Llama al metodo que carga el archivo
            this.doctors = getDoctors();
        }
        
        this.idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        this.firstNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(1));//tome los valores que estan en el indice 0
            }
        });
         this.lastNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(2));//tome los valores que estan en el indice 0
            }
        });
        this.phoneTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));//tome los valores que estan en el indice 0
            }
        });
         this.birthdayTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(4));//tome los valores que estan en el indice 0
            }
        });
        this.addressTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(5));//tome los valores que estan en el indice 0
            }
        });
        this.emailTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(6));//tome los valores que estan en el indice 0
            }
        });
          
        //Cargar los datos en una tableView
        if (doctors!=null && !doctors.isEmpty()) {
            this.doctorTableView.setItems(getData());
        }  
       
    }   
    //Metodo para hacer una ventana
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
        util.Utility.setBorderPaneDoctor(bp);
        loadPage(getClass().getResource("FXMLAddDoctor.fxml"));
    }

    @FXML
    private void btnReadOnAction(ActionEvent event) {
          //Leer segun el id
        TextInputDialog read = new TextInputDialog();
        read.setTitle("Doctors AND Specialist");
        read.setHeaderText("Enter the ID of the element to read");
        read.showAndWait();
        try {
            Doctor d = new Doctor(Integer.parseInt(read.getResult()), "", "", null, "", "", "");
            //Reivsa si contiene el id del doctor
            if(doctors.contains(d)){
                int index = doctors.indexOf(d);
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Doctor AND Specialist - Read");
               alert.setHeaderText("The element is...");
               alert.setContentText(String.valueOf(doctors.getNode(index).data));
               alert.show();
                
            }else{//SIno le dice que no existe
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Doctors - Read");
               alert.setContentText("Element doesn't exist");
               alert.show();
                
            }
       } catch (ListException ex) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Doctor AND Specialist - Read");
            alert.setContentText("List is Empty!");
            alert.show();
            
      } catch(NumberFormatException nfe){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Doctor AND Specialist - Read");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
            }
    }
    

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        if(!doctors.isEmpty()){
          util.Utility.setBorderPaneDoctor(bp);
          loadPage(getClass().getResource("FXMLModifyDoctor.fxml"));
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
         TextInputDialog delete = new TextInputDialog();
        delete.setTitle("Remove - Doctors AND Specialists");
        delete.setHeaderText("Enter the element to remove: ");
        delete.showAndWait();
        
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to remove this element?");
        alert.showAndWait();
        
        if(alert.getResult().getText().equalsIgnoreCase("aceptar")){
            Doctor d = new Doctor(Integer.parseInt(delete.getResult()), "", "", null, "", "", "");
            try {
               doctors.remove(d);
               util.Utility.setDoublyLinkedList(doctors);
                if(!doctors.isEmpty()){
                    this.doctorTableView.setItems(getData());
                }else{
                    this.doctorTableView.getItems().clear();
               }
            } catch (ListException ex) {
                Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch(NumberFormatException nfe){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Doctor AND Specialist - Remove");
                alert.setContentText("Invalid character, try a number.");
                alert.show();
            }
            removeArchive(delete.getResult(),"doctors");
             removeArchive(delete.getResult(),"users");
        }
    }
 
    
      //El getData para la tableView
     private ObservableList<List<String>> getData(){
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for(int i = 1; i <= doctors.size(); i++) {
                Doctor d = (Doctor) doctors.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                
                arrayList.add(String.valueOf(d.getId()));
                arrayList.add(d.getFirstname());
                arrayList.add(d.getLastname());
                arrayList.add(d.getPhoneNumber());
                arrayList.add(util.Utility.format(d.getBirthday()));
                arrayList.add(d.getAdress());
                arrayList.add(d.getEmail());
               
                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
          return data;  
     }
    
     //Para obetner los datos dela rchivo y cargarlos alas listas
    private DoublyLinkedList getDoctors() {
    
        DoublyLinkedList list = util.Utility.getDoublyLinkedList();
        BufferedReader br = archives.getBufferedReader("doctors");
                 
        File file = new File("doctors.txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    String fName = "";
                    String lstName = "";
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthday = null;
                    String phone ="";
                    String email = "";
                    String address = "";
                    //EL token es;
                    StringTokenizer sT = new StringTokenizer(lineRegister, ";");
                    int controlTokens = 1;

                    //Para separar los tokens
                    while (sT.hasMoreTokens()) {
                        switch (controlTokens) {
                            case 1:
                                id = Integer.parseInt(sT.nextToken());
                                controlTokens++;//Separa el id
                                break;
                            case 2:
                                lstName = sT.nextToken();
                                controlTokens++;//El Apellido
                                break;
                            case 3:
                                fName = sT.nextToken();
                                controlTokens++;//EL nombre
                                break;
                            case 4:
                                //COnvierte el brithday de formato a un DATE
                                birthday = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                phone = sT.nextToken();
                                controlTokens++;//EL numero de telefono
                                break;
                            case 6:
                                email = sT.nextToken();
                                controlTokens++;///El email
                                break;
                            case 7:
                                address = sT.nextToken();
                                controlTokens++;//La direccion
                                break;
                        }
                    }//End while   
                     Doctor d = new Doctor(id, lstName, fName, birthday, phone,email, address);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    
                    if(lineRegister != null){
                        list.add(d);
                    }
                        lineRegister = br.readLine();
                 
                    
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setDoublyLinkedList(list);

            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    private void removeArchive(String lineToRemove, String path) {
      try {
 
        File file = new File(path+".txt");
 
        if (!file.isFile()) {
            return;
        }
 
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(file.getAbsolutePath()+".tmp");
 
        BufferedReader br = archives.getBufferedReader(path);
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
        if (!tempFile.renameTo(new File(path+".txt"))){
            System.out.println("Could not rename file");
 
        }
    } catch (FileNotFoundException ex) {
    } catch (IOException ex) {
    }
        
   }
    

    
}

