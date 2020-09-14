package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Languages {

	ENGLISH(1, "ENGLISH"), CHINESE(2, "CHINESE");

	private Integer code;

	private String description;

	public static Languages toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Languages ct : Languages.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
