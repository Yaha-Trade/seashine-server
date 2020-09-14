package com.seashine.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.I18n;

@Repository
public interface I18nRepository extends JpaRepository<I18n, Integer> {

	List<I18n> findByLanguageId(Integer languageId);
}
