package com.seashine.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seashine.server.domain.OrderListItem;

@Repository
public interface OrderListItemRepository
		extends JpaRepository<OrderListItem, Integer>, JpaSpecificationExecutor<OrderListItem> {

	@Transactional(readOnly = true)
	@EntityGraph(attributePaths = { "product", "product.factory", "product.certification", "orderList",
			"orderList.season", "orderList.season.customer", "production" })
	Page<OrderListItem> findAll(@Nullable Specification<OrderListItem> spec, Pageable pageable);
}
