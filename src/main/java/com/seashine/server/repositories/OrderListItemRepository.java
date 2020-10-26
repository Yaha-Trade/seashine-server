package com.seashine.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seashine.server.domain.OrderListItem;

@Repository
public interface OrderListItemRepository
		extends JpaRepository<OrderListItem, Integer>, JpaSpecificationExecutor<OrderListItem> {

	@Transactional(readOnly = true)
	@Query("SELECT orderListItem FROM OrderListItem orderListItem WHERE orderListItem.product.parentProduct.id = :parentProductId AND orderListItem.orderList.id = :orderListId")
	List<OrderListItem> findByParentProductIdAndOrderListId(Integer parentProductId, Integer orderListId);

	@Transactional(readOnly = true)
	@Query("SELECT orderListItem FROM OrderListItem orderListItem WHERE orderListItem.product.parentProduct.id = :parentProductId")
	Page<OrderListItem> findByParentProductId(Integer parentProductId, Pageable pageable);

	@EntityGraph(attributePaths = { "product", "product.factory" })
	Page<OrderListItem> findAll(@Nullable Specification<OrderListItem> spec, Pageable pageable);
}
