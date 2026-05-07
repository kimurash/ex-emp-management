package com.example.domain;

import lombok.*;

/**
 * 管理者を表す Entity.
 *
 * @author shunsei
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Administrator {
    /** 従業員ID */
    private Integer id;
    /** 名前 */
    private String name;
    /** メールアドレス */
    private String mailAddress;
    /** パスワード */
    private String password;
}
