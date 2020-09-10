package com.seashine.server.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.User;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("Email not found");
		}

		String newPass = newPassword();
		user.setPassword(bCryptPasswordEncoder.encode(newPass));

		userRepository.save(user);

		emailService.sendNewPasswordEmail(user, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		} else {
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
