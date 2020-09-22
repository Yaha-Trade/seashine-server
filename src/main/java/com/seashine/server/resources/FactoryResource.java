package com.seashine.server.resources;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.Factory;
import com.seashine.server.services.FactoryService;

@RestController
@RequestMapping(value = "/factories")
public class FactoryResource {

	@Autowired
	private FactoryService factoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Factory>> findAll() {
		List<Factory> factories = factoryService.findAll();

		return ResponseEntity.ok().body(factories);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Factory> findById(@PathVariable Integer id) {
		Factory factory = factoryService.findById(id);

		return ResponseEntity.ok().body(factory);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Factory>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "contact", defaultValue = "") String contact,
			@RequestParam(value = "bankAccountNumber", defaultValue = "") String bankAccountNumber) {

		Page<Factory> customers = factoryService.findPage(page, linesPerPage, orderBy, orderByDirection.toUpperCase(),
				name, address, contact, bankAccountNumber);

		return ResponseEntity.ok().body(customers);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Factory factory) {
		factory = factoryService.insert(factory);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(factory.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Factory factory, @PathVariable Integer id) {
		factory.setId(id);
		factory = factoryService.update(factory);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		factoryService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
