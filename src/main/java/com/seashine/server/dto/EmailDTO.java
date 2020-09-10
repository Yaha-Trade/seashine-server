package com.seashine.server.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Email(message = "Invalid e-mail")
	@NotEmpty(message = "Required field")
	private String email;

}
