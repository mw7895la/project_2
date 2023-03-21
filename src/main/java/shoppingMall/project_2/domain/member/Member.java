package shoppingMall.project_2.domain.member;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Member {

    private Long id;

    @NotBlank(message = "이름은 필수입니다.")
    private String username;
    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    private Grade grade;

    public Member(){

    }

    public Member(String username, String userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;

    }
}
