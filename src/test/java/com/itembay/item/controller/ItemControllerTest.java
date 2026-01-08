package com.itembay.item.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.itembay.item.domain.Item;
import com.itembay.item.domain.ItemType;
import com.itembay.item.repository.ItemRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

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
}