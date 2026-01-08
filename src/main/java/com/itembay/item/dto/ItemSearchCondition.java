package com.itembay.item.dto;

import com.itembay.item.domain.ItemType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCondition {
	
	private String keyword;
	private ItemType itemType;
	private String server;
	private Integer minPrice;
	private Integer maxPrice;
}
