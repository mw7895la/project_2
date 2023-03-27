package shoppingMall.project_2.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import shoppingMall.project_2.domain.member.Member;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Item {

    private Long id;
    private int quantity;
    private int price;
    private String itemName;
    private String userId;
    private int likeCount;
    private String image;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;


    public Item(int quantity, int price, String itemName, String userId, int likeCount, String image) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
        this.userId = userId;
        this.likeCount = likeCount;
        this.image = image;
    }

    public Item(){

    }

/**
     * 이미지도 넣어야 함.
     */
}
