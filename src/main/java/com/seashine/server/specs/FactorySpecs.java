package com.seashine.server.specs;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Factory;

public class FactorySpecs {

	public static Specification<Factory> filterLikeByName(String name) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("name"), Utils.getLike(name));
			return equalPredicate;
		};
	}

	public static Specification<Factory> filterLikeByContact(String contact) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("contact"), Utils.getLike(contact));
			return equalPredicate;
		};
	}

	public static Specification<Factory> filterLikeByBankAccountNumber(String bankAccountNumber) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("bankAccountNumber"),
					Utils.getLike(bankAccountNumber));
			return equalPredicate;
		};
	}

	public static Specification<Factory> filterLikeByAddress(String address) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("address"), Utils.getLike(address));
			return equalPredicate;
		};
	}

}
