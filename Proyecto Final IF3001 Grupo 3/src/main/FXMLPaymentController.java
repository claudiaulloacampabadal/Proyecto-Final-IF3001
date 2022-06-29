/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import domain.Archives.ArchiveTXT;
import domain.Patient;
import domain.Payment;
import domain.TDA.CircularLinkedList;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.QueueException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import static jdk.nashorn.internal.runtime.Debug.id;

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
        //ObservableList <String> List FXCollections
        // TODO
        
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
    
}
