package io.spring.lab.marketing.special.select;

import static io.spring.lab.marketing.special.calculate.SpecialCalculator.regularPrice;

import java.math.BigDecimal;
import java.util.List;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;

public class FirstSpecialSelector implements SpecialSelector {

	@Override
	public SpecialCalculator selectSpecial(List<SpecialCalculator> specials, int unitCount, BigDecimal unitPrice) {
		return specials.stream()
				.findFirst()
				.orElse(regularPrice);
	}
}
