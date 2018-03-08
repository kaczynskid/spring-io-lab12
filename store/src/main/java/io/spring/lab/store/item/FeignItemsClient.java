package io.spring.lab.store.item;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class FeignItemsClient implements ItemsClient {

    @FeignClient(name = "warehouse", path = "/items")
    interface Client {

        @GetMapping
        List<ItemRepresentation> findAll();

        @GetMapping("/{id}")
        ItemRepresentation findOne(@PathVariable("id") long id);

        @PutMapping("/{id}/stock")
        void updateStock(@PathVariable("id")long id, @RequestBody ItemStockUpdate changes);
    }

    private final Client client;

    @Override
    public List<ItemRepresentation> findAll() {
        return client.findAll();
    }

    @Override
    public ItemRepresentation findOne(long id) {
        return client.findOne(id);
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        client.updateStock(changes.getId(), changes);
    }

}
