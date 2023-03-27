package shoppingMall.project_2.domain.item;

import lombok.Data;

@Data
public class ItemUpdateForm {
    private int quantity;
    private int price;
    private String itemName;
    private String image;

    public ItemUpdateForm(int quantity, int price, String itemName, String image) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
        this.image = image;
    }

    public ItemUpdateForm() {
    }
}
