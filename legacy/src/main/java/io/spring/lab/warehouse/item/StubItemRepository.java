package io.spring.lab.warehouse.item;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

class StubItemRepository implements ItemRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Item> db = new HashMap<>();

    @Override
    public Item findOne(long id) {
        return db.get(id);
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Item save(Item item) {
        long id = setAndGetNextId(item);
        db.put(id, item);
        return item;
    }

    private long setAndGetNextId(Item item) {
        try {
            long id = seq.incrementAndGet();
            writeField(item, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
