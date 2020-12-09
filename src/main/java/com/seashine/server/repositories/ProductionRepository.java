package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.Production;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Integer>, JpaSpecificationExecutor<Production> {

}
