package io.spring.lab.warehouse

import io.spring.lab.warehouse.item.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
abstract class SpringSpecBase extends Specification {

    @TestConfiguration
    static class SpringSpecConfig {

        @Autowired ItemRepository items

        @Bean ApplicationRunner init() {
            return { args ->
                TestDataConfiguration.itemsTestData(items)
            } as ApplicationRunner
        }

    }
}
