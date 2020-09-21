package com.seashine.server.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Factory;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class FactoryService {

	@Autowired
	private FactoryRepository factoryRepository;

	public Factory findById(Integer id) {
		Optional<Factory> obj = factoryRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Factory.class.getName()));
	}

	public List<Factory> findAll() {
		return factoryRepository.findAll();
	}

	public Page<Factory> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return factoryRepository.findAll(pageRequest);
	}

	@Transactional
	public Factory insert(Factory factory) {
		factory.setId(null);
		factory = factoryRepository.save(factory);

		return factory;
	}

	public Factory update(Factory factory) {
		Factory customerDB = findById(factory.getId());
		updateData(customerDB, factory);
		return factoryRepository.save(customerDB);
	}

	public void delete(Integer id) {
		try {
			factoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Customer has orders!");
		}
	}

	private void updateData(Factory factoryDB, Factory factory) {
		factoryDB.setAddress(factory.getAddress());
		factoryDB.setBankAccountNumber(factory.getBankAccountNumber());
		factoryDB.setContact(factory.getContact());
		factoryDB.setMobilePhones(factory.getMobilePhones());
		factoryDB.setName(factory.getName());
		factoryDB.setQqNumbers(factory.getQqNumbers());
		factoryDB.setTelephones(factory.getTelephones());
	}

}
