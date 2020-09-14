package com.seashine.server.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.I18n;
import com.seashine.server.services.I18nService;

@RestController
@RequestMapping(value = "/locales")
public class I18nResource {

	@Autowired
	private I18nService i18nService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> getLocale(@PathVariable Integer id) {
		List<I18n> i18ns = i18nService.findAllByLanguage(id);

		Map<String, String> map = i18ns.stream()
				.collect(Collectors.toMap(I18n::getKeyValue, i18n -> i18n.getTextValue()));

		return ResponseEntity.ok().body(map);
	}

}
