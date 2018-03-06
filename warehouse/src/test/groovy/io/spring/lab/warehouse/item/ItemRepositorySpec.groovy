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
            with(items.findMostExpensive()) {
                id == 1L
                price == 40.0
            }
    }

    def "Finds by name prefix"() {
        given:
            items.save(new Item(null, 'Xero', 1, 100.0))
        expect:
            with(items.findByNamePrefix('X')) {
                size() == 1
                get(0).name == 'Xero'
            }
    }
}
