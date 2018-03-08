package io.spring.lab.store.special;

import java.math.BigDecimal;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FeignSpecialClient implements SpecialClient {

    @FeignClient(name = "marketing", path = "/specials", fallback = Fallback.class)
    interface Specials {

        @PostMapping("/{itemId}/calculate")
        SpecialCalculation calculateFor(@PathVariable("itemId") long itemId, @RequestBody SpecialCalculationRequest request);
    }

    @Component
    static class Fallback implements Specials {

        @Override
        public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
            return new SpecialCalculation(null, request.getUnitPrice().multiply(BigDecimal.valueOf(request.getUnitCount())));
        }
    }

    private final Specials specials;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        return specials.calculateFor(itemId, request);
    }
}
