package io.spring.lab.warehouse

import io.spring.lab.warehouse.item.Item
import io.spring.lab.warehouse.item.ItemRepository

class TestDataConfiguration {

    static void itemsTestData(ItemRepository items) {
        itemsTestData().each {
            items.save(it)
        }
    }

    static List<Item> itemsTestData() {
        return [
                new Item(null, 'A', 100, 40.0),
                new Item(null, 'B', 100, 10.0),
                new Item(null, 'C', 100, 30.0),
                new Item(null, 'D', 100, 25.0)
        ]
    }
}
