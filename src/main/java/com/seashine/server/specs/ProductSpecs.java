package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Factory;
import com.seashine.server.domain.Product;

public class ProductSpecs {

	public static Specification<Product> filterLikeByReference(String reference) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("reference"), Utils.getLike(reference));
			return equalPredicate;
		};
	}

	public static Specification<Product> filterLikeByDescription(String description) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("description"), "%" + description + "%");
			return equalPredicate;
		};
	}

	public static Specification<Product> filterOnlyProductModels() {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.isNull(root.get("parentProduct"));
			return equalPredicate;
		};
	}

	public static Specification<Product> filterLikeByFactoryName(String factory) {
		return (root, query, criteriaBuilder) -> {
			Join<Product, Factory> factoryJoin = root.join("factory", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(factoryJoin.get("name"), "%" + factory + "%");
			return equalPredicate;
		};
	}

}
