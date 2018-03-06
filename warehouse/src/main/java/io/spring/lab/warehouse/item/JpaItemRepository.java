package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaItemRepository implements ItemRepository {

    interface SpringDataItemRepository extends JpaRepository<Item, Long> {

        Item findTopByOrderByPriceDesc();

        List<Item> findByNameStartingWith(String prefix);

        @Query("from Item order by price desc")
        List<Item> findMostExpensive(Pageable pageable);

        @Query("from Item where name like :prefix%")
        List<Item> findByNamePrefix(@Param("prefix") String namePrefix);
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
    public Item findMostExpensive() {
        return repository.findMostExpensive(new PageRequest(0, 1)).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Empty DB!"));
    }

    @Override
    public List<Item> findByNamePrefix(String prefix) {
        return repository.findByNamePrefix(prefix);
    }
}
