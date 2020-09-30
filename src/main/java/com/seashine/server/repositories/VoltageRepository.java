package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.Voltage;

@Repository
public interface VoltageRepository extends JpaRepository<Voltage, Integer> {

}
