package com.itembay.item.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itembay.item.domain.SortType;
import com.itembay.item.dto.ItemResponse;
import com.itembay.item.dto.ItemSearchCondition;
import com.itembay.item.dto.PageResponse;
import com.itembay.item.service.ItemService;

@RestController
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	
	@GetMapping("/api/items")
	public ResponseEntity<PageResponse<ItemResponse>> getItems(ItemSearchCondition condition,
																Pageable pageable,
																List<SortType> sortTypes,
																List<Sort.Direction> directions) {
		Pageable effectivePageable;

		if (sortTypes != null && !sortTypes.isEmpty()) {
			Sort sort = SortType.toSort(sortTypes, directions);
			effectivePageable = PageRequest.of(
												pageable.getPageNumber(),
												pageable.getPageSize(),
												sort
											);
		} else {
			effectivePageable = pageable;
		}

		Page<ItemResponse> page = itemService.getItems(condition, effectivePageable);

		return ResponseEntity.ok(PageResponse.from(page));
	}
}
