package io.spring.lab.store.special;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

@Value
public class SpecialCalculation {

	private final String specialId;

	private final BigDecimal totalPrice;

	@JsonCreator
	static SpecialCalculation of(@JsonProperty("specialId") String specialId, @JsonProperty("totalPrice")BigDecimal totalPrice) {
		return new SpecialCalculation(specialId, totalPrice);
	}
}
