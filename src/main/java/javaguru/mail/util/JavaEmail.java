package javaguru.mail.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmail {
	Session mailSession;

	public static void main(String args[]) throws AddressException, MessagingException {
		JavaEmail javaEmail = new JavaEmail();
		javaEmail.setMailServerProperties();
		javaEmail.draftEmailMessage();
		javaEmail.sendEmail();
	}

	private void setMailServerProperties() {
		Properties mailProp = System.getProperties();
		mailProp.put("mail.transport.protocol", "smtp");
		mailProp.put("mail.smtp.auth", "true");
		mailProp.put("mail.smtp.starttls.enable", "true");
		mailProp.put("mail.smtp.starttls.required", "true");
		mailProp.put("mail.debug", "true");
		mailProp.put("mail.smtp.ssl.enable", "true");
		mailSession = Session.getDefaultInstance(mailProp, null);
	}

	private MimeMessage draftEmailMessage() throws AddressException, MessagingException {
		String[] toEmails = { "javaguru1988@gmail.com" };
		String emailSubject = "Test email subject";
		String emailBody = "This is an email sent by <b>//Java Guru 1988</b>.";
		MimeMessage emailMessage = new MimeMessage(mailSession);
		/**
		 * Set the mail recipients
		 */
		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}
		emailMessage.setSubject(emailSubject);
		/**
		 * If sending HTML mail
		 */
		emailMessage.setContent(emailBody, "text/html");
		/**
		 * If sending only text mail
		 */
		// emailMessage.setText(emailBody);// for a text email
		return emailMessage;
	}

	private void sendEmail() throws AddressException, MessagingException {
		/**
		 * Sender's credentials
		 */
		String fromUser = "my_gmail_id";
		String fromUserEmailPassword = "my_gmail_password";

		String emailHost = "smtp.gmail.com";
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		/**
		 * Draft the message
		 */
		MimeMessage emailMessage = draftEmailMessage();
		/**
		 * Send the mail
		 */
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}
}