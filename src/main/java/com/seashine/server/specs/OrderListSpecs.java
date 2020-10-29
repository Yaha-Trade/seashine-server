package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Customer;
import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.Season;

public class OrderListSpecs {

	public static Specification<OrderList> filterLikeByName(String name) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("name"), Utils.getLike(name));
			return equalPredicate;
		};
	}

	public static Specification<OrderList> filterLikeByCustomerName(String customer) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderList, Season> seasonJoin = root.join("season", JoinType.INNER);
			Join<Season, Customer> customerJoin = seasonJoin.join("customer", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(customerJoin.get("name"), Utils.getLike(customer));
			return equalPredicate;
		};
	}

	public static Specification<OrderList> filterLikeBySeasonName(String season) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderList, Season> seasonJoin = root.join("season", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(seasonJoin.get("name"), Utils.getLike(season));
			return equalPredicate;
		};
	}

}
