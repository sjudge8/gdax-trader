package com.judge.gdax.alerts;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.judge.gdax.GdaxProperties;


public class SmsAlerts
{
	static Logger logger = LogManager.getLogger(SmsAlerts.class.getName());

	Properties props = new Properties();

	final String senderUsername = GdaxProperties.ALERT_FROM_EMAIL_ADDRESS;
	final String senderPassword = GdaxProperties.ALERT_FROM_EMAIL_PASSWORD;
	final String receiverEmailAddress = GdaxProperties.ALERT_TO_EMAIL_ADDRESS;

	public SmsAlerts()
	{
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	public void sendAlert(String body)
	{
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(senderUsername, senderPassword);
			}
		});

		try
		{

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(senderUsername));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailAddress));
			msg.setText(body);

			Transport.send(msg);

		} catch (MessagingException e)
		{
			logger.error("Error Sending Out Alert! ", e);
		}
	}
}
