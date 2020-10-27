package com.seashine.server.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.dto.OrderListItemListDTO;
import com.seashine.server.services.OrderListItemService;

@RestController
@RequestMapping(value = "/orderlistitems")
public class OrderListItemResource {

	@Autowired
	private OrderListItemService orderListItemService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderListItemListDTO> findById(@PathVariable Integer id) {
		OrderListItem orderListItem = orderListItemService.findById(id);

		return ResponseEntity.ok().body(new OrderListItemListDTO(orderListItem));
	}

	@RequestMapping(value = "/{idOrder}/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> getOrderListItems(@PathVariable Integer idOrder,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "quantity") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListItemService.getOrderListItems(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), idOrder);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}

	@RequestMapping(value = "check/{idOrderList}/{idProduct}", method = RequestMethod.GET)
	public ResponseEntity<Integer> update(@PathVariable Integer idOrderList, @PathVariable Integer idProduct) {

		return ResponseEntity.ok().body(orderListItemService.checkIfProductIsInInOrder(idOrderList, idProduct));
	}

	@RequestMapping(value = "purchasehistory/{parentProductId}/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> findByParentProductId(@PathVariable Integer parentProductId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "product") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "customer", defaultValue = "") String customer) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListItemService.findByParentProductId(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), parentProductId, customer);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrderListItem orderListItem) {
		orderListItem = orderListItemService.insert(orderListItem, orderListItem.getOrderList().getId());

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderListItem.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "{idOrderList}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrderListItem orderListItem, @PathVariable Integer id,
			@PathVariable Integer idOrderList) {
		orderListItem.setId(id);
		orderListItem = orderListItemService.update(orderListItem, idOrderList);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "{idOrder}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer idOrder, @PathVariable Integer id) {
		orderListItemService.delete(id, idOrder);

		return ResponseEntity.noContent().build();
	}
}
