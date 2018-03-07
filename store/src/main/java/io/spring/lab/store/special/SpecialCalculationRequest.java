package io.spring.lab.store.special;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = PRIVATE)
public class SpecialCalculationRequest {

    private BigDecimal unitPrice;
    private int unitCount;

    public static SpecialCalculationRequest requestCalculationFor(BigDecimal unitPrice, int unitCount) {
        return new SpecialCalculationRequest(unitPrice, unitCount);
    }
}
