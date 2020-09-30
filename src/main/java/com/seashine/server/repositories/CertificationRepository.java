package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {

}
