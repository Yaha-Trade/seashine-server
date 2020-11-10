package com.seashine.server.specs;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.User;

public class UserSpecs {

	public static Specification<User> filterLikeByName(String name) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("name"), Utils.getLike(name));
			return equalPredicate;
		};
	}

	public static Specification<User> filterLikeByEmail(String email) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("email"), Utils.getLike(email));
			return equalPredicate;
		};
	}

	public static Specification<User> filterLikeByLogin(String login) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("login"), Utils.getLike(login));
			return equalPredicate;
		};
	}
}
