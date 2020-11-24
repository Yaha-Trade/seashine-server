package com.seashine.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Language;
import com.seashine.server.repositories.LanguageRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	public Language findById(Integer id) {
		Optional<Language> obj = languageRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Language.class.getName()));
	}
}
