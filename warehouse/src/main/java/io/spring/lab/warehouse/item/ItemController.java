package io.spring.lab.warehouse.item;

import static io.spring.lab.warehouse.error.ErrorMessage.messageOf;
import static io.spring.lab.warehouse.error.ErrorMessage.messageResponseOf;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.spring.lab.warehouse.error.ErrorMessage;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
class ItemController {

    private final ItemService items;

    @GetMapping
    List<ItemRepresentation> findAll() {
        List<Item> list = items.findAll();
        return list.stream().map(ItemRepresentation::of).collect(toList());
    }

    @PostMapping
    public ItemRepresentation create(@RequestBody ItemRepresentation request) {
        return ItemRepresentation.of(items.create(request.asItem()));
    }

    @GetMapping("/{id}")
    public ItemRepresentation findOne(@PathVariable("id") long id) {
        Item item = items.findOne(id);
        return ItemRepresentation.of(item);
    }

    @PutMapping("/{id}")
    public ItemRepresentation update(@PathVariable("id") long id, @RequestBody ItemUpdate changes) {
        return ItemRepresentation.of(items.update(changes.withId(id)));
    }

    @PutMapping("/{id}/stock")
    public ItemRepresentation updateStock(@PathVariable("id") long id, @RequestBody ItemStockUpdate changes) {
        return ItemRepresentation.of(items.updateStock(changes.withId(id)));
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorMessage handle(ItemNotFound e) {
        return messageOf(e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handle(OutOfStock e) {
        return messageResponseOf(BAD_REQUEST, e);
    }
}