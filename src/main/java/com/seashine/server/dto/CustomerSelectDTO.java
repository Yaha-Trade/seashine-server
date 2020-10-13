package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSelectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	public CustomerSelectDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
	}
}
