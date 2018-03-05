package io.spring.lab.warehouse.item

import spock.lang.Specification

import static io.spring.lab.warehouse.TestDataConfiguration.itemsTestData

class ItemServiceSpec extends Specification {

    ItemRepository repository = new StubItemRepository()

    ItemService items = new ItemService(repository)

    void setup() {
        itemsTestData(repository)
    }

    def  "should find all items" () {
        when:
            def items = items.findAll()
        then:
            items.size() >= 4
    }

    def "should not update not existing item"() {
        when:
            items.update(new ItemUpdate(123L, 'test', 23.99))
        then:
            thrown ItemNotFound
    }

    def "should update item details"() {
        when:
            items.update(new ItemUpdate(1L, 'test', 23.99))
        then:
            Item item = items.findOne(1L)
            item.name == "test"
            item.price == 23.99
    }

    def "should not check out too many items"() {
        when:
           items.updateStock(new ItemStockUpdate(1L, -120))
        then:
            thrown OutOfStock
    }

    def "should check out some items"() {
        when:
            items.updateStock(new ItemStockUpdate(1, -20))
        then:
            Item item = items.findOne(1L)
            item.count == 80
    }
}
