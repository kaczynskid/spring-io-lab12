package io.spring.lab.marketing.special;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
