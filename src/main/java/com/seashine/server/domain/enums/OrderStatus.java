package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

	NO_STATUS(0, "NO_STATUS"), CONFIRMED(1, "DEFAULT"), RECEIVED(2, "RECEIVED"),
	PRODUCTION_STARTED(3, "PRODUCTION_STARTED"), PRODUCTION_FINISHED(4, "PRODUCTION_FINISHED"),
	LABELING_STARTED(5, "LABELING_STARTED"), LABELING_FINISHED(6, "LABELING_FINISHED");

	private Integer code;

	private String description;

	public static OrderStatus toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (OrderStatus ct : OrderStatus.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
