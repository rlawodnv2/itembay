package com.itembay.item.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleEnumTypeMismatch(MethodArgumentTypeMismatchException ex){
		Class<?> requiredType = ex.getRequiredType();
		
		if(requiredType != null && requiredType.isEnum()) {
			String enumName = requiredType.getSimpleName();
			String allowedValues = Arrays.stream(requiredType.getEnumConstants())
											.map(Object::toString)
											.collect(Collectors.joining(", "));
			
			return ResponseEntity.badRequest()
									.body(new ErrorResponse(
												"INVALID_ENUM_VALUE",
												String.format(
													"%s 값이 올바르지 않습니다. 허용 값: [%s]",
													ex.getName(),
													allowedValues
												)
											));
		}
		
		return ResponseEntity.badRequest()
								.body(new ErrorResponse(
											"INVALID_PARAMETER",
											"요청 파라미터 타입이 올바르지 않습니다."
										));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(
														error -> errors.put(error.getField(), error.getDefaultMessage())
													);
		
		return ResponseEntity.badRequest()
								.body(Map.of(
											"code", "VALIDATION_FAILED",
											"error", errors
										));
	}
}
