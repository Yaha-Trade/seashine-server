package com.seashine.server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.dto.OrderListItemListDTO;
import com.seashine.server.services.OrderListItemService;

@RestController
@RequestMapping(value = "/orderlistitems")
public class OrderListItemResource {

	@Autowired
	private OrderListItemService orderListItemService;

	@RequestMapping(value = "/{id}/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> getOrderListItems(@PathVariable Integer id,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "quantity") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListItemService.getOrderListItems(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), id);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}
}
