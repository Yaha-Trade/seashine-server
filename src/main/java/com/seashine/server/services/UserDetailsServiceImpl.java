package com.seashine.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.User;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(login);

		if (user == null) {
			throw new UsernameNotFoundException(login);
		}

		return new UserSS(user.getIduser(), user.getLogin(), user.getPassword(), user.getProfilesObject());
	}

}
