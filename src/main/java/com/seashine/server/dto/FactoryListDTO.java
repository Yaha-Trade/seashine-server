package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.Factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactoryListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String address;

	private String contact;

	private String bankAccountNumber;

	public FactoryListDTO(Factory factory) {
		this.id = factory.getId();
		this.name = factory.getName();
		this.address = factory.getAddress();
		this.contact = factory.getContact();
		this.bankAccountNumber = factory.getBankAccountNumber();
	}
}
