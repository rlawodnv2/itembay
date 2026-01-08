package com.itembay.item.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {
	
	@Column(updatable = false)
	protected LocalDateTime createdAt;
	
	@Column(updatable = false)
	protected LocalDateTime modifiedAt;
	
	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.modifiedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedAt = LocalDateTime.now();
	}
}
