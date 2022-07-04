/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import com.itextpdf.text.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.PDF;
import domain.Patient;
import domain.Payment;
import domain.TDA.CircularLinkedList;
import domain.TDA.ListException;
import domain.TDA.QueueException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Claudia
 */
public class FXMLReportsController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Button generatePDF;
    CircularLinkedList Patient;
    PDF p = new PDF();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void btnGeneratePDF(ActionEvent event) throws DocumentException, BadElementException, IOException, QueueException, ListException {
        
//       p.generateReport(prueba, Prueba PDF, getData());
        
//            generaPDF();
              

    }

    private ObservableList<java.util.List<String>> getData() throws QueueException, ListException {
        //recordar agregar los datos
//        Patient p = new Patient(0, lastname, firstname, birthday, email, adress);
        ObservableList<java.util.List<String>> data = FXCollections.observableArrayList();

        for (int i = 1; i <= Patient.size(); i++) {
            Patient p = (Patient) Patient.getNext(Patient);
            //Se le actualizan los cambios a la cola original en utility
            util.Utility.setCircularLinkedList(Patient);

            java.util.List<String> arrayList = new ArrayList<>();

            arrayList.add(String.valueOf(p.getId()));
            arrayList.add(util.Utility.format(p.getBirthday()));
            arrayList.add(String.valueOf(p.getAdress()));
            arrayList.add(String.valueOf(p.getEmail()));
            arrayList.add(String.valueOf(p.getLastname()));

            data.add(arrayList);
        }
            return data;
        }
        

    public static void loadPage(URL ui, BorderPane bp) {
        Parent root = null;
        try {
            root = FXMLLoader.load(ui);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainMenuController.class.getName());
        }
        //cleaning nodes
        //bp.setTop(null);
        bp.setCenter(null);
        //bp.setBottom(null); 
        //bp.setLeft(null);
        //bp.setRight(null);
        bp.setCenter(root);
    }
//
//    @FXML
//        public void generaPDF() throws FileNotFoundException, DocumentException, BadElementException, IOException {
//        ArrayList arrayTest = new ArrayList();
//        FileOutputStream archive = new FileOutputStream("C:\\Users\\Usuario\\Desktop\\Prueba\\Pruebas.pdf");
//        Document doc = new Document();
//
//        arrayTest.add("Hamburguesas");
//        arrayTest.add("Pizza");
//        arrayTest.add("Empanadas");
//        arrayTest.add("Burritos");
//        arrayTest.add("Papas");
//
//
//        PdfWriter.getInstance(doc, archive);
//        doc.open();
//
//        Paragraph title = new Paragraph("Lista de Comida\n\n", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));
//
//        title.setAlignment(Paragraph.ALIGN_CENTER);
//
//        Image img = Image.getInstance("C:\\Users\\Usuario\\Desktop\\Prueba\\icon.png");
//        img.setAlignment(Image.ALIGN_CENTER);
//        img.setBorder(Image.BOX);
//        img.setBorderWidth(0);
//        img.setBorderColor(BaseColor.WHITE);
//        img.scaleToFit(1100, 100); // tamaño 
//        doc.add(img);
//        doc.add(title);
//        Paragraph products = new Paragraph("Comidas" + "\n\n", FontFactory.getFont("arial", 18, Font.NORMAL, BaseColor.BLACK));
//        products.setAlignment(Paragraph.ALIGN_CENTER);
//        doc.add(products);
//
//        for (int i = 0; i < arrayTest.size(); i++) {
//
//            Paragraph food = new Paragraph(arrayTest.get(i) + "\n\n", FontFactory.getFont("arial", 18, Font.NORMAL, BaseColor.BLACK));
//            food.setAlignment(Paragraph.ALIGN_CENTER);
//            doc.add(food);
//            PdfPTable tablefood = new PdfPTable(1); //El numero adentro del paréntesis es la cantidad de columnas
//            doc.add(tablefood);
//
//        }
//
//        doc.close();
//        
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Confirmación");
//        alert.setContentText("Se ha generado el pdf");
//        alert.show();
//
//    }

}

