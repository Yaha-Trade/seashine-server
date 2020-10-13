package com.seashine.server.dto;

import java.io.Serializable;
import java.sql.Date;

import com.seashine.server.domain.Season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Date scheduledDate;

	private String customerName;

	public SeasonListDTO(Season season) {
		this.id = season.getId();
		this.name = season.getName();
		this.scheduledDate = season.getScheduledDate();
		this.customerName = season.getCustomer().getName();
	}
}
