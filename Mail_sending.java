package mali_file;
//import javax.swing.*;
//import java.awt.Dimension;
//import java.awt.Rectangle;
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
//import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
public class Mail_sending {

     public boolean mail_sending(Node m) {
       Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                
              
 
        	Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("SENDING MAIL@gmail.com", "PASSWORD");
			}
                        });//JOptionPane.showMessageDialog(null,"authenticated");
                        System.out.println("authenticated");
                        try {
                            String aaa[]={"RECIEVING MAIL 1 @gmail.com,RECEIVING MAIL 2 @gmail.com,SEPERATED WITH@gmail.com , AS MANY AS U WANT@gmail.com"};
			Message message[]=new Message[aaa.length];
                        int t=0;
                        for(t=0;t<aaa.length;t++)
                        {
                        message[t]= new MimeMessage(session);
			message[t].setFrom(new InternetAddress("SENDING MAIL@gmail.com"));
			message[t].setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(aaa[t]));
			message[t].setSubject("Family_File of = "+m.name.toUpperCase());
			message[t].setText("#128/3 Dashmesh nagar-A");
                        
                        
  BodyPart messageBodyPart = new MimeBodyPart();

  // Fill the message
  messageBodyPart.setText(m.file.getAbsolutePath());

  Multipart multipart = new MimeMultipart();
  multipart.addBodyPart(messageBodyPart);

  // Part two is attachment
  messageBodyPart = new MimeBodyPart();
  //String filename = "C:\\screenshot.zip";
  //File ff=new File("E:\\Screen shots1\\manu.jpg");
  DataSource source = new FileDataSource(m.file);
 
  messageBodyPart.setDataHandler(new DataHandler(source));
  messageBodyPart.setFileName(m.file.getName());
  multipart.addBodyPart(messageBodyPart);

  // Put parts in message
  message[t].setContent(multipart);
                     
                        
                       Transport.send(message[t]);
   
                        //JOptionPane.showMessageDialog(null,"DONE");
                       System.out.println("DONE Sending to    "+aaa[t]);
                        }}
                  catch(Exception e)
                  {
                      
                    //JOptionPane.showMessageDialog(null,e);}
                      System.out.println(e);
                      return false;
                  
                  }
     
                        
         return true;
    }
    
}
