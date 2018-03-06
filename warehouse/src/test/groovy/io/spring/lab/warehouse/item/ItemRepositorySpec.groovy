package io.spring.lab.warehouse.item

import io.spring.lab.warehouse.SpringSpecBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

//FIXME: should be just @DataJpaTest !!!
@SpringBootTest
class ItemRepositorySpec extends SpringSpecBase {

    @Autowired
    ItemRepository items

    def "Finds the most expensive item"() {
        expect:
            with(items.findTopByOrderByPriceDesc()) {
                id == 1L
                price == 40.0
            }
    }
}
