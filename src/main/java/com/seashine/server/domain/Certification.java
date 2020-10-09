package com.seashine.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Certification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String englishDescription;

	private Integer quantityOfParts;

	private String composition;

	private Integer model;

	private String color;

	private Integer sound;

	private Integer light;

	private Integer motor;

	private Integer metalPart;

	private Integer clip;

	private Integer line;

	private String specialRequirements;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<BatteryData> batteries = new ArrayList<>();
}
