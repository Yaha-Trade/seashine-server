package com.seashine.server.services;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.History;
import com.seashine.server.repositories.HistoryRepository;
import com.seashine.server.services.exception.DataIntegrityException;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	@Transactional
	public History insert(History history) {
		history.setId(null);
		history.setDate(new Timestamp(System.currentTimeMillis()));
		history = historyRepository.save(history);

		return history;
	}

	@Transactional
	public void deleteAll(List<History> histories) {
		try {
			historyRepository.deleteAll(histories);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Couldn't delete history");
		}
	}
}
