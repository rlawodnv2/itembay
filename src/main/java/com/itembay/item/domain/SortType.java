package com.itembay.item.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

public enum SortType {

	CREATED_AT("createdAt"),
	PRICE("price");

	private final String field;

	SortType(String field) {
		this.field = field;
	}

	public Sort toSort(Sort.Direction direction) {
		return Sort.by(new Sort.Order(direction, field));
	}

	public static Sort toSort(List<SortType> types, List<Sort.Direction> directions) {
		if (types == null || types.isEmpty()) {
			return Sort.unsorted();
		}

		if (directions == null || directions.size() != types.size()) {
			throw new IllegalArgumentException("SortType과 Direction의 개수가 일치해야 합니다.");
		}

		List<Sort.Order> orders = new ArrayList<>();
		for (int i = 0; i < types.size(); i++) {
			orders.add(
				new Sort.Order(
					directions.get(i),
					types.get(i).field
				)
			);
		}

		return Sort.by(orders);
	}
}
