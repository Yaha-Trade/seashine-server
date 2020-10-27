package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Customer;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.Product;

public class OrderListItemSpecs {

	public static Specification<OrderListItem> filterByOrderListId(Integer id) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.equal(root.get("orderList"), id);
			return equalPredicate;
		};
	}

	public static Specification<OrderListItem> filterLikeByCustomerName(String customer) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderListItem, Customer> customerJoin = root.join("orderList.season.customer", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(customerJoin.get("name"), Utils.getLike(customer));
			return equalPredicate;
		};
	}

	public static Specification<OrderListItem> filterByParentProductId(Integer parentProductId) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderListItem, Product> productJoin = root.join("product", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.equal(productJoin.get("parentProduct"), parentProductId);
			return equalPredicate;
		};
	}
}
