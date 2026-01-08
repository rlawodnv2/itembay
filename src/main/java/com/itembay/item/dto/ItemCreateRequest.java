package com.itembay.item.dto;

import com.itembay.item.domain.ItemType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemCreateRequest {

	@NotBlank
	private String server;
	
	@NotBlank
	private String sellerName;
	
	@NotNull
	private ItemType itemType;
	
	@NotBlank
	private String title;
	
	@Positive
	private int price;
	
	@Positive
	private int quantity;
}
