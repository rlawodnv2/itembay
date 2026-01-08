package com.itembay.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.ItemType;
import com.itembay.item.dto.ItemCreateRequest;
import com.itembay.item.repository.ItemRepository;

class ItemServiceTest {
	ItemService itemService;
	ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		itemRepository = Mockito.mock(ItemRepository.class);
		itemService = new ItemService(itemRepository);
	}

	@Test
	void 상품_등록_성공() {
		ItemCreateRequest request = new ItemCreateRequest();
		request.setServer("라엘08");
		request.setSellerName("아리");
		request.setItemType(ItemType.GAME_MONEY);
		request.setTitle("다야 판매");
		request.setPrice(10000);
		request.setQuantity(1000);

		Mockito.when(itemRepository.save(any()))
			   .thenAnswer(inv -> inv.getArgument(0));

		Item item = itemService.create(request);

		assertEquals("라엘08", item.getServer());
		assertEquals(10000, item.getPrice());
	}
}
