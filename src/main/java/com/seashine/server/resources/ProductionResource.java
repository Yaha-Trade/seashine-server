package com.seashine.server.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.Production;
import com.seashine.server.services.ProductionService;

@RestController
@RequestMapping(value = "/productions")
public class ProductionResource {

	@Autowired
	private ProductionService productionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Production> findById(@PathVariable Integer id) {
		Production production = productionService.findById(id);

		return ResponseEntity.ok().body(production);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Production production, @PathVariable Integer id) {
		production.setId(id);
		production = productionService.update(production);

		return ResponseEntity.noContent().build();
	}
}
