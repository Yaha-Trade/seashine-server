package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

	ENGLISH(0, "ENGLISH"),

	CHINESE(1, "CHINESE");

	private Integer code;

	private String description;

	public static Language toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Language ct : Language.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
