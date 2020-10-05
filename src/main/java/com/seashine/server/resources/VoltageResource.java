package com.seashine.server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.Voltage;
import com.seashine.server.services.VoltageService;

@RestController
@RequestMapping(value = "/voltages")
public class VoltageResource {

	@Autowired
	private VoltageService voltageService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Voltage>> findAll() {
		List<Voltage> voltages = voltageService.findAll();

		return ResponseEntity.ok().body(voltages);
	}

}
