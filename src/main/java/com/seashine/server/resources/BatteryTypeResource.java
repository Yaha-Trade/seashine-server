package com.seashine.server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.BatteryType;
import com.seashine.server.services.BatteryTypeService;

@RestController
@RequestMapping(value = "/batterytypes")
public class BatteryTypeResource {

	@Autowired
	private BatteryTypeService batteryTypeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BatteryType>> findAll() {
		List<BatteryType> batteryTypes = batteryTypeService.findAll();

		return ResponseEntity.ok().body(batteryTypes);
	}

}
