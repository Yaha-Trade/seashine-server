package com.seashine.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String reference;

	private String description;

	private Integer quantityInner;

	private Integer quantityOfPieces;

	private BigDecimal price;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxLength;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxWidth;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxHeight;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal packingLength;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal packingWidth;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal packingHeight;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal productLength;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal productWidth;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal productHeight;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxCubage;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxGrossWeight;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal boxNetWeight;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal netWeightWithPacking;

	@Column(columnDefinition = "Decimal(19,3)")
	private BigDecimal netWeightWithoutPacking;

	private Integer quantityOfBoxesPerContainer;

	private Integer quantityOfPiecesPerContainer;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACKAGEID")
	private Packing packing;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACTORYID")
	private Factory factory;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATIONID")
	private Certification certification;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images = new ArrayList<Image>();

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Remark> remarks = new ArrayList<Remark>();

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Product parentProduct;
}
