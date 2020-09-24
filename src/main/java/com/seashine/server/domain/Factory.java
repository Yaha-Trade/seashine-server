package com.seashine.server.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private String telephone1;

	private String telephone2;

	private String telephone3;

	private String mobilePhone1;

	private String mobilePhone2;

	private String mobilePhone3;

	private String qqNumber1;

	private String qqNumber2;

	private String qqNumber3;
}
