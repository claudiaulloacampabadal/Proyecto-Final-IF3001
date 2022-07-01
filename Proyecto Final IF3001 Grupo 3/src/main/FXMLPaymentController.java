/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Payment;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.QueueException;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Fiorella
 */
public class FXMLPaymentController implements Initializable {
    ArchiveTXT archives = new ArchiveTXT();
    HeaderLinkedQueue payment;

    @FXML
    private BorderPane bp;
    @FXML
    private TableView<List<String>> paymentTableView;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnRead;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<String> optionsPayment;
    @FXML
    private TableColumn<List<String>,String> serviceTableColumn;
    @FXML
    private TableColumn<List<String>,String> dateTableColumn;
    @FXML
    private TableColumn<List<String>,String> totalTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Si no esta vacia
        if(!util.Utility.getDoublyLinkedList().isEmpty()){
            //carga la lista utility, que añadio
            this.payment = util.Utility.getHeaderLinkedQueue();
        }else{try {
            //si esta vacia por primera vez entonces
            //Llama al metodo que carga el archivo
            this.payment = getPayment();
            } catch (QueueException ex) {
                Logger.getLogger(FXMLPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.serviceTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));//tome los valores que estan en el indice 0
            }
        });
        this.dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(1));//tome los valores que estan en el indice 1
            }
        });
        this.totalTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(2));//tome los valores que estan en el indice 2
            }
        });
        
        //Cargar los datos en una tableView
        if (payment!=null && !payment.isEmpty()) {
            try {
                this.paymentTableView.setItems(getData());
            } catch (QueueException ex) {
                Logger.getLogger(FXMLPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }    

    private HeaderLinkedQueue getPayment() throws QueueException {
    
        HeaderLinkedQueue list = util.Utility.getHeaderLinkedQueue();
        BufferedReader br = archives.getBufferedReader("payment");
        
        //creamos el archivo de pago
        File file = new File("payment.txt");
        
       try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {

                    int id = 0;
                    String paymentMode="";
                    double serviceCharges=0;
                    double totalCharge=0;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date billingDate = null;
                    
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
                                paymentMode = sT.nextToken();
                                controlTokens++;//modo de pago
                                break;
                            case 3:
                                //convertir a date
                                billingDate = format.parse(sT.nextToken());
                                controlTokens++;
                                break;
                        }
                    }
                    
                     Payment pay = new Payment(id, paymentMode, serviceCharges, billingDate, totalCharge);
                    //Evita repeticion de pagos
                    if(lineRegister != null){
                        list.enQueue(pay);
                    }
                    lineRegister = br.readLine();
                }
                //Se pone aqui para que se carge en la lista encolada
                util.Utility.setHeaderLinkedQueue(list);
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
    
     //El getData para la tableView
     private ObservableList<List<String>> getData() throws QueueException{
        //recordar agregar los datos
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        for(int i = 1; i <= payment.size(); i++) {
            Payment p = (Payment) payment.deQueue();
            //Se le actualizan los cambios a la cola original en utility
            util.Utility.setHeaderLinkedQueue(payment);
            
            List<String> arrayList = new ArrayList<>();
            
            arrayList.add(String.valueOf(p.getId()));
            arrayList.add(util.Utility.format(p.getBillingDate()));
            arrayList.add(String.valueOf(p.getPaymentMode()));
            arrayList.add(String.valueOf(p.getServiceCharge()));
            arrayList.add(String.valueOf(p.getTotalCharge()));
            
            data.add(arrayList);
        }
                
          return data;  
     }
    
}
