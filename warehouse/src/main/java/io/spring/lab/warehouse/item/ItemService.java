package io.spring.lab.warehouse.item;

import java.util.List;

import org.apache.commons.lang3.Validate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemService {

	private final ItemRepository items;

	public Item findOne(long id) {
		Item item = items.findOne(id);
		if (item == null) {
			throw new ItemNotFound(id);
		}
		return item;
	}

	public List<Item> findAll() {
		return items.findAll();
	}

	public Item create(Item item) {
		return items.save(item);
	}

	public Item update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.update(changes);
		return items.save(item);
	}

	public Item updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.updateStock(changes);

		return items.save(item);
	}
}

