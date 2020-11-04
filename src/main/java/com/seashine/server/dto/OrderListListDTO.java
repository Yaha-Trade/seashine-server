package com.seashine.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import com.seashine.server.domain.OrderList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer status;

	private Date purchaseDate;

	private String season;

	private String customer;

	private BigDecimal totalPrice;

	private BigDecimal totalCubage;

	private Integer totalOfBoxes;

	private Integer totalOfReferences;

	private Integer quantityOfProducts;

	private Integer quantityOfContainers;

	public OrderListListDTO(OrderList orderList) {
		this.id = orderList.getId();
		this.name = orderList.getName();
		this.status = orderList.getStatus();
		this.purchaseDate = orderList.getPurchaseDate();
		this.season = orderList.getSeason().getName();
		this.customer = orderList.getSeason().getCustomer().getName();
		this.totalPrice = orderList.getTotalPrice();
		this.totalCubage = orderList.getTotalCubage();
		this.totalOfBoxes = orderList.getTotalOfBoxes();
		this.totalOfReferences = orderList.getTotalOfReferences();
		this.quantityOfProducts = orderList.getQuantityOfProducts();
		this.quantityOfContainers = orderList.getQuantityOfContainers();
	}
}
