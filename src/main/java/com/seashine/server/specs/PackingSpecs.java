package com.seashine.server.specs;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.seashine.server.domain.Packing;

public class PackingSpecs {

	public static Specification<Packing> filterLikeByEnglishName(String englishName) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("englishName"), Utils.getLike(englishName));
			return equalPredicate;
		};
	}

	public static Specification<Packing> filterLikeByChineseName(String chineseName) {
		return (root, query, criteriaBuilder) -> {
			Predicate equalPredicate = criteriaBuilder.like(root.get("chineseName"), Utils.getLike(chineseName));
			return equalPredicate;
		};
	}

}
