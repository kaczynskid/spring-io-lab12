package io.spring.lab.marketing.special;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.lab.marketing.special.calculate.SpecialCalculation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/specials")
public class SpecialController {

    private final SpecialService specials;

    @GetMapping
    public List<SpecialRepresentation> findAll() {
        return specials.findAll().stream()
                .map(SpecialRepresentation::of)
                .collect(toList());
    }

    @PostMapping
    public SpecialRepresentation add(@RequestBody SpecialRepresentation request) {
        return SpecialRepresentation.of(specials.create(request.asSpecial()));
    }

    @PostMapping("/{itemId}/calculate")
    public SpecialCalculation calculateFor(@PathVariable("itemId") long itemId,
                                           @RequestBody SpecialCalculationRequest request) {
        SpecialCalculation calculation = specials.calculateFor(itemId, request.getUnitPrice(), request.getUnitCount());
        return calculation;
    }

}
