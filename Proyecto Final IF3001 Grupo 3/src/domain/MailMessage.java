/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Maria Celeste
 */
public class MailMessage {
    	
 public static void sendMail(String recipient) throws Exception{
       System.out.println("Preparing to send email");
            String myAccountEmail = "clinicacfm@gmail.com";
            String password = "ClauFioCele22";
               
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

        Message message = prepareMessage(session, myAccountEmail,recipient);
        Transport.send(message);
//        Transport transport = session.getTransport();
//        transport.connect("smtp.gmail.com", 25, myAccountEmail, password);
//       // transport.connect("smtp.gmail.com", "clinicacfm@gmail.com", password);
//        transport.sendMessage(message, message.getAllRecipients());
//        transport.close();
        System.out.println("Message sent succesfully");
    }
//Se hace le mesaje para enviarse
    private static Message prepareMessage(Session session, String myAccountEmail,String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("My First Email from Java App");
            message.setText("Hey There, \n Look my email");
            return message;//retorna el mensaje para la clase
        } catch (Exception ex) {
            Logger.getLogger(MailMessage.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;

   }
  
    
}
