package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.Factory;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer> {

}
