package com.seashine.server.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.seashine.server.domain.enums.Language;
import com.seashine.server.domain.enums.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iduser;

	private String name;

	private String email;

	private String login;

	private String password;
	
	private Language language;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROFILE")
	private Set<Integer> profiles = new HashSet<Integer>();

	public Set<Profile> getProfilesObject() {
		return this.profiles.stream().map(profile -> Profile.toEnum(profile)).collect(Collectors.toSet());
	}
}
