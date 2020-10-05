package com.seashine.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Voltage;
import com.seashine.server.repositories.VoltageRepository;

@Service
public class VoltageService {

	@Autowired
	private VoltageRepository voltageRepository;

	public List<Voltage> findAll() {
		return voltageRepository.findAll();
	}

}
