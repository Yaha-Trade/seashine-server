package com.seashine.server.resources;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.OrderList;
import com.seashine.server.dto.OrderListListDTO;
import com.seashine.server.dto.OrderListSelectDTO;
import com.seashine.server.services.OrderListService;
import com.seashine.server.services.UserService;

@RestController
@RequestMapping(value = "/orderlists")
public class OrderListResource {

	@Autowired
	private OrderListService orderListService;

	@Autowired
	private UserService userService;

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
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "customer", defaultValue = "") String customer,
			@RequestParam(value = "season", defaultValue = "") String season) {

		return ResponseEntity.ok().body(
				getPages(page, linesPerPage, orderBy, orderByDirection.toUpperCase(), name, customer, season, false));
	}

	@RequestMapping(value = "approval/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OrderListListDTO>> orderListApproval(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "customer", defaultValue = "") String customer,
			@RequestParam(value = "season", defaultValue = "") String season) {

		return ResponseEntity.ok().body(
				getPages(page, linesPerPage, orderBy, orderByDirection.toUpperCase(), name, customer, season, true));
	}

	private Page<OrderListListDTO> getPages(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name, String customer, String season, Boolean isApproval) {

		Page<OrderList> orderLists = (Page<OrderList>) orderListService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), name, customer, season, isApproval);

		return orderLists.map(orderList -> new OrderListListDTO(orderList));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrderList orderList,
			@RequestHeader("userId") Integer userId) {
		orderList = orderListService.insert(orderList, userId);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderList.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrderList orderList, @PathVariable Integer id,
			@RequestHeader("userId") Integer userId) {
		orderList.setId(id);
		orderList = orderListService.update(orderList, userId);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		orderListService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "sendtoapproval/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> sendToApproval(@PathVariable Integer id, @RequestHeader("userId") Integer userId) {
		orderListService.sendToApproval(id, userId);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "approve/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> approve(@PathVariable Integer id, @RequestHeader("userId") Integer userId) {
		orderListService.approve(id, userId);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "reprove/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> reprove(@PathVariable Integer id, @RequestHeader("userId") Integer userId) {
		orderListService.reprove(id, userId);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> export(@PathVariable Integer id,
			@RequestHeader("userId") Integer userId) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + UUID.randomUUID().toString() + ".xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(orderListService.exportOrder(id, userService.findById(userId)));
	}
}
