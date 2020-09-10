package com.seashine.server.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seashine.server.domain.enums.Profile;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String login;

	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {

	}

	public UserSS(Integer id, String login, String password, Set<Profile> profiles) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.authorities = profiles.stream().map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Integer getId() {
		return id;
	}

	public boolean hasRole(Profile profile) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
	}
}
