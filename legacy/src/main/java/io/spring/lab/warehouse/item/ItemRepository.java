package io.spring.lab.warehouse.item;

import java.util.List;

public interface ItemRepository {

    Item findOne(long id);

    List<Item> findAll();

    Item save(Item item);
}
