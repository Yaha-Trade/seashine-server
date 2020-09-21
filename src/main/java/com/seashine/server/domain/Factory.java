package com.seashine.server.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Factory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String address;

	private String contact;

	private String bankAccountNumber;

	@ElementCollection
	@CollectionTable(name = "TELEPHONE")
	@JsonIgnore
	private Set<String> telephones = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "MOBILEPHONE")
	@JsonIgnore
	private Set<String> mobilePhones = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "QQNUMBER")
	@JsonIgnore
	private Set<String> qqNumbers = new HashSet<>();
}
