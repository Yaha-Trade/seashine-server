package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.BatteryData;

@Repository
public interface BatteryDataRepository extends JpaRepository<BatteryData, Integer> {

}
