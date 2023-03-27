package shoppingMall.project_2.repository.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberUpdateForm {

    @NotBlank(message = "비밀번호 변경 시 아이디는 필수입니다.")
    private String userId;
    @NotBlank(message = "기존 비밀번호를 입력해 주세요")
    private String oldPassword;
    @NotBlank(message = "신규 비밀번호를 입력해 주세요")
    private String newPassword;
    @NotBlank(message = "신규 비밀번호를 한번 더 입력해주세요")
    private String newCheckPassword;
}
