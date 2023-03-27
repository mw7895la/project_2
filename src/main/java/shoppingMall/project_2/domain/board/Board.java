package shoppingMall.project_2.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Board {

    private Long id;
    @NotBlank(message = "제목은 꼭 입력해야 합니다.")
    private String title;

    @NotBlank(message = "내용은 꼭 입력해야 합니다.")
    private String content;
    private String userId;

    private long viewCount;
    private String regDate;

    public Board() {
    }
}
