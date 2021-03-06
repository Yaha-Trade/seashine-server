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

import com.seashine.server.domain.Packing;
import com.seashine.server.repositories.PackingRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.PackingSpecs;

@Service
public class PackingService {

	@Autowired
	private PackingRepository packingRepository;

	public Packing findById(Integer id) {
		Optional<Packing> obj = packingRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Packing.class.getName()));
	}

	public List<Packing> findAll() {
		return packingRepository.findAll();
	}

	public Page<Packing> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String englishName, String chineseName) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return packingRepository.findAll(getFilters(englishName, chineseName), pageRequest);
	}

	@Transactional
	public Packing insert(Packing packing) {
		packing.setId(null);
		packing = packingRepository.save(packing);

		return packing;
	}

	public Packing update(Packing packing) {
		Packing packingDB = findById(packing.getId());
		updateData(packingDB, packing);
		return packingRepository.save(packingDB);
	}

	public void delete(Integer id) {
		try {
			packingRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Packing has products!");
		}
	}

	private void updateData(Packing packingDB, Packing packing) {
		packingDB.setEnglishName(packing.getEnglishName());
		packingDB.setChineseName(packing.getChineseName());
	}

	private Specification<Packing> getFilters(String englishName, String chineseName) {
		Specification<Packing> packingSpecs = Specification.where(null);

		if (!englishName.equals("")) {
			packingSpecs = packingSpecs.and(PackingSpecs.filterLikeByEnglishName(englishName));
		}

		if (!chineseName.equals("")) {
			packingSpecs = packingSpecs.and(PackingSpecs.filterLikeByChineseName(chineseName));
		}

		return packingSpecs;
	}

}
