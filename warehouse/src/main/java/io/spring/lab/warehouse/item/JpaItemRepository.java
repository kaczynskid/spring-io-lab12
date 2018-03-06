package io.spring.lab.warehouse.item;

import static io.spring.lab.warehouse.item.QItem.item;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaItemRepository implements ItemRepository {

    interface SpringDataItemRepository extends JpaRepository<Item, Long>, QueryDslPredicateExecutor<Item> {

        Item findTopByOrderByPriceDesc();

        List<Item> findByNameStartingWith(String prefix);

        @Query("from Item order by price desc")
        List<Item> findMostExpensive(Pageable pageable);

        @Query("from Item where name like :prefix%")
        List<Item> findByNamePrefix(@Param("prefix") String namePrefix);

        @Override
        List<Item> findAll(Predicate predicate);
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

    @PersistenceContext EntityManager jpa;

    @Override
    public Item findMostExpensive() {
//        return repository.findTopByOrderByPriceDesc();

//        return repository.findMostExpensive(new PageRequest(0, 1)).stream().findFirst()
//                .orElseThrow(() -> new RuntimeException("Empty DB!"));

        return new JPAQuery<Item>(jpa, HQLTemplates.DEFAULT)
                .from(item)
                .orderBy(item.price.desc())
                .fetchFirst();
    }

    @Override
    public List<Item> findByNamePrefix(String prefix) {
//        return repository.findByNameStartingWith(prefix);

//        return repository.findByNamePrefix(prefix);

        return repository.findAll(withNameStartingWith(prefix));
    }

    private static BooleanExpression withNameStartingWith(String prefix) {
        return item.name.startsWith(prefix);
    }
}
