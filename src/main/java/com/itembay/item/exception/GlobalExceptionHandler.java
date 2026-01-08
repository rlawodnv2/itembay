package com.itembay.item.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.OptimisticLockException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, String>> handleEnumTypeMismatch(
			MethodArgumentTypeMismatchException ex) {

		Class<?> requiredType = ex.getRequiredType();

		if (requiredType != null && requiredType.isEnum()) {
			String allowedValues = Arrays.stream(requiredType.getEnumConstants())
					.map(Object::toString)
					.collect(Collectors.joining(", "));

			return ResponseEntity.badRequest().body(Map.of(
				"code", "INVALID_ENUM_VALUE",
				"message", String.format(
					"%s 값이 올바르지 않습니다. 허용 값: [%s]",
					ex.getName(), allowedValues
				)
			));
		}

		return ResponseEntity.badRequest().body(Map.of(
			"code", "INVALID_PARAMETER",
			"message", "요청 파라미터 타입이 올바르지 않습니다."
		));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(
			MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
			.forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

		return ResponseEntity.badRequest().body(Map.of(
			"code", "VALIDATION_FAILED",
			"errors", errors
		));
	}

	@ExceptionHandler(OptimisticLockException.class)
	public ResponseEntity<Map<String, String>> handleOptimisticLock() {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
			"code", "CONCURRENT_MODIFICATION",
			"message", "다른 사용자가 먼저 수정했습니다."
		));
	}
}

