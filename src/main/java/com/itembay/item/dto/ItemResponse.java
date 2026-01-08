package com.itembay.item.dto;

import java.time.LocalDateTime;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.ItemType;

public class ItemResponse {

	private final Long id;
	private final String server;
	private final String sellerName;
	private final ItemType itemType;
	private final String title;
	private final long price;
	private final long quantity;
	private final LocalDateTime createdAt;

	public ItemResponse(Long id, String server, String sellerName, ItemType itemType,
						String title, long price, long quantity, LocalDateTime createdAt) {
		this.id = id;
		this.server = server;
		this.sellerName = sellerName;
		this.itemType = itemType;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.createdAt = createdAt;
	}

	public static ItemResponse from(Item item) {
		return new ItemResponse(
				item.getId(),
				item.getServer(),
				item.getSellerName(),
				item.getItemType(),
				item.getTitle(),
				item.getPrice(),
				item.getQuantity(),
				item.getCreatedAt()
		);
	}

	public Long getId() {
		return id;
	}
	public String getServer() {
		return server;
	}
	public String getSellerName() {
		return sellerName;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public String getTitle() {
		return title;
	}
	public long getPrice() {
		return price;
	}
	public long getQuantity() {
		return quantity;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
