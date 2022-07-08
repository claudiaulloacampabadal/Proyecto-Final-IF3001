/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


        
import domain.Archives.ArchiveTXT;
import domain.TDA.BST;
import java.io.File;
import java.io.IOException;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Maria Celeste
 */
public class MailMessagePDF {
    	
 public void sendMail(String recipient, String name, String text, File file) throws Exception{
       System.out.println("Preparing to send email");
         ArchiveTXT archive = new ArchiveTXT();
         BST bst =  archive.getConfigurations("configurations");
         Configuration confg = (Configuration) bst.getRoot().data;
       
        String myAccountEmail = confg.getClinicEmail();
        String password = confg.getClinicPassword();
               
        Properties properties = System.getProperties();
            //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "25");
        properties.put("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");
   
      //  properties.put("mail.debug", "true");
 
    //Se añade el email que e debe enviar

       Session session = Session.getInstance(properties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            myAccountEmail, password);
                }
            });

        Message message = prepareMessage(session, myAccountEmail,recipient, name, text, file);
       
        Transport.send(message, message.getAllRecipients());
      //  Transport transport = session.getTransport("smtp");
      //  transport.connect("smtp.gmail.com", 25, myAccountEmail, password);
       // transport.connect("smtp.gmail.com", "clinicacfm@gmail.com", password);
      //  transport.sendMessage(message, message.getAllRecipients());
      //  transport.close();
        System.out.println("Message sent succesfully");
    }
//Se hace le mesaje para enviarse
    private Message prepareMessage(Session session, String myAccountEmail,String recipient, String name, String text, File file) throws IOException {
        try {
            BST configurations = util.Utility.getBST();
            Configuration confg = (Configuration) configurations.getRoot().data;
            Message message = new MimeMessage(session);
            MimeMultipart parts = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            MimeBodyPart image = new MimeBodyPart();
            MimeBodyPart pdf = new MimeBodyPart();
            DataSource source = new FileDataSource(confg.getImagesPath());   
            DataSource sourceP = new FileDataSource(file.getAbsolutePath());   
            //Poner el texto del email
            texto.setText(text);  
            //Para poner una imagen al enviar el correo
           // image.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\Maria Celeste\\Desktop\\U - I.E\\I Semestre-2022\\IF 3001\\ProyectoGr3\\Proyecto-Grupo3\\Proyecto Final IF3001 Grupo 3\\src\\images\\medical.png")));
          image.setDataHandler(new DataHandler(source));
          image.setFileName("CLINICA CFM.png");
          
          //Añadir el pdf para enviar la factura
            pdf.setDataHandler(new DataHandler(sourceP));
            pdf.setFileName("FACTURA_"+util.Utility.random()+".pdf");
         
            //Añadir texto e imagen
            parts.addBodyPart(texto);
            parts.addBodyPart(image);
            parts.addBodyPart(pdf);
            
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject(name + ", This a message from Clinic-CFM");
            message.setContent(parts);
             
            return message;//retorna el mensaje para la clase
        } catch (MessagingException ex) {
            Logger.getLogger(MailMessagePDF.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;

   }
    
}
