package com.itembay.item.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.SortType;
import com.itembay.item.dto.ItemCreateRequest;
import com.itembay.item.dto.ItemResponse;
import com.itembay.item.dto.ItemSearchCondition;
import com.itembay.item.dto.PageResponse;
import com.itembay.item.service.ItemService;

import jakarta.validation.Valid;

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
	
	@PostMapping("/api/items")
	public ResponseEntity<Item> createItem(@Valid @RequestBody ItemCreateRequest request){
		
		Item saved = itemService.create(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}
