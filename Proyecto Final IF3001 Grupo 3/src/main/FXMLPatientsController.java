/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.MailMessage;
import domain.Patient;
import domain.Sickness;
import domain.TDA.CircularLinkedList;
import domain.TDA.ListException;
import domain.TDA.SinglyLinkedList;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javax.mail.MessagingException;
import static main.FXMLMainMenuController.loadPage;

/**
 * FXML Controller class
 *
 * @author Maria Celeste
 */
public class FXMLPatientsController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
    private CircularLinkedList patients;
    @FXML
    private BorderPane bp;
    @FXML
    private TableView<List<String>> patientsTableView;
    @FXML
    private TableColumn<List<String>, String> idTableColumn;
    @FXML
    private TableColumn<List<String>, String> emailTableColumn;
    @FXML
    private TableColumn<List<String>, String> firstNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> lastNameTableColumn;
    @FXML
    private TableColumn<List<String>, String> birthdayTableColumn;
    @FXML
    private TableColumn<List<String>, String> addressTableColumn;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnRead;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Si no esta vacia
        if (!util.Utility.getCircularLinkedList().isEmpty()) {
            //carga la lista utility, que añadio
            this.patients = util.Utility.getCircularLinkedList();
        } else {//si esta vacia por primera vez entonces
            //Llama al metodo que carga el archivo
            this.patients = getPatients();
          
        }

        //  getIllness();
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
        this.birthdayTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));//tome los valores que estan en el indice 0
            }
        });
        this.emailTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
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

        //Cargar los datos en una tableView
        if (patients != null && !patients.isEmpty()) {
            this.patientsTableView.setItems(getData());
        }

    }

    public static void loadPage(URL ui, BorderPane bp) {
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

    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        //Se manda a bari el lugar para agregar a los pacientes
        loadPage(getClass().getResource("FXMLAddPatient.fxml"), bp);
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

    //El getData para la tableView
    private ObservableList<List<String>> getData() {
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            for (int i = 1; i <= patients.size(); i++) {
                Patient p = (Patient) patients.getNode(i).data;
                List<String> arrayList = new ArrayList<>();

                arrayList.add(String.valueOf(p.getId()));
                arrayList.add(p.getFirstname());
                arrayList.add(p.getLastname());
                arrayList.add(util.Utility.format(p.getBirthday()));
                arrayList.add(p.getEmail());
                arrayList.add(p.getAdress());

                data.add(arrayList);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;

    }

    //Para obetner los datos dela rchivo y cargarlos alas listas
    private CircularLinkedList getPatients() {
    
        CircularLinkedList list = util.Utility.getCircularLinkedList();
        BufferedReader br = archives.getBufferedReader("patients");
                 
        File file = new File("patients.txt");
                 System.out.print("Sigue");

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
                                controlTokens++;
                                System.out.print(id);
                                break;
                            case 2:
                                lstName = sT.nextToken();
                                controlTokens++;
                                break;
                            case 3:
                                fName = sT.nextToken();
                                controlTokens++;
                                break;
                            case 4:
                                birthday = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                            case 5:
                                email = sT.nextToken();
                                controlTokens++;
                                break;
                            case 6:
                                address = sT.nextToken();
                                controlTokens++;
                                break;
                        }
                    }//End while   
                     Patient p = new Patient(id, lstName, fName, birthday, email, address);
                    //Esto evita que en la lista se repiten enfermedades o se sumen dobles
                    if (list.isEmpty()) {
                        list.add(p);
                        lineRegister = br.readLine();
                    } else if (!list.contains(p)) {
                        list.add(new Patient(id, lstName, fName, birthday, email, address));
                        lineRegister = br.readLine();
                    }
                }
                //Se pone aqui para que se carge en el addList
                util.Utility.setCircularLinkedList(list);

            } else {
                //Sino existe se crea uno
                file.createNewFile();
            }

        } catch (IOException | ListException ex) {
            Logger.getLogger(FXMLIllnessAndDiseaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLPatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
