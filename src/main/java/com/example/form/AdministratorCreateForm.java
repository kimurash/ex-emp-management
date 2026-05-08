package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 管理者登録フォーム.
 *
 * @author shunsei
 */
@Builder
@Getter
@Setter
@ToString
public class AdministratorCreateForm {
    /** 名前 */
    @NotBlank(message = "名前を入力してください")
    private String name;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が不正です")
    private String mailAddress;

    /** パスワード */
    @NotBlank(message = "パスワードを入力してください")
    @Length(min = 6, message = "パスワードは6文字以上でご入力ださい")
    private String password;
}
