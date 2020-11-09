package com.seashine.server.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.History;
import com.seashine.server.services.HistoryService;

@RestController
@RequestMapping(value = "/histories")
public class HistoryResource {

	@Autowired
	private HistoryService historyService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody History history) {
		history = historyService.insert(history);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(history.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
