package io.spring.lab.store.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaBasketRepository implements BasketRepository {

    interface SpringDataBasketRepository extends JpaRepository<Basket, Long> {

    }

    private final SpringDataBasketRepository repository;

    @Override
    public Basket findOne(long id) {
        return repository.findOne(id);
    }

    @Override
    public Basket save(Basket basket) {
        return repository.save(basket);
    }

    @Override
    public Basket saveAndFlush(Basket basket) {
        return repository.saveAndFlush(basket);
    }
}
