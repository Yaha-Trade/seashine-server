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

import com.seashine.server.domain.Season;
import com.seashine.server.dto.SeasonListDTO;
import com.seashine.server.services.SeasonService;

@RestController
@RequestMapping(value = "/seasons")
public class SeasonResource {

	@Autowired
	private SeasonService seasonService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Season>> findAll() {
		List<Season> seasons = seasonService.findAll();

		return ResponseEntity.ok().body(seasons);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Season> findById(@PathVariable Integer id) {
		Season season = seasonService.findById(id);

		return ResponseEntity.ok().body(season);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<SeasonListDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "customer", defaultValue = "") String customer) {

		Page<Season> seasons = (Page<Season>) seasonService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), name, customer);

		Page<SeasonListDTO> seasonsListDTO = seasons.map(season -> new SeasonListDTO(season));

		return ResponseEntity.ok().body(seasonsListDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Season season) {
		season = seasonService.insert(season);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(season.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Season season, @PathVariable Integer id) {
		season.setId(id);
		season = seasonService.update(season);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		seasonService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
