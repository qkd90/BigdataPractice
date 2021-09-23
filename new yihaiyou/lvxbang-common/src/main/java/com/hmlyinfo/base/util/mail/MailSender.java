package com.hmlyinfo.base.util.mail;

import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.util.StringUtil;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
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
import javax.mail.internet.MimeUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 邮件发送类
 *
 * @author qsRoy
 */
public class MailSender {
	private final Logger log = Logger.getLogger(MailSender.class);
	public static final String MALL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	public static final String MALL_SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
	public static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
	public static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

	private static MailSender instance = null;

	private MailSender() {

	}

	public static MailSender getInstance() {
		if (null == instance) {
			instance = new MailSender();
		}
		return instance;
	}

	/**
	 * 发送HTML邮件
	 *
	 * @param sendInfo 邮件信息
	 * @return true:成功,false:失败
	 */
	public boolean sendHtmlMail(MailInfo sendInfo) {
		MailAuthenticator mailAuthenticator = null;
		Properties mailProperties = new Properties();
		mailProperties.put(MALL_TRANSPORT_PROTOCOL, "smtp");
		mailProperties.put(MAIL_SMTP_HOST, Config.get("mail.server.host"));
		mailProperties.put(MAIL_SMTP_PORT, Config.get("mail.server.port"));
		mailProperties.put(MAIL_SMTP_AUTH, Config.get("mail.auth.validation"));
		mailProperties.put(MAIL_SMTP_SOCKETFACTORY_CLASS, MALL_SSL_FACTORY);
		mailProperties.put(MAIL_SMTP_SOCKETFACTORY_FALLBACK, "false");
		mailProperties.put(MAIL_SMTP_SOCKETFACTORY_PORT, Config.get("mail.server.port"));
		mailProperties.put(MAIL_SMTP_STARTTLS_ENABLE, "true");

		if (Boolean.valueOf(Config.get("mail.auth.validation"))) {
			mailAuthenticator = new MailAuthenticator(Config.get("mail.username"), Config.get("mail.password"));
		}
		Session sendMailSession = Session.getInstance(mailProperties, mailAuthenticator);

		sendMailSession.setDebug(true);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address sendFrom = new InternetAddress(Config.get("mail.from"));
			List<String> sendToList = new ArrayList<String>();

			String patternE = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
			Pattern pe = Pattern.compile(patternE);
			if (!StringUtil.isBlank(sendInfo.getMailTo()) && pe.matcher(sendInfo.getMailTo()).matches()) {
				sendToList.add(sendInfo.getMailTo());
			}
			if (null != sendInfo.getMailToList() && sendInfo.getMailToList().size() > 0) {
				for (String toAddr : sendInfo.getMailToList()) {
					if (!StringUtil.isBlank(toAddr) && pe.matcher(sendInfo.getMailTo()).matches()) {
						sendToList.add(toAddr);
					}
				}
			}
			if (sendToList.size() == 0) {
				if (log.isDebugEnabled()) {
					log.debug("--------------------sendToList is null--------------------");
				}
				return true;
			}
			Address[] sendToArr = new Address[sendToList.size()];
			for (int i = 0; i < sendToList.size(); i++) {
				sendToArr[i] = new InternetAddress(sendToList.get(i));
			}
			mailMessage.setFrom(sendFrom);
			mailMessage.setRecipients(Message.RecipientType.TO, sendToArr);
			mailMessage.setSubject(MimeUtility.encodeText(sendInfo.getMailSubject(), "UTF-8", "B"));

			Multipart mainPart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(sendInfo.getMailContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(bodyPart);
			if (null != sendInfo.getMailAttachList() && sendInfo.getMailAttachList().size() > 0) {
				for (String filePath : sendInfo.getMailAttachList()) {
					BodyPart filePart = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(filePath);
					filePart.setDataHandler(new DataHandler(fds));
					filePart.setFileName(fds.getName());
					mainPart.addBodyPart(filePart);
				}
			}
			mailMessage.setContent(mainPart);
			mailMessage.setSentDate(new Date());
			mailMessage.saveChanges();
			Transport.send(mailMessage);
			if (log.isDebugEnabled()) {
				log.debug("--------------------to[" + sendInfo.getMailTo() + "],send ok...--------------------");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				log.debug("--------------------to[" + sendInfo.getMailTo() + "],send fail...--------------------");
			}
			return false;
		}
	}

	class MailAuthenticator extends Authenticator {
		String userName;
		String userPsw;

		public MailAuthenticator() {

		}

		public MailAuthenticator(String sUserName, String sUserPsw) {
			this.userName = sUserName;
			this.userPsw = sUserPsw;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, userPsw);
		}
	}
}
