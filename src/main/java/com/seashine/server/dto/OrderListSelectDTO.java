package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.OrderList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListSelectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	public OrderListSelectDTO(OrderList orderList) {
		this.id = orderList.getId();
		this.name = orderList.getName();
	}
}
