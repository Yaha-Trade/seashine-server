package com.seashine.server.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.User;
import com.seashine.server.dto.UserListDTO;
import com.seashine.server.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable Integer id) {
		User user = userService.findById(id);

		return ResponseEntity.ok().body(user);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<UserListDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "login", defaultValue = "") String login) {

		Page<User> users = (Page<User>) userService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), name, email, login);

		Page<UserListDTO> usersListDTO = users.map(user -> new UserListDTO(user));

		return ResponseEntity.ok().body(usersListDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody User user) {
		user = userService.insert(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Integer id) {
		user.setId(id);
		user = userService.update(user);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		userService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "changelanguage", method = RequestMethod.GET)
	public ResponseEntity<Void> changeLanguage(@RequestHeader("userId") Integer userId,
			@RequestParam(value = "language") Integer idLanguage) {

		userService.changeLanguage(userId, idLanguage);

		return ResponseEntity.noContent().build();
	}

}
