package com.seashine.server.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.seashine.server.domain.User;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendNewPasswordEmail(User user, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(User user, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(user.getEmail());
		sm.setFrom(sender);
		sm.setSubject("New password request ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + newPass);

		return sm;
	}

}
