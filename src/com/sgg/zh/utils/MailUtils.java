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
		//1.创建一个程序与邮箱服务器回话对象Session
		
		Properties propers = new Properties();
		
		//设置发送的协议
		propers.setProperty("mail.transport.protocol", "SMTP");
		
		//设置发送邮件的服务器
		propers.setProperty("mail.host", "localhost");
		propers.setProperty("mail.smtp.auth", "true");
		
		//创建验证器
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("service", "123");
			}
		};
		
		Session session = Session.getInstance(propers, auth);
		
		//2.创建一个Message, 它相当于是邮件的内容
		Message message = new MimeMessage(session);
		
		//设置发送者
		message.setFrom(new InternetAddress("service@store.com"));
		
		//设置发送方式与接收着
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		
		//设置邮件主题
		message.setSubject("用户激活");
		
		//设置邮件内容
		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		//3.创建Transporty用于将邮件发送
		Transport.send(message);
	}
}
