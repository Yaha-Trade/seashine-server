package com.seashine.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Date purchaseDate;

	private Integer status;

	private BigDecimal totalPrice;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal totalCubage;

	private Integer totalOfBoxes;

	private Integer totalOfReferences;

	private Integer quantityOfProducts;

	private Integer quantityOfContainers;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEASONID")
	private Season season;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy(value = "date desc")
	private List<History> histories = new ArrayList<History>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderList")
	private List<OrderListItem> orderListItems = new ArrayList<OrderListItem>();
}
