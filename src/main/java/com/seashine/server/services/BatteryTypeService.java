package com.seashine.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.BatteryType;
import com.seashine.server.repositories.BatteryTypeRepository;

@Service
public class BatteryTypeService {

	@Autowired
	private BatteryTypeRepository batteryTypeRepository;

	public List<BatteryType> findAll() {
		return batteryTypeRepository.findAll();
	}

}
