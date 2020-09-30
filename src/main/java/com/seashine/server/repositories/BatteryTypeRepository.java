package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.BatteryType;

@Repository
public interface BatteryTypeRepository extends JpaRepository<BatteryType, Integer> {

}
