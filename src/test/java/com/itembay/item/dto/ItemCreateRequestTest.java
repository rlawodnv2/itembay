package com.itembay.item.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class ItemCreateRequestTest {

	Validator validator;

	@BeforeEach
	void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void 가격_음수_실패() {
		ItemCreateRequest req = new ItemCreateRequest();
		req.setPrice(-1);

		Set<ConstraintViolation<ItemCreateRequest>> violations =
			validator.validate(req);

		assertFalse(violations.isEmpty());
	}
}
