package com.itembay.item.dto;

import com.itembay.item.domain.ItemType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateRequest {

	@NotBlank(message = "서버명은 필수입니다.")
	private String server;
	
	@NotBlank(message = "판매자명은 필수입니다.")
	private String sellerName;
	
	@NotNull(message = "상품 종류는 필수입니다.")
	private ItemType itemType;
	
	@NotBlank(message = "상품명은 필수입니다.")
	private String title;
	
	@NotNull(message = "가격은 필수입니다.")
	@Min(value = 1000, message = "가격은 1,000원 이상이여야 합니다.")
	private int price;
	
	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 1, message = "수량은 1 이상이어야 합니다.")
	private int quantity;
}
