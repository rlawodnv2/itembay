package com.itembay.item.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.ItemType;
import com.itembay.item.repository.ItemRepository;

import jakarta.persistence.OptimisticLockException;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemRepository itemRepository;
	
	Long itemId;

	@BeforeEach
	void setUp() {
		itemRepository.deleteAll();

		itemRepository.saveAll(List.of(
			Item.builder()
				.server("라엘08")
				.sellerName("아리")
				.itemType(ItemType.GAME_MONEY)
				.title("다야 팝니다")
				.price(10000)
				.quantity(1000)
				.build(),

			Item.builder()
				.server("오르페")
				.sellerName("도레미")
				.itemType(ItemType.ITEM)
				.title("아이템 판매")
				.price(50000)
				.quantity(1)
				.build()
		));
		
		Item item = itemRepository.save(
			Item.builder()
				.server("라엘08")
				.sellerName("아리")
				.itemType(ItemType.GAME_MONEY)
				.title("기존 제목")
				.price(10000)
				.quantity(1000)
				.build()
		);
		
		itemId = item.getId();
	}
	
	@Test
	void 상품_목록_기본_조회() throws Exception {

		mockMvc.perform(get("/api/items")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content.length()").value(2))
			.andExpect(jsonPath("$.page").value(0))
			.andExpect(jsonPath("$.size").value(10));
	}
	
	@Test
	void 가격_오름차순_정렬() throws Exception {

		mockMvc.perform(get("/api/items")
				.param("sortTypes", "PRICE")
				.param("directions", "ASC"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].price").value(10000))
			.andExpect(jsonPath("$.content[1].price").value(50000));
	}
	
	@Test
	void 등록일_내림차순_정렬() throws Exception {

		mockMvc.perform(get("/api/items")
				.param("sortTypes", "CREATED_AT")
				.param("directions", "DESC"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].createdAt").exists());
	}
	
	@Test
	void 상품명_검색() throws Exception {

		mockMvc.perform(get("/api/items")
				.param("keyword", "다야"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.length()").value(1))
			.andExpect(jsonPath("$.content[0].title").value("다야 팝니다"));
	}
	
	@Test
	void 잘못된_정렬_ENUM_요청() throws Exception {

		mockMvc.perform(get("/api/items")
				.param("sortTypes", "NAME"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("INVALID_ENUM_VALUE"));
	}
	
	@Test
	void 상품_수정_성공() throws Exception {

		mockMvc.perform(put("/api/items/{id}", itemId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
				{
				  "server": "라엘08",
				  "sellerName": "아리",
				  "itemType": "GAME_MONEY",
				  "title": "수정된 제목",
				  "price": 20000,
				  "quantity": 500
				}
				"""))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("수정된 제목"))
			.andExpect(jsonPath("$.price").value(20000));
	}
	
	@Test
	void 상품_삭제_성공() throws Exception {
		mockMvc.perform(delete("/api/items/{id}", itemId))
			.andExpect(status().isNoContent());
	}
	
	@Test
	void 상품_삭제_실패_존재하지않음() throws Exception {
		mockMvc.perform(delete("/api/items/{id}", 999L))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	void optimistic_lock_충돌_발생() {
		Item item = itemRepository.save(
			Item.builder()
				.server("라엘08")
				.sellerName("아리")
				.itemType(ItemType.GAME_MONEY)
				.title("다야")
				.price(10000)
				.quantity(100)
				.build()
		);

		Item a = itemRepository.findById(item.getId()).get();
		Item b = itemRepository.findById(item.getId()).get();

		a.update(
			"라엘08", "아리", ItemType.GAME_MONEY,
			"A가 수정", 20000, 100
		);
		itemRepository.saveAndFlush(a);

		b.update(
			"라엘08", "아리", ItemType.GAME_MONEY,
			"B가 수정", 30000, 100
		);

		assertThrows(OptimisticLockException.class, () -> {
			itemRepository.saveAndFlush(b);
		});
	}
}