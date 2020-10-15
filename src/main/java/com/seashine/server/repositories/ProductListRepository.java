package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.ProductList;

@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Integer> {

}
