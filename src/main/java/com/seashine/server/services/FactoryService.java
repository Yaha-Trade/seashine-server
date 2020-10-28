package com.seashine.server.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Factory;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.FactorySpecs;

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

	public Page<Factory> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name, String address, String contact, String bankAccountNumber) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return factoryRepository.findAll(getFilters(name, address, contact, bankAccountNumber), pageRequest);
	}

	@Transactional
	public Factory insert(Factory factory) {
		factory.setId(null);
		factory = factoryRepository.save(factory);

		return factory;
	}

	public Factory update(Factory factory) {
		Factory factoryDB = findById(factory.getId());
		updateData(factoryDB, factory);
		return factoryRepository.save(factoryDB);
	}

	public void delete(Integer id) {
		try {
			factoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Factory has products!");
		}
	}

	private void updateData(Factory factoryDB, Factory factory) {
		factoryDB.setAddress(factory.getAddress());
		factoryDB.setBankAccountNumber(factory.getBankAccountNumber());
		factoryDB.setContact(factory.getContact());
		factoryDB.setName(factory.getName());
		factoryDB.setTelephone1(factory.getTelephone1());
		factoryDB.setTelephone2(factory.getTelephone2());
		factoryDB.setTelephone3(factory.getTelephone3());
		factoryDB.setMobilePhone1(factory.getMobilePhone1());
		factoryDB.setMobilePhone2(factory.getMobilePhone2());
		factoryDB.setMobilePhone3(factory.getMobilePhone3());
		factoryDB.setQqNumber1(factory.getQqNumber1());
		factoryDB.setQqNumber2(factory.getQqNumber2());
		factoryDB.setQqNumber3(factory.getQqNumber3());
	}

	private Specification<Factory> getFilters(String name, String address, String contact, String bankAccountNumber) {
		Specification<Factory> factorySpecs = Specification.where(null);

		if (!name.equals("")) {
			factorySpecs = factorySpecs.and(FactorySpecs.filterLikeByName(name));
		}

		if (!address.equals("")) {
			factorySpecs = factorySpecs.and(FactorySpecs.filterLikeByAddress(address));
		}

		if (!contact.equals("")) {
			factorySpecs = factorySpecs.and(FactorySpecs.filterLikeByContact(contact));
		}

		if (!bankAccountNumber.equals("")) {
			factorySpecs = factorySpecs.and(FactorySpecs.filterLikeByBankAccountNumber(bankAccountNumber));
		}

		return factorySpecs;
	}

}
