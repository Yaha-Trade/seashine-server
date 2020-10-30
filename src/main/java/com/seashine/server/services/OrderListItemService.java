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

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.repositories.OrderListItemRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.OrderListItemSpecs;

@Service
public class OrderListItemService {

	@Autowired
	private OrderListItemRepository orderListItemRepository;

	@Autowired
	private OrderListService orderListService;

	@Autowired
	private ProductService productService;

	public OrderListItem findById(Integer id) {
		Optional<OrderListItem> obj = orderListItemRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + OrderListItem.class.getName()));
	}

	public Page<OrderListItem> getOrderListItems(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, Integer id, String factory, String productReference, String productDescription) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(getItemFilters(id, factory, productReference, productDescription),
				pageRequest);
	}

	private Specification<OrderListItem> getItemFilters(Integer orderListId, String factory, String productReference,
			String productDescription) {
		Specification<OrderListItem> orderListSpecs = Specification.where(null);

		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterByOrderListId(orderListId));

		if (!productReference.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByProductReference(productReference));
		}

		if (!productDescription.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByProductDescription(productDescription));
		}

		if (!factory.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByFactoryName(factory));
		}

		return orderListSpecs;
	}

	@Transactional
	public OrderListItem insert(OrderListItem orderListItem, Integer idOrderList) {
		orderListItem.setId(null);
		orderListItem = orderListItemRepository.save(orderListItem);

		orderListService.updateTotals(orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "")),
				idOrderList);

		return orderListItem;
	}

	public OrderListItem update(OrderListItem orderListItem, Integer idOrderList) {
		OrderListItem orderListItemDB = findById(orderListItem.getId());
		updateData(orderListItemDB, orderListItem);
		orderListItemRepository.save(orderListItemDB);

		orderListService.updateTotals(orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "")),
				idOrderList);

		return orderListItemDB;
	}

	private void updateData(OrderListItem orderListItemDB, OrderListItem orderListItem) {
		orderListItemDB.setOrderList(orderListItem.getOrderList());
		orderListItemDB.setProduct(orderListItem.getProduct());
		orderListItemDB.setQuantityOfBoxes(orderListItem.getQuantityOfBoxes());
		orderListItemDB.setQuantityOfPiecesPerBox(orderListItem.getQuantityOfPiecesPerBox());
		orderListItemDB.setTotalCubage(orderListItem.getTotalCubage());
		orderListItemDB.setTotalPrice(orderListItem.getTotalPrice());
		orderListItemDB.setTotalQuantityOfPieces(orderListItem.getTotalQuantityOfPieces());
		orderListItemDB.setUnitPrice(orderListItem.getUnitPrice());
	}

	public void delete(Integer id, Integer idOrderList) {
		try {
			OrderListItem orderListItem = findById(id);
			orderListItemRepository.delete(orderListItem);
			orderListService.updateTotals(orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "")),
					idOrderList);

			productService.delete(orderListItem.getProduct().getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderListItem has orders!");
		}
	}

	public Integer checkIfProductIsInInOrder(Integer idOrderList, Integer idProductParent) {
		Specification<OrderListItem> orderListItemSpecs = Specification.where(null);
		orderListItemSpecs = orderListItemSpecs.and(getFilters(idProductParent, ""));
		orderListItemSpecs = orderListItemSpecs.and(getItemFilters(idOrderList, "", "", ""));

		List<OrderListItem> orderItemList = orderListItemRepository.findAll(orderListItemSpecs);

		return orderItemList.size() > 0 ? orderItemList.get(0).getId() : -1;
	}

	public Page<OrderListItem> findByParentProductId(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, Integer parentProductId, String customer) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(getFilters(parentProductId, customer), pageRequest);
	}

	private String checkOrderBy(String orderBy) {
		switch (orderBy) {
		case "customer":
			return "orderList.season.customer.name";
		case "factory":
			return "product.factory.name";
		}

		return orderBy;
	}

	private Specification<OrderListItem> getFilters(Integer parentProductId, String customer) {
		Specification<OrderListItem> orderListItemSpecs = Specification.where(null);

		if (!customer.equals("")) {
			orderListItemSpecs = orderListItemSpecs.and(OrderListItemSpecs.filterLikeByCustomerName(customer));
		}

		orderListItemSpecs = orderListItemSpecs.and(OrderListItemSpecs.filterByParentProductId(parentProductId));

		return orderListItemSpecs;
	}
}
