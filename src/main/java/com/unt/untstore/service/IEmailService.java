package com.unt.untstore.service;

import javax.mail.MessagingException;

public interface IEmailService {

	public void sendSimpleMessage(String to, String subject, String text);
	
	public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException;
}
