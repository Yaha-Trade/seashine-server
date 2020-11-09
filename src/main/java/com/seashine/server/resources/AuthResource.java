package com.seashine.server.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.dto.EmailDTO;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.security.JWTUtil;
import com.seashine.server.security.UserSS;
import com.seashine.server.services.AuthService;
import com.seashine.server.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDto) {
		authService.sendNewPassword(emailDto.getEmail());

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/getUserId", method = RequestMethod.GET)
	public ResponseEntity<Integer> userId(@RequestParam(value = "login") String login) {
		return ResponseEntity.ok().body(userRepository.findByLogin(login).getId());
	}
}
