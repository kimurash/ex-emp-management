package com.example.domain;

import lombok.*;

import java.util.Date;

/**
 * 従業員を表す Entity.
 *
 * @author shunsei
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    /** 従業員ID */
    private Integer id;
    /** 名前 */
    private String name;
    /** 画像 */
    private String image;
    /** 性別 */
    private String gender;
    /** 入社日 */
    private Date hireDate;
    /** メールアドレス */
    private String mailAddress;
    /** 郵便番号 */
    private String zipCode;
    /** 住所 */
    private String address;
    /** 電話番号 */
    private String telephone;
    /** 給料 */
    private Integer salary;
    /** 特性 */
    private String characteristics;
    /** 不要人数 */
    private Integer dependentsCount;
}
