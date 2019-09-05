package com.sgg.zh.utils;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailUtils {

	public static void sendMail(String email, String emailMsg) throws AddressException, MessagingException {
		//1.����һ������������������ػ�����Session
		
		Properties propers = new Properties();
		
		//���÷��͵�Э��
		propers.setProperty("mail.transport.protocol", "SMTP");
		
		//���÷����ʼ��ķ�����
		propers.setProperty("mail.host", "localhost");
		propers.setProperty("mail.smtp.auth", "true");
		
		//������֤��
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("service", "123");
			}
		};
		
		Session session = Session.getInstance(propers, auth);
		
		//2.����һ��Message, ���൱�����ʼ�������
		Message message = new MimeMessage(session);
		
		//���÷�����
		message.setFrom(new InternetAddress("service@store.com"));
		
		//���÷��ͷ�ʽ�������
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		
		//�����ʼ�����
		message.setSubject("�û�����");
		
		//�����ʼ�����
		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		//3.����Transporty���ڽ��ʼ�����
		Transport.send(message);
	}
}
