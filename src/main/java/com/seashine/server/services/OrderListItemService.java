package com.seashine.server.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.repositories.OrderListItemRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.specs.OrderListItemSpecs;

@Service
public class OrderListItemService {

	@Autowired
	private OrderListItemRepository orderListItemRepository;

	@Autowired
	private OrderListService orderListService;

	public Page<OrderListItem> getOrderListItems(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, Integer id) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return orderListItemRepository.findAll(getItemFilters(id), pageRequest);
	}

	private Specification<OrderListItem> getItemFilters(Integer id) {
		Specification<OrderListItem> orderListSpecs = Specification.where(null);

		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterByOrderListId(id));

		return orderListSpecs;
	}

	@Transactional
	public OrderListItem insert(OrderListItem orderListItem, Integer idOrderList) {
		orderListItem.setId(null);
		orderListItem = orderListItemRepository.save(orderListItem);

		orderListService.updateTotals(orderListItemRepository.findAll(getItemFilters(idOrderList)), idOrderList);

		return orderListItem;
	}

	public void delete(Integer id, Integer idOrderList) {
		try {
			orderListItemRepository.deleteById(id);
			orderListService.updateTotals(orderListItemRepository.findAll(getItemFilters(idOrderList)), idOrderList);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderListItem has orders!");
		}
	}
}
