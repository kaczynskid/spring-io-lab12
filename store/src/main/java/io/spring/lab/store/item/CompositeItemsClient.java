package io.spring.lab.store.item;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@EnableBinding(ItemsBinding.class)
class CompositeItemsClient implements ItemsClient {

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

    private final ItemsBinding binding;

    private final ObjectMapper json;

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
        asynchronousStockUpdate(changes);
    }

    private void asynchronousStockUpdate(ItemStockUpdate changes) {
        ObjectToJsonTransformer transformer = new ObjectToJsonTransformer(new Jackson2JsonObjectMapper(json));

        Message<ItemStockUpdate> message = MessageBuilder.withPayload(changes).build();

        binding.stockUpdate().send(transformer.transform(message));
    }

    private void synchronousStockUpdate(ItemStockUpdate changes) {
        client.updateStock(changes.getId(), changes);
    }
}
