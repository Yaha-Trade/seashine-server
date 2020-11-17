package com.seashine.server.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

import com.seashine.server.domain.History;
import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.enums.OrderHistoryAction;
import com.seashine.server.domain.enums.OrderStatus;
import com.seashine.server.repositories.OrderListRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.OrderListSpecs;

@Service
public class OrderListService {

	@Autowired
	private OrderListRepository orderListRepository;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	public OrderList findById(Integer id) {
		Optional<OrderList> obj = orderListRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + OrderList.class.getName()));
	}

	public List<OrderList> findAll() {
		return orderListRepository.findAll();
	}

	public Page<OrderList> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name, String customer, String season, Boolean isApproval) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListRepository.findAll(getFilters(name, customer, season, isApproval), pageRequest);
	}

	@Transactional
	public OrderList insert(OrderList orderList, Integer userId) {
		History history = addHistory(userId, OrderHistoryAction.CREATED);

		orderList.setId(null);
		orderList.setQuantityOfContainers(0);
		orderList.setQuantityOfProducts(0);
		orderList.setTotalCubage(new BigDecimal("0"));
		orderList.setTotalPrice(new BigDecimal("0"));
		orderList.setTotalOfReferences(0);
		orderList.setTotalOfBoxes(0);
		orderList.setStatus(OrderStatus.OPENED.getCode());
		orderList.getHistories().add(history);
		orderList = orderListRepository.save(orderList);

		return orderList;
	}

	public OrderList update(OrderList orderList, Integer userId) {
		OrderList orderListDB = findById(orderList.getId());
		updateData(orderListDB, orderList, userId);
		return orderListRepository.save(orderListDB);
	}

	public void delete(Integer id) {
		try {
			OrderList orderListDB = findById(id);
			orderListDB.getHistories().size();
			List<History> histories = orderListDB.getHistories();
			orderListRepository.deleteById(id);
			historyService.deleteAll(histories);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderList has products!");
		}
	}

	private void updateData(OrderList orderListDB, OrderList orderList, Integer userId) {
		orderListDB.setName(orderList.getName());
		orderListDB.setPurchaseDate(orderList.getPurchaseDate());
		orderListDB.setSeason(orderList.getSeason());
		orderListDB.setHistories(orderList.getHistories());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.EDITED));
	}

	private Specification<OrderList> getFilters(String name, String customer, String season, Boolean isApproval) {
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

		if (isApproval) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterByStatusApproval());
		}

		return orderListSpecs;
	}

	public void updateTotals(List<OrderListItem> orderItems, Integer idOrderList, Integer userId) {
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

		update(orderList, userId);
	}

	private String checkOrderBy(String orderBy) {
		switch (orderBy) {
		case "customer":
			return "season.customer.name";
		}

		return orderBy;
	}

	public OrderList sendToApproval(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.ON_APPROVAL.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.SENT_TO_APPROVAL));

		return orderListRepository.save(orderListDB);
	}

	public OrderList approve(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.APPROVED.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.APPROVED));

		return orderListRepository.save(orderListDB);
	}

	public OrderList reprove(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.REPROVED.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.REPROVED));

		return orderListRepository.save(orderListDB);
	}

	private History addHistory(Integer userId, OrderHistoryAction value) {
		String message = "";

		switch (value) {
		case CREATED:
			message = "ordercreated";
			break;

		case EDITED:
			message = "orderedited";
			break;

		case SENT_TO_APPROVAL:
			message = "senttoapproval";
			break;

		case APPROVED:
			message = "approvedtheorder";
			break;

		case REPROVED:
			message = "reprovedtheorder";
			break;
		}

		History history = new History(null, new Timestamp(System.currentTimeMillis()), userService.findById(userId),
				message, 1);
		history = historyService.insert(history);

		return history;
	}

}
