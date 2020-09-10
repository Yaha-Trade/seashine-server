package com.seashine.server.services;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.config.SecurityConfig;
import com.seashine.server.domain.User;
import com.seashine.server.domain.enums.Language;
import com.seashine.server.domain.enums.Profile;
import com.seashine.server.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private SecurityConfig pe;

	@Autowired
	private UserRepository userRepository;

	public void instantiateTestDataBase() throws ParseException {
		Set<Integer> profiles = new HashSet<Integer>();
		profiles.add(Profile.ADMINISTRADOR.getCode());

		User jean = new User(null, "Jean Briesemeister", "jean@yahacomex.com.br", "jean",
				pe.bCryptPasswordEncoder().encode("123"), Language.ENGLISH, profiles);
		
		User miranda = new User(null, "Rafael Miranda", "rafaelmiranda@yahacomex.com.br", "miranda",
				pe.bCryptPasswordEncoder().encode("456"), Language.CHINESE, profiles);

		userRepository.saveAll(Arrays.asList(jean, miranda));
	}
}
