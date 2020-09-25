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

import com.seashine.server.domain.Packing;
import com.seashine.server.services.PackingService;

@RestController
@RequestMapping(value = "/packings")
public class PackingResource {

	@Autowired
	private PackingService packingService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Packing>> findAll() {
		List<Packing> packings = packingService.findAll();

		return ResponseEntity.ok().body(packings);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Packing> findById(@PathVariable Integer id) {
		Packing packing = packingService.findById(id);

		return ResponseEntity.ok().body(packing);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Packing>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "englishName") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "englishName", defaultValue = "") String englishName,
			@RequestParam(value = "chineseName", defaultValue = "") String chineseName) {

		Page<Packing> packings = (Page<Packing>) packingService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), englishName, chineseName);

		return ResponseEntity.ok().body(packings);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Packing packing) {
		packing = packingService.insert(packing);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(packing.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Packing packing, @PathVariable Integer id) {
		packing.setId(id);
		packing = packingService.update(packing);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		packingService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
