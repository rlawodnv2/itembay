package com.itembay.item.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String server;
	private String sellerName;
	
	@Enumerated(EnumType.STRING)
	private ItemType itemType;
	
	private String title;
	private long price;
	private long quantity;

	@Builder
    private Item(String server, String sellerName, ItemType itemType,
                 String title, Integer price, Integer quantity) {
        this.server = server;
        this.sellerName = sellerName;
        this.itemType = itemType;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
