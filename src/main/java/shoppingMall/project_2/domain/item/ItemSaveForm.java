package shoppingMall.project_2.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemSaveForm {
    @NotNull(message = "수량을 입력해주세요")
    @Range(min = 10, max = 9999)
    private int quantity;
    @NotNull(message = "가격은 필수 입니다.")
    @Range(min = 1000)
    private int price;

    @NotBlank(message = "아이템 이름은 필수입니다.")
    private String itemName;
    //private String image;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
