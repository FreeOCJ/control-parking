package pe.cp.core.service;

import java.util.List;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.UtilDao;

@Service
public class UtilServiceImpl implements UtilService {

	@Autowired
	private UtilDao utildao;
	
	@Override
	public List<String> obtenerDepartamentos() {
		return utildao.obtenerDepartamentos();
	}

	@Override
	public List<String> obtenerProvincias(String departamento) {
		return utildao.obtenerProvincias(departamento);
	}

	@Override
	public List<String> obtenerDistritos(String departamento, String provincia) {
		return utildao.obtenerDistritos(departamento, provincia);
	}
	
	
	@Override
	public void enviarEmail(String to, String pwd )
	{
		
		String USER_NAME = "emailusrtest"; 
	    String PASSWORD = "BCTS*1234"; 
	    
	   
	    Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

       Session session = Session.getDefaultInstance(props);
       MimeMessage message = new MimeMessage(session);
	   
       try {
       	
       message.setFrom(new InternetAddress(USER_NAME));
       InternetAddress toAddress = new InternetAddress(to);

       message.addRecipient(Message.RecipientType.TO, toAddress);
       message.setSubject("Portal Control Parking | Usuario de acceso.");
       message.setText("Estimado usuario: Su clave es: "+ PASSWORD);
           
       //String attachmentName = "C:\\Tempo\\ultimus.pdf";
       //FileDataSource fileDataSource = new FileDataSource(attachmentName);
       
       Transport transport = session.getTransport("smtp");
       transport.connect(host, USER_NAME, PASSWORD);
       transport.sendMessage(message, message.getAllRecipients());
       transport.close();
                 
       }
       catch (AddressException ae) {
           ae.printStackTrace();
       }
       catch (MessagingException me) {
           me.printStackTrace();
       }
	}

}
