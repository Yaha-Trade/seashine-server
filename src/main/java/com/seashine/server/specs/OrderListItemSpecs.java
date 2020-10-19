package com.seashine.server.specs;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.OrderListItem;

public class OrderListItemSpecs {

	public static Specification<OrderListItem> filterByOrderListId(Integer id) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.equal(root.get("orderList"), id);
			return equalPredicate;
		};
	}

}
