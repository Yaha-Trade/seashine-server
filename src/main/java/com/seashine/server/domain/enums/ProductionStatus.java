package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductionStatus {

	WAITING_START(0, "WAITING_START"), PRODUCTION_STARTED(1, "PRODUCTION_STARTED"),
	PRODUCTION_FINISHED(2, "PRODUCTION_FINISHED");

	private Integer code;

	private String description;

	public static ProductionStatus toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (ProductionStatus ct : ProductionStatus.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
