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

import com.seashine.server.domain.Season;
import com.seashine.server.repositories.SeasonRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.SeasonSpecs;

@Service
public class SeasonService {

	@Autowired
	private SeasonRepository seasonRepository;

	public Season findById(Integer id) {
		Optional<Season> obj = seasonRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Season.class.getName()));
	}

	public List<Season> findAll() {
		return seasonRepository.findAll();
	}

	public Page<Season> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name, String customer) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return seasonRepository.findAll(getFilters(name, customer), pageRequest);
	}

	@Transactional
	public Season insert(Season season) {
		season.setId(null);
		season = seasonRepository.save(season);

		return season;
	}

	public Season update(Season season) {
		Season seasonDB = findById(season.getId());
		updateData(seasonDB, season);
		return seasonRepository.save(seasonDB);
	}

	public void delete(Integer id) {
		try {
			seasonRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Season has orders!");
		}
	}

	private void updateData(Season seasonDB, Season season) {
		seasonDB.setName(season.getName());
		seasonDB.setScheduledDate(season.getScheduledDate());
		seasonDB.setCustomer(season.getCustomer());
	}

	private Specification<Season> getFilters(String name, String customer) {
		Specification<Season> seasonSpecs = Specification.where(null);

		if (!name.equals("")) {
			seasonSpecs = seasonSpecs.and(SeasonSpecs.filterLikeByName(name));
		}

		if (!customer.equals("")) {
			seasonSpecs = seasonSpecs.and(SeasonSpecs.filterLikeByCustomerName(customer));
		}

		return seasonSpecs;
	}

}
