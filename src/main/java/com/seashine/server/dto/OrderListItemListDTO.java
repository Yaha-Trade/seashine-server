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

	private String factory;

	private String customer;

	private String season;

	private String order;

	private Integer productId;

	private String productReference;

	private String productDescription;

	private Integer quantityOfBoxes;

	private Integer quantityOfPiecesPerBox;

	private Integer totalQuantityOfPieces;

	private BigDecimal unitPrice;

	private BigDecimal totalPrice;

	private BigDecimal totalCubage;

	private Integer quantityOfImages;

	private Integer certificationStatus;

	private Integer labelingStatus;

	private Integer productionStatus;

	public OrderListItemListDTO(OrderListItem orderListItem) {
		this.id = orderListItem.getId();
		this.factory = orderListItem.getProduct().getFactory().getName();
		this.productId = orderListItem.getProduct().getId();
		this.productReference = orderListItem.getProduct().getReference();
		this.productDescription = orderListItem.getProduct().getDescription();
		this.quantityOfBoxes = orderListItem.getQuantityOfBoxes();
		this.quantityOfPiecesPerBox = orderListItem.getQuantityOfPiecesPerBox();
		this.totalQuantityOfPieces = orderListItem.getTotalQuantityOfPieces();
		this.unitPrice = orderListItem.getUnitPrice();
		this.totalPrice = orderListItem.getTotalPrice();
		this.totalCubage = orderListItem.getTotalCubage();
		this.customer = orderListItem.getOrderList().getSeason().getCustomer().getName();
		this.season = orderListItem.getOrderList().getSeason().getName();
		this.order = orderListItem.getOrderList().getName();
		this.quantityOfImages = orderListItem.getProduct().getQuantityOfImages();
		this.certificationStatus = orderListItem.getProduct().getCertification().getStatus();
		this.labelingStatus = orderListItem.getProduct().getLabelingStatus();
		this.productionStatus = orderListItem.getProduction().getStatus();
	}
}
