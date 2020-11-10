package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

	OPENED(0, "OPENED"), ON_APPROVAL(1, "ON_APPROVAL"), APPROVED(2, "APPROVED"), RECEIVED(3, "RECEIVED"),
	PRODUCTION_STARTED(4, "PRODUCTION_STARTED"), PRODUCTION_FINISHED(5, "PRODUCTION_FINISHED"),
	LABELING_STARTED(6, "LABELING_STARTED"), LABELING_FINISHED(7, "LABELING_FINISHED");

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
