package com.itembay.item.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.ItemType;
import com.itembay.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemSeedDataRunner implements ApplicationRunner {
	private final ItemRepository itemRepository;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) {
		if (itemRepository.count() > 0) {
			return;
		}

		itemRepository.saveAll(List.of(
			Item.builder()
				.server("라엘08")
				.sellerName("아리")
				.itemType(ItemType.GAME_MONEY)
				.title("다이아 1,000,000 판매")
				.price(100000)
				.quantity(1000000)
				.build(),

			Item.builder()
				.server("오르페")
				.sellerName("도레미")
				.itemType(ItemType.ITEM)
				.title("전설 아이템 판매")
				.price(50000)
				.quantity(1)
				.build()
		));
	}
}
