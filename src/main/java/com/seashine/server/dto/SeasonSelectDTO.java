package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.Season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonSelectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	public SeasonSelectDTO(Season season) {
		this.id = season.getId();
		this.name = season.getName();
	}
}
