package com.itembay.item.domain;

public enum ItemType {
	GAME_MONEY("게임 머니"),
	ITEM("아이템"),
	ACCOUNT("계정"),
	ETC("기타");

	ItemType(String displayName) {
		this.displayName = displayName;
	}

	private final String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
}
