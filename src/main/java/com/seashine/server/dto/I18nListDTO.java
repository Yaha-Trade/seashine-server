package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.I18n;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class I18nListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String textValue;

	private String language;

	public I18nListDTO(I18n i18n) {
		this.id = i18n.getId();
		this.textValue = i18n.getTextValue();
		this.language = i18n.getLanguage().getName();
	}
}
