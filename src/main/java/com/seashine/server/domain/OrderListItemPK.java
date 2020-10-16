package com.seashine.server.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderListItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ORDERID")
	private OrderList order;

	@ManyToOne
	@JoinColumn(name = "PRODUCTID")
	private Product product;
}
