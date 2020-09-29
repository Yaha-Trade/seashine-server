package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.Factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactorySelectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	public FactorySelectDTO(Factory factory) {
		this.id = factory.getId();
		this.name = factory.getName();
	}
}
