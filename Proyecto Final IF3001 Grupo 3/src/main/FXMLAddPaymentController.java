/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.Appointment;
import domain.Archives.ArchiveTXT;
import domain.Payment;
import domain.TDA.CircularLinkedList;
import domain.TDA.DoublyLinkedList;
import domain.TDA.HeaderLinkedQueue;
import domain.TDA.ListException;
import domain.TDA.QueueException;
import domain.TDA.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Fiorella
 */
public class FXMLAddPaymentController implements Initializable {

    ArchiveTXT archives = new ArchiveTXT();
    Alert alert;
    private HeaderLinkedQueue payment;
    BorderPane paymentPane;
    private SinglyLinkedList users;
    private DoublyLinkedList appointments;
    private BufferedWriter bw;
    private File f;
    private CircularLinkedList patient;

    @FXML
    private Button btnCreate;
    @FXML
    private TextField serviceTextField;
    @FXML
    private TextField totalTextField;
    @FXML
    private ComboBox paymentComboBox;
    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker calendarChoice;
    @FXML
    private BorderPane bp;
    @FXML
    private Button btnSend;
    String []paymentMode={"Card","Cash"};   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        appointments= new DoublyLinkedList();
   
        try {
            for (int i = 1; i <= appointments.size(); i++) {
                this.paymentComboBox.setItems((ObservableList) getAppointment());
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLAddPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        paymentComboBox.getItems().addAll(paymentMode);
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
    private void btnCreateOnAction(ActionEvent event) throws QueueException, IOException {
        try {
            if (!idTextField.getText().equals("") && !serviceTextField.getText().equals("") && !"".equals(calendarChoice) && !totalTextField.getText().equals("")) {
                if (util.Utility.countDigits(Integer.parseInt(idTextField.getText())) == 9) {
                    //Le hace una isntancia para el date
                    Calendar date = Calendar.getInstance();
                    //Le da un set a date para obtener los valores que se requieren               
                    date.set(calendarChoice.getValue().getYear(), calendarChoice.getValue().getMonthValue(), calendarChoice.getValue().getDayOfMonth());
                    payment.enQueue(new Payment(Integer.parseInt(idTextField.getText()), paymentComboBox.getAccessibleText(), Double.parseDouble(serviceTextField.getText()), date.getTime(), Double.parseDouble(totalTextField.getText())));
                    if (!payment.contains(payment)) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creating bill");
                        alert.setContentText("CREATED BILL");
                        alert.show();
                        loadPage(getClass().getResource("FXMLPayment.fxml"), paymentPane);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("CREATED BILL");
                        alert.setContentText("Element is repeated");
                        alert.show();
                    }
                } else {//Revisa que no esten vacios los espacios
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Creating bill");
                    alert.setContentText("Fill ALL the blank spaces");
                    alert.show();

                }
            }
        } catch (NumberFormatException nfe) {//La validacion si insertan una letra
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creating bill");
            alert.setContentText("Invalid character, try a number.");
            alert.show();
        }
        Stage mystage = (Stage) btnCreate.getScene().getWindow();
        mystage.close();
    }
    
    
    
       private DoublyLinkedList getAppointment() {
        DoublyLinkedList list = util.Utility.getDoublyLinkedListAppointment();
        BufferedReader br = archives.getBufferedReader("appointment");
        File file = new File("appointment.txt");

        try {
            //Revisa si el archivo existe
            if (file.exists()) {
    
                String lineRegister = br.readLine();
                while (lineRegister != null) {
             
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
                                idPatient = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del Paciente
                                break;
                            case 2:
                                idDoctor = Integer.parseInt(sT.nextToken());
                                controlTokens++;//El id del doctor
                                break;
                            case 3:
                                //Convierte de String a local date time
                                dateTime = LocalDateTime.parse(sT.nextToken(),format);
                                controlTokens++;
                                break;
                            case 4:
                                remarks = sT.nextToken();
                                controlTokens++;//Remarks 
                                break;
                        }
                    }//End while   
                     Appointment ap = new Appointment(idPatient, idDoctor, dateTime, remarks);
                    
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
       

    @FXML
    private void btnSendOnAction(ActionEvent event) throws IOException {
        
    }

    public void generateBill() throws FileNotFoundException, DocumentException, BadElementException, IOException {

        FileOutputStream archive = new FileOutputStream("root" + "\\Factura Cita.pdf"); //Falta la ruta
        Document doc = new Document();

        PdfWriter.getInstance(doc, archive);
        doc.open();

        Paragraph title = new Paragraph("Clínica...\n\n", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));

        title.setAlignment(Paragraph.ALIGN_CENTER);

        Image img = Image.getInstance("C:\\Users\\Saúl\\Desktop\\AED\\2022\\Proyecto AED\\ProyectAlgorithmsGrupo5\\Proyecto\\src\\images\\logo.png");
        img.setAlignment(Image.ALIGN_CENTER);
        img.setBorder(Image.BOX);
        img.setBorderWidth(0);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1100, 100); // tamaño 
        doc.add(img);
        doc.add(title);

        //  public Payment(int patientID, String paymentMode, double serviceCharge, Date billingDate) {
        PdfPTable billTable = new PdfPTable(1); //El numero adentro del paréntesis es la cantidad de columnas

        Date currentDate = new Date();
        currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //Fecha Actual

        String billCurrentDate = currentDate.getDate() + "/" + (currentDate.getMonth() + 1) + "/" + (currentDate.getYear() + 1900);
        String billCurrentHour = currentDate.getHours() + ":" + currentDate.getMinutes();

        if (currentDate.getMinutes() < 10) {
            billCurrentHour = currentDate.getHours() + ":0" + currentDate.getMinutes();
        }

      

        doc.close();

    }
    


}
