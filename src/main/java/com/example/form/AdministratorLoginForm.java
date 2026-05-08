package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 管理者用ログインフォーム.
 *
 * @author shunsei
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AdministratorLoginForm {
    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が不正です")
    private String mailAddress;

    /** パスワード */
    @NotBlank(message = "パスワードを入力してください")
    private String password;
}
