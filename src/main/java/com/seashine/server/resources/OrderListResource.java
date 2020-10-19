package com.seashine.server.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.dto.OrderListItemListDTO;
import com.seashine.server.dto.OrderListListDTO;
import com.seashine.server.dto.OrderListSelectDTO;
import com.seashine.server.services.OrderListService;

@RestController
@RequestMapping(value = "/orderlists")
public class OrderListResource {

	@Autowired
	private OrderListService orderListService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderListSelectDTO>> findAll() {
		List<OrderList> orderLists = orderListService.findAll();

		List<OrderListSelectDTO> orderListsDTO = orderLists.stream().map(orderList -> new OrderListSelectDTO(orderList))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(orderListsDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderList> findById(@PathVariable Integer id) {
		OrderList orderList = orderListService.findById(id);

		return ResponseEntity.ok().body(orderList);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListListDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "name", defaultValue = "") String name) {

		Page<OrderList> orderLists = (Page<OrderList>) orderListService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), name);

		Page<OrderListListDTO> orderListDTO = orderLists.map(orderList -> new OrderListListDTO(orderList));

		return ResponseEntity.ok().body(orderListDTO);
	}

	@RequestMapping(value = "/{id}/productorder/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListItemListDTO>> getOrderListItems(@PathVariable Integer id,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "quantity") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection) {

		Page<OrderListItem> orderListItems = (Page<OrderListItem>) orderListService.getOrderListItems(page,
				linesPerPage, orderBy, orderByDirection.toUpperCase(), id);

		Page<OrderListItemListDTO> orderListItemDTO = orderListItems
				.map(orderListItem -> new OrderListItemListDTO(orderListItem));

		return ResponseEntity.ok().body(orderListItemDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrderList orderList) {
		orderList = orderListService.insert(orderList);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderList.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrderList orderList, @PathVariable Integer id) {
		orderList.setId(id);
		orderList = orderListService.update(orderList);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		orderListService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
