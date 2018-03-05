package io.spring.lab.store.basket;

public interface BasketRepository {

    Basket findOne(long id);

    Basket save(Basket basket);

    Basket saveAndFlush(Basket basket);
}
