package shoppingMall.project_2.web.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotBlank(message = "아이디 값은 필수입니다.")
    private String loginId;
    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;
}
