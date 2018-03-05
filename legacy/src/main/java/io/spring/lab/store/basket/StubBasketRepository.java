package io.spring.lab.store.basket;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

class StubBasketRepository implements BasketRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Basket> db = new HashMap<>();

    @Override
    public Basket findOne(long id) {
        return db.get(id);
    }

    @Override
    public Basket save(Basket basket) {
        long id = setAndGetNextId(basket);
        db.put(id, basket);
        return basket;
    }

    @Override
    public Basket saveAndFlush(Basket basket) {
        return save(basket);
    }

    private long setAndGetNextId(Basket basket) {
        try {
            long id = seq.incrementAndGet();
            writeField(basket, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
