package shoppingMall.project_2.repository.item;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingMall.project_2.domain.item.Item;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemoryItemRepositoryTest {
/*
    @Autowired
    ItemRepository itemRepository;

    @Test
    void addItem() {
        Item item = new Item(123, 123, "키보드", "우진",5,   "image");
        Item item1 = itemRepository.addItem(item);

        Assertions.assertThat(item1.getId())
                .isEqualTo(1L);
    }*/
}