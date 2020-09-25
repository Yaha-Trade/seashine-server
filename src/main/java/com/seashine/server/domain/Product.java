package com.seashine.server.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	private Integer price;

	private Long boxLength;

	private Long boxWidth;

	private Long boxHeight;

	private Long packingLength;

	private Long packingWidth;

	private Long packingHeight;

	private Long productLength;

	private Long productWidth;

	private Long productHeight;

	private Long boxCubage;

	private Long boxGrossWeight;

	private Long boxNetWeight;

	private Long netWeightWithPacking;

	private Long netWeightWithoutPacking;

	private Long quantityOfBoxesPerContainer;

	private Long quantityOfPiecesPerContainer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACKAGEID")
	private Packing packing;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACTORYID")
	private Factory factory;
}
