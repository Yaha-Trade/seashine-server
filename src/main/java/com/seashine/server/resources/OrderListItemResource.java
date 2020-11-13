package com.seashine.server.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@RequestMapping(value = "images/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> getAllOrderListsItems(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "quantity") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "factory", defaultValue = "") String factory,
			@RequestParam(value = "productReference", defaultValue = "") String productReference,
			@RequestParam(value = "productDescription", defaultValue = "") String productDescription,
			@RequestParam(value = "customer", defaultValue = "") String customer,
			@RequestParam(value = "season", defaultValue = "") String season,
			@RequestParam(value = "order", defaultValue = "") String order,
			@RequestParam(value = "quantityOfImages", defaultValue = "") String quantityOfImages) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListItemService.getAllOrderListsItems(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), customer, season, order, factory,
				productReference, productDescription, quantityOfImages);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}

	@RequestMapping(value = "/{idOrder}/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> getOrderListItems(@PathVariable Integer idOrder,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "quantity") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "factory", defaultValue = "") String factory,
			@RequestParam(value = "productReference", defaultValue = "") String productReference,
			@RequestParam(value = "productDescription", defaultValue = "") String productDescription) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListItemService.getOrderListItems(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), idOrder, factory, productReference,
				productDescription);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}

	@RequestMapping(value = "checkproductinorder/{idOrderList}/{idProduct}", method = RequestMethod.GET)
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
	public ResponseEntity<Void> insert(@Valid @RequestBody OrderListItem orderListItem,
			@RequestHeader("userId") Integer userId) {
		orderListItem = orderListItemService.insert(orderListItem, orderListItem.getOrderList().getId(), userId);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderListItem.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "{idOrderList}/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrderListItem orderListItem, @PathVariable Integer id,
			@PathVariable Integer idOrderList, @RequestHeader("userId") Integer userId) {
		orderListItem.setId(id);
		orderListItem = orderListItemService.update(orderListItem, idOrderList, userId);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "{idOrder}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer idOrder, @PathVariable Integer id,
			@RequestHeader("userId") Integer userId) {
		orderListItemService.delete(id, idOrder, userId);

		return ResponseEntity.noContent().build();
	}
}
