package shoppingMall.project_2.web.loginargument;

import java.lang.annotation.*;

/**
 * 로그인이 필요한 요청일 경우 사용한다.
 */
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
