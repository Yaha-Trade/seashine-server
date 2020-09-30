package com.seashine.server.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	private Double price;

	private Double boxLength;

	private Double boxWidth;

	private Double boxHeight;

	private Double packingLength;

	private Double packingWidth;

	private Double packingHeight;

	private Double productLength;

	private Double productWidth;

	private Double productHeight;

	private Double boxCubage;

	private Double boxGrossWeight;

	private Double boxNetWeight;

	private Double netWeightWithPacking;

	private Double netWeightWithoutPacking;

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
}
