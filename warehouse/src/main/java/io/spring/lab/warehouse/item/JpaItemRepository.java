package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaItemRepository implements ItemRepository {

    interface SpringDataItemRepository extends JpaRepository<Item, Long> {

        Item findTopByOrderByPriceDesc();
    }

    private final SpringDataItemRepository repository;

    @Override
    public Item findOne(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public Item findTopByOrderByPriceDesc() {
        return repository.findTopByOrderByPriceDesc();
    }
}
