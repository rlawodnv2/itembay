package com.itembay.item.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itembay.item.domain.Item;
import com.itembay.item.dto.ItemCreateRequest;
import com.itembay.item.dto.ItemResponse;
import com.itembay.item.dto.ItemSearchCondition;
import com.itembay.item.repository.ItemRepository;
import com.itembay.item.specification.ItemSpecification;

import jakarta.validation.Valid;

@Service
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Cacheable(
		cacheNames = "itemList",
		key = "{#condition, #pageable.pageNumber, #pageable.pageSize}"
	)
	public Page<ItemResponse> getItems(ItemSearchCondition condition, Pageable pageable){
		
		Page<Item> page = itemRepository.findAll(
													ItemSpecification.search(condition),
													pageable
												);
		
		return page.map(ItemResponse::from);
	}
	
	@Transactional
	@CacheEvict(cacheNames = "itemList", allEntries = true)
	public Item create(ItemCreateRequest request) {
		Item item = Item.builder()
							.server(request.getServer())
							.sellerName(request.getSellerName())
							.itemType(request.getItemType())
							.title(request.getTitle())
							.price(request.getPrice())
							.quantity(request.getQuantity())
							.build();
		
		return itemRepository.save(item);
	}

	@Transactional
	@CacheEvict(cacheNames = "itemList", allEntries = true)
	public Item update(@Valid ItemCreateRequest request, Long id) {
		Item item = itemRepository.findByIdForUpdate(id)
								.orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
	
		item.update(
			request.getServer(),
			request.getSellerName(),
			request.getItemType(),
			request.getTitle(),
			request.getPrice(),
			request.getQuantity()
		);
	
		return item;
	}

	@Transactional
	@CacheEvict(cacheNames = "itemList", allEntries = true)
	public void delete(Long id) {
		Item item = itemRepository.findById(id)
								.orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
		
		itemRepository.delete(item);
	}
}
