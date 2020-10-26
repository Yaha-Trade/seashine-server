package com.seashine.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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

	private String customerName;

	private Integer productId;

	private String productReference;

	private String productDescription;

	private Integer quantityOfBoxes;

	private Integer quantityOfPiecesPerBox;

	private Integer totalQuantityOfPieces;

	private BigDecimal unitPrice;

	private BigDecimal totalPrice;

	private BigDecimal totalCubage;

	public OrderListItemListDTO(OrderListItem orderListItem) {
		this.id = orderListItem.getId();
		this.factoryName = orderListItem.getProduct().getFactory().getName();
		this.productId = orderListItem.getProduct().getId();
		this.productReference = orderListItem.getProduct().getReference();
		this.productDescription = orderListItem.getProduct().getDescription();
		this.quantityOfBoxes = orderListItem.getQuantityOfBoxes();
		this.quantityOfPiecesPerBox = orderListItem.getQuantityOfPiecesPerBox();
		this.totalQuantityOfPieces = orderListItem.getTotalQuantityOfPieces();
		this.unitPrice = orderListItem.getUnitPrice();
		this.totalPrice = orderListItem.getTotalPrice();
		this.totalCubage = orderListItem.getTotalCubage();
		this.customerName = orderListItem.getOrderList().getSeason().getCustomer().getName();
	}
}
