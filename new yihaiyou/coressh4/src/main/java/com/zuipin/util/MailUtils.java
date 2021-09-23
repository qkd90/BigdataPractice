package com.zuipin.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;

public class MailUtils {
	
	// 邮箱服务器
	private static String		host			= "smtp.jxmall.com";
	
	private static String		mail_from		= "service@jxmall.com";
	
	private final static String	SERIVCE_PWD		= "damall.12345";
	
	private final static String	SERIVCE_NAME	= "service";
	
	public static void send(String mail_to, String subject, String html, String mail_cc) throws Exception {
		try {
			Properties props = new Properties(); // 获取系统环境
			Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(mail_from, "吉象吉送"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
			if (StringUtils.isNotBlank(mail_cc)) {
				message.setRecipient(Message.RecipientType.CC, new InternetAddress(mail_cc));
			}
			message.setSubject(subject);
			
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(html, "text/html;charset=UTF-8");
			
			Multipart part = new MimeMultipart();
			part.addBodyPart(bodyPart);
			message.setContent(part);
			
			Transport.send(message);
			System.out.println("send ok!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	/**
	 * 用来进行服务器对用户的认证
	 */
	public static class Email_Autherticator extends Authenticator {
		
		public Email_Autherticator() {
			super();
		}
		
		public Email_Autherticator(String user, String pwd) {
			super();
		}
		
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SERIVCE_NAME, SERIVCE_PWD);
		}
	}
	
	public static String encode64(String str) {
		return new String(Base64.encodeBase64(str.getBytes(Charset.forName("UTF-8"))));
	}
	
	public static String decode64(String str) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(str.getBytes()), "UTF-8");
	}
	
}
