package shoppingMall.project_2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingMall.project_2.domain.item.Item;
import shoppingMall.project_2.repository.item.ItemRepository;
import shoppingMall.project_2.domain.item.ItemSearchCond;
import shoppingMall.project_2.domain.item.ItemUpdateForm;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        Item savedItem = itemRepository.addItem(item);
        return savedItem;
    }

    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);

    }

    public List<Item> findAll(ItemSearchCond cond) {
        return itemRepository.findAll(cond);

    }

    public void update(Long itemId,ItemUpdateForm updateForm) {
        itemRepository.update(itemId,updateForm);
    }
}
