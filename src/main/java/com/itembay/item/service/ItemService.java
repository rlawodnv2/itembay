package com.itembay.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itembay.item.domain.Item;
import com.itembay.item.dto.ItemResponse;
import com.itembay.item.dto.ItemSearchCondition;
import com.itembay.item.repository.ItemRepository;
import com.itembay.item.specification.ItemSpecification;

@Service
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Page<ItemResponse> getItems(ItemSearchCondition condition, Pageable pageable){
		
		Page<Item> page = itemRepository.findAll(
													ItemSpecification.search(condition),
													pageable
												);
		
		return page.map(ItemResponse::from);
	}
}
