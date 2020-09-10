package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {

	ADMINISTRADOR(1, "ADMINISTRADOR");

	private Integer code;

	private String description;

	public static Profile toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Profile ct : Profile.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
