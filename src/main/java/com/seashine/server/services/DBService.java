package com.seashine.server.services;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.config.SecurityConfig;
import com.seashine.server.domain.I18n;
import com.seashine.server.domain.Language;
import com.seashine.server.domain.User;
import com.seashine.server.domain.enums.Languages;
import com.seashine.server.domain.enums.Profile;
import com.seashine.server.repositories.I18nRepository;
import com.seashine.server.repositories.LanguageRepository;
import com.seashine.server.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private SecurityConfig pe;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private I18nRepository i18nRepository;

	@Autowired
	private LanguageRepository languageRepository;

	public void instantiateTestDataBase() throws ParseException {
		Set<Integer> profiles = new HashSet<Integer>();
		profiles.add(Profile.ADMINISTRADOR.getCode());

		Language english = new Language(Languages.ENGLISH.getCode(), "English", "en");
		Language chinese = new Language(Languages.CHINESE.getCode(), "Chinese", "zh");

		User jean = new User(null, "Jean Briesemeister", "jean@yahacomex.com.br", "jean",
				pe.bCryptPasswordEncoder().encode("123"), english, profiles);

		User miranda = new User(null, "Rafael Miranda", "rafaelmiranda@yahacomex.com.br", "miranda",
				pe.bCryptPasswordEncoder().encode("456"), chinese, profiles);

		User doctor = new User(null, "Doutor Estranho", null, "doctor", pe.bCryptPasswordEncoder().encode("789"),
				english, profiles);

		I18n addresse = new I18n(null, "address", "Address", english);
		I18n addressc = new I18n(null, "address", "地址", chinese);

		I18n addbaterye = new I18n(null, "addbatery", "Add battery", english);
		I18n addbateryc = new I18n(null, "addbatery", "加電池", chinese);

		I18n addnewe = new I18n(null, "addnew", "Add new", english);
		I18n addnewc = new I18n(null, "addnew", "添新", chinese);

		I18n batterytypee = new I18n(null, "batterytype", "Battery type", english);
		I18n batterytypec = new I18n(null, "batterytype", "電池類型", chinese);

		languageRepository.saveAll(Arrays.asList(english, chinese));
		userRepository.saveAll(Arrays.asList(jean, miranda, doctor));
		i18nRepository.saveAll(Arrays.asList(addresse, addressc, addbaterye, addbateryc, addnewe, addnewc, batterytypee,
				batterytypec));
	}
}
