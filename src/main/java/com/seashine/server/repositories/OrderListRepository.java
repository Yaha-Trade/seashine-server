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

import com.seashine.server.domain.OrderList;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer>, JpaSpecificationExecutor<OrderList> {

	@Transactional(readOnly = true)
	@EntityGraph(attributePaths = { "season", "season.customer" })
	Page<OrderList> findAll(@Nullable Specification<OrderList> spec, Pageable pageable);
}
