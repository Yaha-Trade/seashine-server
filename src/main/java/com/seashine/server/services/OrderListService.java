package com.seashine.server.services;

import java.math.BigDecimal;
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
import com.seashine.server.domain.OrderListItem;
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
			String name, String customer, String season) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListRepository.findAll(getFilters(name, customer, season), pageRequest);
	}

	@Transactional
	public OrderList insert(OrderList orderList) {
		orderList.setId(null);
		orderList.setQuantityOfContainers(0);
		orderList.setQuantityOfProducts(0);
		orderList.setTotalCubage(new BigDecimal("0"));
		orderList.setTotalPrice(new BigDecimal("0"));
		orderList.setTotalOfReferences(0);
		orderList.setTotalOfBoxes(0);
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
			throw new DataIntegrityException("OrderList has products!");
		}
	}

	private void updateData(OrderList orderListDB, OrderList orderList) {
		orderListDB.setName(orderList.getName());
		orderListDB.setPurchaseDate(orderList.getPurchaseDate());
		orderListDB.setSeason(orderList.getSeason());
		orderListDB.setStatus(orderList.getStatus());
	}

	private Specification<OrderList> getFilters(String name, String customer, String season) {
		Specification<OrderList> orderListSpecs = Specification.where(null);

		if (!name.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeByName(name));
		}

		if (!customer.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeByCustomerName(customer));
		}

		if (!season.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeBySeasonName(season));
		}

		return orderListSpecs;
	}

	public void updateTotals(List<OrderListItem> orderItems, Integer idOrderList) {
		BigDecimal totalPrice = new BigDecimal("0");
		BigDecimal totalCubage = new BigDecimal("0");
		Integer quantityOfProducts = 0;
		Integer totalOfReferences = 0;
		Integer totalOfBoxes = 0;

		for (OrderListItem orderListItem : orderItems) {
			totalPrice = totalPrice.add(orderListItem.getTotalPrice());
			totalCubage = totalCubage.add(orderListItem.getTotalCubage());
			quantityOfProducts += orderListItem.getTotalQuantityOfPieces();
			totalOfBoxes += orderListItem.getQuantityOfBoxes();
			totalOfReferences++;
		}

		OrderList orderList = findById(idOrderList);
		orderList.setQuantityOfContainers((int) Math.ceil(totalCubage.doubleValue() / 67));
		orderList.setQuantityOfProducts(quantityOfProducts);
		orderList.setTotalCubage(totalCubage);
		orderList.setTotalPrice(totalPrice);
		orderList.setTotalOfReferences(totalOfReferences);
		orderList.setTotalOfBoxes(totalOfBoxes);

		update(orderList);
	}

	private String checkOrderBy(String orderBy) {
		switch (orderBy) {
		case "customer":
			return "season.customer.name";
		}

		return orderBy;
	}
}
