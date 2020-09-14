package com.seashine.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.I18n;
import com.seashine.server.repositories.I18nRepository;

@Service
public class I18nService {

	@Autowired
	private I18nRepository i18nRepository;

	public List<I18n> findAll() {
		return i18nRepository.findAll();
	}

	public List<I18n> findAllByLanguage(Integer languageId) {
		return i18nRepository.findByLanguageId(languageId);
	}

}
