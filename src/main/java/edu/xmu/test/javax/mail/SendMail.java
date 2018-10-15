package edu.xmu.test.javax.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Before;
import org.junit.Test;

public class SendMail
{
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_AUTH_USER = "davyjones2010@gmail.com";
	private static final String SMTP_AUTH_PWD = "*******";
	private static final String SMTP_PORT_NUM = "465";

	@Before
	public void setUp()
	{
	}

	@Test
	public void sendMail()
	{
		String[] fakeToList = { "554212185@qq.com", "kunlun0519@sohu.com" };
		InternetAddress[] realToList = new InternetAddress[fakeToList.length];

		String from = "davyjones2010@gmail.com";
		String subject = "Hello World!";
		String content = "This is a test email from java-mail";
		Transport transport = null;
		// Set the host smtp address
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", SMTP_HOST_NAME);
		properties.put("mail.smtp.user", SMTP_AUTH_USER);
		properties.put("mail.smtp.port", SMTP_PORT_NUM);
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT_NUM);
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("javax.net.ssl.SSLSocketFactory", "false");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(properties, auth);

		MimeMessage message = new MimeMessage(session);

		System.out.println("CurrentPath = " + ClassLoader.getSystemResource("")
				+ "attachment.txt");
		try
		{
			message.setFrom(new InternetAddress(from));

			for (int i = 0; i < fakeToList.length; i++)
			{
				realToList[i] = new InternetAddress(fakeToList[i]);
			}

			message.setRecipients(Message.RecipientType.TO, realToList);
			message.setSubject(subject);

			// Create a multi-part message
			Multipart multipart = new MimeMultipart();

			// Set part one -> plain text
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);
			multipart.addBodyPart(messageBodyPart);

			// Set part two -> attachment
			BodyPart attachmentBodyPart = new MimeBodyPart();
			String fileName = ClassLoader.getSystemResource("").toString()
					.replaceAll("%20", " ").replaceAll("file:/", "")
					+ "attachment.txt";
			DataSource source = new FileDataSource(fileName);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName("attachment.txt");
			multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

			transport = session.getTransport("smtp");
			transport.connect(SMTP_HOST_NAME, Integer.valueOf(SMTP_PORT_NUM)
					.intValue(), SMTP_AUTH_USER, SMTP_AUTH_PWD);

			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("Message sent successflly...");
		} catch (AddressException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				transport.close();
			} catch (MessagingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class SMTPAuthenticator extends Authenticator
	{
		@Override
		public PasswordAuthentication getPasswordAuthentication()
		{
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;

			return new PasswordAuthentication(username, password);
		}
	}
}