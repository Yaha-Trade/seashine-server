package com.seashine.server.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.I18n;
import com.seashine.server.repositories.I18nRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.I18nSpecs;

@Service
public class I18nService {

	@Autowired
	private I18nRepository i18nRepository;

	public I18n findById(Integer id) {
		Optional<I18n> obj = i18nRepository.findById(id);

		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Class: " + I18n.class.getName()));
	}

	public List<I18n> findAll() {
		return i18nRepository.findAll();
	}

	public List<I18n> findAllByLanguage(Integer languageId) {
		return i18nRepository.findByLanguageId(languageId);
	}

	public Map<String, String> getMapByLanguageId(Integer id) {
		List<I18n> i18ns = i18nRepository.findByLanguageId(id);

		return i18ns.stream().collect(Collectors.toMap(I18n::getKeyValue, i18n -> i18n.getTextValue()));
	}

	public Page<I18n> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String textValue, String language) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return i18nRepository.findAll(getFilters(textValue, language), pageRequest);
	}

	private Specification<I18n> getFilters(String textValue, String language) {
		Specification<I18n> i18nSpecs = Specification.where(null);

		if (!textValue.equals("")) {
			i18nSpecs = i18nSpecs.and(I18nSpecs.filterLikeByTextValue(textValue));
		}

		if (!language.equals("")) {
			i18nSpecs = i18nSpecs.and(I18nSpecs.filterLikeByLanguageName(language));
		}

		return i18nSpecs;
	}

	public I18n update(I18n i18n) {
		I18n i18nDB = findById(i18n.getId());
		i18nDB.setTextValue(i18n.getTextValue());
		return i18nRepository.save(i18nDB);
	}
}
