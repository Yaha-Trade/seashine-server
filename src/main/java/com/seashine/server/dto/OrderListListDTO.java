package com.seashine.server.dto;

import java.io.Serializable;
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

	private Date purchaseDate;

	private String seasonName;

	private String customerName;

	public OrderListListDTO(OrderList orderList) {
		this.id = orderList.getId();
		this.name = orderList.getName();
		this.purchaseDate = orderList.getPurchaseDate();
		this.seasonName = orderList.getSeason().getName();
		this.customerName = orderList.getSeason().getCustomer().getName();
	}
}
