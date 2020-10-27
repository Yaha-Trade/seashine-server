package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Customer;
import com.seashine.server.domain.Season;

public class SeasonSpecs {

	public static Specification<Season> filterLikeByName(String name) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("name"), Utils.getLike(name));
			return equalPredicate;
		};
	}

	public static Specification<Season> filterLikeByCustomerName(String customer) {
		return (root, query, criteriaBuilder) -> {
			Join<Season, Customer> customerJoin = root.join("customer", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(customerJoin.get("name"), Utils.getLike(customer));
			return equalPredicate;
		};
	}

}
