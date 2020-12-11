package com.seashine.server.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Production;
import com.seashine.server.domain.enums.ProductionStatus;
import com.seashine.server.repositories.ProductionRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class ProductionService {

	@Autowired
	private ProductionRepository productionRepository;

	public List<Production> findAll() {
		return productionRepository.findAll();
	}

	public Production findById(Integer id) {
		Optional<Production> obj = productionRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Production.class.getName()));
	}

	public @Valid Production update(@Valid Production production) {
		Production productionDB = findById(production.getId());
		productionDB.setDeliveryDate(production.getDeliveryDate());
		productionDB.setReceveidDate(production.getReceveidDate());
		productionDB.setQualityInspectionRequirements(production.getQualityInspectionRequirements());
		productionDB.setOrderTerms(production.getOrderTerms());
		productionDB.setStatus(ProductionStatus.PRODUCTION_STARTED.getCode());

		return productionRepository.save(productionDB);
	}

}
