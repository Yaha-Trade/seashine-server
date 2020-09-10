package com.seashine.server.services;

import org.springframework.mail.SimpleMailMessage;

import com.seashine.server.domain.User;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(User user, String newPass);
}
