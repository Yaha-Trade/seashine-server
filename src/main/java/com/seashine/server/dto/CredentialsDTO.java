package com.seashine.server.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;

	private String password;
}
