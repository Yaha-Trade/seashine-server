package com.seashine.server.dto;

import java.io.Serializable;

import com.seashine.server.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String login;

	private String email;

	public UserListDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.login = user.getLogin();
		this.email = user.getEmail();
	}
}
