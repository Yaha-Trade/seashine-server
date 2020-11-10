package com.seashine.server.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.seashine.server.config.SecurityConfig;
import com.seashine.server.domain.User;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.security.UserSS;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.UserSpecs;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityConfig pe;

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

	public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection, String name,
			String email, String login) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return userRepository.findAll(getFilters(name, email, login), pageRequest);
	}

	private Specification<User> getFilters(String name, String email, String login) {
		Specification<User> productsSpecs = Specification.where(null);

		if (!name.equals("")) {
			productsSpecs = productsSpecs.and(UserSpecs.filterLikeByName(name));
		}

		if (!email.equals("")) {
			productsSpecs = productsSpecs.and(UserSpecs.filterLikeByEmail(email));
		}

		if (!login.equals("")) {
			productsSpecs = productsSpecs.and(UserSpecs.filterLikeByLogin(login));
		}

		return productsSpecs;
	}

	@Transactional
	public User insert(User user) {
		user.setId(null);
		user = userRepository.save(user);
		user.setPassword(pe.bCryptPasswordEncoder().encode(user.getPassword()));

		return user;
	}

	public User update(User user) {
		User userDB = findById(user.getId());
		userDB.setName(user.getName());
		userDB.setEmail(user.getEmail());
		userDB.setLogin(user.getLogin());
		if (!user.getPassword().equals("")) {
			userDB.setPassword(pe.bCryptPasswordEncoder().encode(user.getPassword()));
		}
		return userRepository.save(userDB);
	}

	public void delete(Integer id) {
		try {
			userRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can't delete user");
		}
	}

}
