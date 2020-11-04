package com.seashine.server.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.I18n;
import com.seashine.server.dto.I18nListDTO;
import com.seashine.server.services.I18nService;

@RestController
@RequestMapping(value = "/locales")
public class I18nResource {

	@Autowired
	private I18nService i18nService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<I18n> getI18n(@PathVariable Integer id) {
		I18n i18n = i18nService.findById(id);

		return ResponseEntity.ok().body(i18n);
	}

	@RequestMapping(value = "all/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> getAllValues(@PathVariable Integer id) {
		List<I18n> i18ns = i18nService.findAllByLanguage(id);

		Map<String, String> map = i18ns.stream()
				.collect(Collectors.toMap(I18n::getKeyValue, i18n -> i18n.getTextValue()));

		return ResponseEntity.ok().body(map);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<I18nListDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "textValue", defaultValue = "") String textValue,
			@RequestParam(value = "language", defaultValue = "") String language) {

		Page<I18n> i18ns = (Page<I18n>) i18nService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), textValue, language);

		Page<I18nListDTO> i18nsDTO = i18ns.map(i18n -> new I18nListDTO(i18n));

		return ResponseEntity.ok().body(i18nsDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody I18n i18n, @PathVariable Integer id) {
		i18n.setId(id);
		i18n = i18nService.update(i18n);

		return ResponseEntity.noContent().build();
	}

}
