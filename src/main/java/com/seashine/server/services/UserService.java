package com.seashine.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.User;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.security.UserSS;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findById(Integer id) {
		Optional<User> obj = userRepository.findById(id);

		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Class: " + User.class.getName()));
	}

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
