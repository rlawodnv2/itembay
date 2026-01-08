package com.itembay.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.itembay.item.domain.Item;

import jakarta.persistence.LockModeType;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select i from Item i where i.id = :id")
	Optional<Item> findByIdForUpdate(Long id);
}
