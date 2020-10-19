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

	private Integer quantity;

	public OrderListItemListDTO(OrderListItem orderListItem) {
		this.factoryName = orderListItem.getProduct().getFactory().getName();
		this.productReference = orderListItem.getProduct().getReference();
		this.productDescription = orderListItem.getProduct().getDescription();
		this.quantity = orderListItem.getQuantity();
	}
}
