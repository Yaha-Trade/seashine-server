package com.seashine.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LabelingStatus {

	OPENED(0, "OPENED"), ON_APPROVAL(1, "ON_APPROVAL"), APPROVED(2, "APPROVED"), REPROVED(3, "REPROVED");

	private Integer code;

	private String description;

	public static LabelingStatus toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (LabelingStatus ct : LabelingStatus.values()) {
			if (cod.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
