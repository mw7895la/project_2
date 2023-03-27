package shoppingMall.project_2.repository.item;


import shoppingMall.project_2.domain.item.Item;
import shoppingMall.project_2.domain.item.ItemSearchCond;
import shoppingMall.project_2.domain.item.ItemUpdateForm;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item addItem(Item item);

    void update(Long id, ItemUpdateForm updateForm);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);

}
