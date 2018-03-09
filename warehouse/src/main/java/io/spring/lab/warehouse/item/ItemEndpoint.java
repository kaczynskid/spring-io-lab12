package io.spring.lab.warehouse.item;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageEndpoint
@EnableBinding(ItemsBinding.class)
@AllArgsConstructor
class ItemEndpoint {

    private final ItemService items;

    private final ObjectMapper json;

    @ServiceActivator(inputChannel = "stockUpdate")
    public void updateStock(String payload) {
        JsonToObjectTransformer transformer = new JsonToObjectTransformer(ItemStockUpdate.class, new Jackson2JsonObjectMapper(json));
        ItemStockUpdate changes = (ItemStockUpdate) transformer.transform(new GenericMessage<>(payload)).getPayload();
        log.info("Update item stock with changes: {}", changes);
        items.updateStock(changes);
    }
}

interface ItemsBinding {

    @Input("stockUpdate")
    SubscribableChannel stockUpdate();
}
