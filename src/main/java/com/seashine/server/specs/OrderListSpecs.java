package com.seashine.server.specs;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.OrderList;

public class OrderListSpecs {

	public static Specification<OrderList> filterLikeByName(String name) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("name"), Utils.getLike(name));
			return equalPredicate;
		};
	}

}
