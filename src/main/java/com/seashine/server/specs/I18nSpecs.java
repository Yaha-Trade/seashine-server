package com.seashine.server.specs;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.I18n;
import com.seashine.server.domain.Language;

public class I18nSpecs {

	public static Specification<I18n> filterLikeByTextValue(String textValue) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("textValue"), Utils.getLike(textValue));
			return equalPredicate;
		};
	}

	public static Specification<I18n> filterLikeByLanguageName(String factory) {
		return (root, query, criteriaBuilder) -> {
			Join<I18n, Language> languageJoin = root.join("language", JoinType.INNER);
			Predicate equalPredicate = criteriaBuilder.like(languageJoin.get("name"), Utils.getLike(factory));
			return equalPredicate;
		};
	}

}
