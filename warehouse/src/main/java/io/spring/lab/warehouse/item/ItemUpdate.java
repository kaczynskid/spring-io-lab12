package io.spring.lab.warehouse.item;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@ToString
class ItemUpdate {

	private final long id;

	private final String name;

	private final BigDecimal price;

	@JsonCreator
	static ItemUpdate of(@JsonProperty("name") String name, @JsonProperty("price") BigDecimal price) {
		return new ItemUpdate(0, name, price);
	}

	ItemUpdate withId(long id) {
		return new ItemUpdate(id, name, price);
	}
}
