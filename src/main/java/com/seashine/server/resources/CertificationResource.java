package com.seashine.server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.services.CertificationService;
import com.seashine.server.services.OrderListItemService;

@RestController
@RequestMapping(value = "/certifications")
public class CertificationResource {

	@Autowired
	private CertificationService certificationService;

	@Autowired
	private OrderListItemService orderListItemService;

	@RequestMapping(value = "approve/{idOrderListItem}", method = RequestMethod.POST)
	public ResponseEntity<Void> approve(@PathVariable Integer idOrderListItem) {
		OrderListItem orderListItem = orderListItemService.findById(idOrderListItem);

		certificationService.approve(orderListItem.getProduct().getCertification().getId());

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "reprove/{idOrderListItem}", method = RequestMethod.POST)
	public ResponseEntity<Void> reprove(@PathVariable Integer idOrderListItem) {
		OrderListItem orderListItem = orderListItemService.findById(idOrderListItem);

		certificationService.reprove(orderListItem.getProduct().getCertification().getId());

		return ResponseEntity.noContent().build();
	}
}
