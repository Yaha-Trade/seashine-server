package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Customer;
import com.seashine.server.domain.Factory;
import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.Product;
import com.seashine.server.domain.Season;

public class OrderListItemSpecs {

	public static Specification<OrderListItem> filterByOrderListId(Integer id) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.equal(root.get("orderList"), id);
			return equalPredicate;
		};
	}

	public static Specification<OrderListItem> filterLikeByCustomerName(String customer) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderListItem, OrderList> orderListJoin = root.join("orderList", JoinType.INNER);
			Join<OrderList, Season> seasonJoin = orderListJoin.join("season", JoinType.INNER);
			Join<Season, Customer> customerJoin = seasonJoin.join("customer", JoinType.INNER);
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

	public static Specification<OrderListItem> filterLikeByFactoryName(String factory) {
		return (root, query, criteriaBuilder) -> {
			Join<OrderListItem, Product> productJoin = root.join("product", JoinType.INNER);
			Join<Product, Factory> factoryJoin = productJoin.join("factory", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(factoryJoin.get("name"), Utils.getLike(factory));
			return equalPredicate;
		};
	}
}
