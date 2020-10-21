package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.OrderListItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String factoryName;

	private String productReference;

	private String productDescription;

	private Integer quantityOfBoxesOrder;

	public OrderListItemListDTO(OrderListItem orderListItem) {
		this.id = orderListItem.getId();
		this.factoryName = orderListItem.getProduct().getFactory().getName();
		this.productReference = orderListItem.getProduct().getReference();
		this.productDescription = orderListItem.getProduct().getDescription();
		this.quantityOfBoxesOrder = orderListItem.getProduct().getQuantityOfBoxesOrder();
	}
}
