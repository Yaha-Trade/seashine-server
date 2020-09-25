package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String reference;

	private String description;

	public ProductListDTO(Product product) {
		this.id = product.getId();
		this.reference = product.getReference();
		this.description = product.getDescription();
	}
}
