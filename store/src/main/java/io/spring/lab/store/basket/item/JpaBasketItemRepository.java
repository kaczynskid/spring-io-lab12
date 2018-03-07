package io.spring.lab.store.basket.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaBasketItemRepository implements BasketItemRepository {

    interface SpringDataBasketItemRepository extends JpaRepository<BasketItem, Long> {

        List<BasketItem> findByBasketId(long basketId);

        Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId);
    }

    private final SpringDataBasketItemRepository repository;

    @Override
    public List<BasketItem> findByBasketId(long basketId) {
        return repository.findByBasketId(basketId);
    }

    @Override
    public Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId) {
        return repository.findByBasketIdAndItemId(basketId, itemId);
    }

    @Override
    public BasketItem save(BasketItem basketItem) {
        return repository.save(basketItem);
    }

    @Override
    public BasketItem saveAndFlush(BasketItem basketItem) {
        return repository.saveAndFlush(basketItem);
    }

    @Override
    public void delete(BasketItem basketItem) {
        repository.delete(basketItem);
    }
}
