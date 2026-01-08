package com.itembay.item.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity {
	
	@Column(updatable = false)
	protected LocalDateTime createdAt;
	
	@Column(updatable = false)
	protected LocalDateTime modifyedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.modifyedAt = LocalDateTime.now();
	}
}
