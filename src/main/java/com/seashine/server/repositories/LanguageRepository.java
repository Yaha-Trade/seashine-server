package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
