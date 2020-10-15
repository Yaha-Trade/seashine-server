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

import com.seashine.server.domain.OrderList;
import com.seashine.server.repositories.OrderListRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.OrderListSpecs;

@Service
public class OrderListService {

	@Autowired
	private OrderListRepository orderListRepository;

	public OrderList findById(Integer id) {
		Optional<OrderList> obj = orderListRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + OrderList.class.getName()));
	}

	public List<OrderList> findAll() {
		return orderListRepository.findAll();
	}

	public Page<OrderList> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return orderListRepository.findAll(getFilters(name), pageRequest);
	}

	@Transactional
	public OrderList insert(OrderList orderList) {
		orderList.setId(null);
		orderList = orderListRepository.save(orderList);

		return orderList;
	}

	public OrderList update(OrderList orderList) {
		OrderList orderListDB = findById(orderList.getId());
		updateData(orderListDB, orderList);
		return orderListRepository.save(orderListDB);
	}

	public void delete(Integer id) {
		try {
			orderListRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderList has orders!");
		}
	}

	private void updateData(OrderList orderListDB, OrderList orderList) {
		orderListDB.setName(orderList.getName());
		orderListDB.setPurchaseDate(orderList.getPurchaseDate());
		orderListDB.setSeason(orderList.getSeason());
		orderListDB.setStatus(orderList.getStatus());
	}

	private Specification<OrderList> getFilters(String name) {
		Specification<OrderList> orderListSpecs = Specification.where(null);

		if (!name.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeByName(name));
		}

		return orderListSpecs;
	}

}