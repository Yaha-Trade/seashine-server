package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

}
