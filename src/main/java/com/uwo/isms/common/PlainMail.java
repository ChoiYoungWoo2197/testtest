package com.uwo.isms.common;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlainMail {	
	
	static Logger log = LogManager.getLogger(PlainMail.class);
	
	public static void SendMail(Map map, Map smtpMap){
		String content_type ="text/html";
		//String smtp = "idstrust.com";
		String smtp = (String)smtpMap.get("smtpInfo");
		final String tUser = (String)smtpMap.get("authUser");
		final String tPassword = (String)smtpMap.get("authPassword");
		String port = (String)smtpMap.get("smtpPort");
		
		String chracter_set = "euc-kr";
		
		String senderEmail = (String)map.get("senderEmail");
		String senderName = (String)map.get("senderName");
		String receiver = (String)map.get("receiver");
		String subject = (String)map.get("subject");
		String contents = (String)map.get("contents");
		
		
		try{
			Properties pt = System.getProperties();
			pt.put("mail.smtp.auth", "true");
			pt.put("mail.smtp.host", smtp);
			pt.put("mail.smtp.port", port);
			pt.put("mail.smtp.starttls.enable", "true");
			//pt.put("mail.smtp.ssl.enable", "true");
			//pt.put("mail.smtp.socketFactory.port", port);
			//pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			Session session = Session.getDefaultInstance(pt, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(tUser, tPassword);
				}
			});
			MimeMessage msg = new MimeMessage(session);
			
			msg.setSentDate(new Date());
			
			String sender = "<" + senderEmail + ">";
			InternetAddress from = new InternetAddress(sender);
			msg.setFrom(from);
			
			InternetAddress to = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, to);
			
			msg.setSubject(subject,chracter_set);
			
			msg.setText(contents, chracter_set);
			
			msg.setHeader("Content-Type", content_type);
			
			javax.mail.Transport.send(msg);
		}catch(AddressException addr_e){
			System.out.println(addr_e.getMessage());
		}catch(MessagingException msg_e){
			System.out.println(msg_e.getMessage());
		}
	}
}
