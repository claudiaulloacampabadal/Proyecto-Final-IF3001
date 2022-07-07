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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Fiorella
 */
public class FXMLPaymentController implements Initializable {
    ArchiveTXT archives = new ArchiveTXT();
    Alert alert;
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
    private TableColumn<List<String>,String> serviceTableColumn;
    @FXML
    private TableColumn<List<String>,String> dateTableColumn;
    @FXML
    private TableColumn<List<String>,String> totalTableColumn;
    @FXML
    private TableColumn<List<String>,String> paymentTableColumn;
    @FXML
    private Button btnSend;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Si no esta vacia
        if(!util.Utility.getHeaderLinkedQueue().isEmpty()){
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
        this.paymentTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                //
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));//tome los valores que estan en el indice 2
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
         util.Utility.setBorderPanePatient(bp);
        loadPage(getClass().getResource("FXMLAddPayment.fxml"));
    }

    @FXML
    private void btnReadOnAction(ActionEvent event) throws QueueException {
        //Leer segun el id
        TextInputDialog update = new TextInputDialog();
        update.setTitle("Payment");
        update.setHeaderText("Enter the ID of the element to read");
        update.showAndWait();

        Payment p = new Payment(Integer.parseInt(update.getResult()), "", Double.parseDouble(update.getResult()), null, Double.parseDouble(update.getResult()));
        if (payment.contains(p)) {
            int index = payment.indexOf(p);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment - Read");
            alert.setHeaderText("The element is...");
            alert.setContentText(String.valueOf(payment.front()));
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment - Read");
            alert.setContentText("Element doesn't exist");
            alert.show();
        }
    }
    
     @FXML
    private void btnSendOnAction(ActionEvent event) {
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
        domain.TDA.HeaderLinkedQueue c2= new domain.TDA.HeaderLinkedQueue();

        while (!payment.isEmpty()) {
            //desencola
            Payment p = (Payment) payment.deQueue();
            
            //encola en la auxiliar
            c2.enQueue(p);
            
            //Se le actualizan los cambios a la cola original en utility
            util.Utility.setHeaderLinkedQueue(payment);

            List<String> arrayList = new ArrayList<>();
            arrayList.add(" "+String.valueOf(p.getId()));
            arrayList.add(" "+util.Utility.format(p.getBillingDate()));
            arrayList.add(" "+String.valueOf(p.getPaymentMode()));
            arrayList.add(" "+String.valueOf(p.getServiceCharge()));
            arrayList.add(" "+String.valueOf(p.getTotalCharge()));

            data.add(arrayList);
        }
        payment=c2;
        util.Utility.setHeaderLinkedQueue(payment);

        return data;
    }

      //Remover las variables de un archivo
    //Se remueve por id
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
   
   //desencolar por id
   private void remove(Object element, HeaderLinkedQueue queue) throws QueueException{
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
       
        while(!queue.isEmpty()){
             if(util.Utility.equals(queue.front(), element)){
                 queue.deQueue();
             }else{
                 aux.enQueue(queue.deQueue());
             }
        }
   
        while(!aux.isEmpty()){
            queue.enQueue(aux.deQueue());
        }
        
    }

   
}
