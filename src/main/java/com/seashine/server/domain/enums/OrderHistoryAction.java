package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderHistoryAction {

	CREATED(1, "CREATED"), EDITED(2, "EDITED"), SENT_TO_APPROVAL(3, "SENT_TO_APPROVAL"), APPROVED(4, "APPROVED"),
	REPROVED(5, "REPROVED");

	private Integer code;

	private String description;

	public static OrderHistoryAction toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (OrderHistoryAction ct : OrderHistoryAction.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
